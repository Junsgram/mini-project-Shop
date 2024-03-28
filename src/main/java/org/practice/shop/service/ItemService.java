package org.practice.shop.service;

import org.practice.shop.dto.ItemDTO;
import org.practice.shop.dto.MainItemDTO;
import org.practice.shop.dto.PageRequestDTO;
import org.practice.shop.dto.PageResultDTO;
import org.practice.shop.entity.Item;
import org.practice.shop.entity.ItemImg;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    // 아이템 저장 (insert)
    Long saveItem(ItemDTO iteDTO, List<MultipartFile> itemImgFileList) throws Exception;
    // 아이템 상세조회
    ItemDTO getItem(Long itemId);
    // 아이템 업데이트
    Long updateItem(ItemDTO itemDTO, List<MultipartFile> itemImgFileList) throws Exception;
    // 아이템 조회(paging)
    PageResultDTO<ItemDTO, Item> getList(PageRequestDTO requestDTO);
    // 메인 상품 조회
    PageResultDTO<MainItemDTO, Object[]> getMainList(PageRequestDTO requestDTO);
    // 아이템 조회
    PageResultDTO<MainItemDTO,Object[]> getSearchList(PageRequestDTO pageRequestDTO);

    // dto -> entity 변환
    default Item dtoToEntity(ItemDTO itemDTO) {
        Item item = Item.builder()
                .itemNm(itemDTO.getItemNm())
                .itemDetail(itemDTO.getItemDetail())
                .itemSellStatus(itemDTO.getItemSellStatus())
                .stockNum(itemDTO.getStockNumber())
                .price(itemDTO.getPrice())
                .build();
        return item;
    }
    // entity -> dto 변환
    default ItemDTO entityToDTO(Item item) {
        ItemDTO dto = ItemDTO.builder()
                .id(item.getId())
                .itemNm(item.getItemNm())
                .itemDetail(item.getItemDetail())
                .price(item.getPrice())
                .stockNumber(item.getStockNum())
                .itemSellStatus(item.getItemSellStatus())
                .build();
        return dto;
    }

    // Object[] -> MainItemDTO로 변경
    default MainItemDTO entityObjToDTO(Item item, ItemImg itemImg) {
        MainItemDTO mainItemDTO = MainItemDTO.builder()
                .id(item.getId())
                .itemNm(item.getItemNm())
                .stockNumber(item.getStockNum())
                .price(item.getPrice())
                .itemSellStatus(item.getItemSellStatus())
                .imgUrl(itemImg.getImgUrl())
                .build();
        return mainItemDTO;
    }
}
