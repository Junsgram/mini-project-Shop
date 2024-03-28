package org.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.practice.shop.dto.*;
import org.practice.shop.entity.Item;
import org.practice.shop.entity.ItemImg;
import org.practice.shop.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;

    @Override
    public Long saveItem(ItemDTO itemDTO, List<MultipartFile> itemImgFileList) throws Exception {
        // 아이템 영속 저장
        Item item = dtoToEntity(itemDTO);
        itemRepository.save(item);

        // itemImg등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i == 0) {
                itemImg.setRepimgYn("Y");
            } else {
                itemImg.setRepimgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Override
    public PageResultDTO<ItemDTO, Item> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());
        Page<Item> result = itemRepository.findAll(pageable);
        Function<Item, ItemDTO> fn = (entitiy -> entityToDTO(entitiy));
        return new PageResultDTO<>(result, fn);
    }

    // 상품 상세 조회
    @Override
    public ItemDTO getItem(Long itemId) {
        // 아이템 조회하기
        Optional<Item> result = itemRepository.findById(itemId);
        Item item = result.get();
        ItemDTO itemDTO = entityToDTO(item);
        // itemImg 리스트를 조회
        List<ItemImg> itemImgs = itemImgService.findByItemid(itemId);
        // itemimg엔티티 리스트를 -> itemImgDTO 리스트로 변환
        List<ItemImgDTO> itemImgDTOList = itemImgs.stream().map(itemimg ->
                itemImgService.entityToDto(itemimg)).collect(Collectors.toList());
        itemDTO.setItemImgDTOList(itemImgDTOList);
        return itemDTO;
    }

    // 상품 업데이트
    @Override
    public Long updateItem(ItemDTO itemDTO, List<MultipartFile> itemImgFileList) throws Exception {
        // 아이템 값 조회
        Item item = itemRepository.findById(itemDTO.getId()).get();
        // item필드 값 변경
        item.updateItem(itemDTO);
        // item 업데이트 저장
        itemRepository.save(item);
        // itemImg 수정
        List<Long> itemImgIdx = itemDTO.getItemImgIdx();
        // 상품 수정 시 기존에 있던 값에는 update, 값이 없던 곳에는 save를 진행해야한다
        for (int i = 0; i < itemImgFileList.size(); i++) {
            if (itemImgIdx.get(i) == null) {
                System.out.println("null 입니다.");
                ItemImg itemImg = new ItemImg();
                itemImg.setItem(item);
                if (i == 0) {
                    itemImg.setRepimgYn("Y");
                } else {
                    itemImg.setRepimgYn("N");
                }
                itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
            } else {
                itemImgService.updateItemImg(itemImgIdx.get(i), itemImgFileList.get(i));
            }
        }
        return item.getId();
    }

    @Override
    public PageResultDTO<MainItemDTO, Object[]> getMainList(PageRequestDTO requestDTO) {
        Page<Object[]> result =
                itemRepository.getListPage(requestDTO.getPageable(Sort.by("id").descending()));
        Function<Object[], MainItemDTO> fn = (arr->{
            return entityObjToDTO((Item) arr[0], (ItemImg) arr[1]);
        });
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public PageResultDTO<MainItemDTO, Object[]> getSearchList(PageRequestDTO pageRequestDTO) {
        Page<Object[]> result = itemRepository.getList(pageRequestDTO.getPageable(Sort.by("id").descending()),
                pageRequestDTO.getKeyword());
        Function<Object[], MainItemDTO> fn = (arr -> {
            return entityObjToDTO((Item) arr[0], (ItemImg) arr[1]);
        });
        return new PageResultDTO<>(result, fn);
    }
}
