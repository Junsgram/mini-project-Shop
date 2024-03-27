package org.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.practice.shop.entity.ItemImg;
import org.practice.shop.repository.ItemImgRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * ItemImg서비스
 * 1) 파일 업로드 : fileService.uploadFile() 호출
 * 2) itemImg엔티티 영속 저장
 * 3) itemRepository.save(itemImg)
 */

@Service
@RequiredArgsConstructor
public class ItemImgServiceImpl implements ItemImgService{
    @Value("${itemImgLocation}")
    private String itemImgLocation;
    private final FileService fileService;
    private final ItemImgRepository itemImgRepository;
    // 파일 업로드, itemImg 엔티티 영속 저장
    @Override
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        // uploadFile(경로, 원본이미지 이름, 파일)
        String oriImgName = itemImgFile.getOriginalFilename();
        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)) {
            String imgName = fileService.uploadFile(itemImgLocation,oriImgName,itemImgFile.getBytes());
            String imgUrl = "/images/item/"+ imgName;
            itemImg.update(oriImgName,imgName,imgUrl);
            itemImgRepository.save(itemImg);
        }
    }
    // 상세보기 이미지 찾기
    @Override
    public List<ItemImg> findByItemid(Long ItemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemId(ItemId);
        return itemImgList;
    }
    // 이미지 수정하기
    @Override
    public void updateItemImg(Long itemImgId, MultipartFile multipartFile) throws Exception {
        // itemImgId로 조회
        // 있으면 기존 이미지 삭제 없으면 추가
        // 가지고온 이미지 업로드
        // itemImgUpdate
        Optional<ItemImg> result = itemImgRepository.findById(itemImgId);
        ItemImg itemImg = result.get();
        // 원본 이미지 보관
        String oriImgName = multipartFile.getOriginalFilename();
        // 이미지 이름이 비어있지 않으면
        if (!StringUtils.isEmpty(itemImg.getImgName()) && oriImgName.length() > 0) {
            fileService.deleteFile(itemImgLocation + "/" + itemImg.getImgName());
        }
        // 이미지 업로드
        String oriImgName1 = multipartFile.getOriginalFilename();
        if(!StringUtils.isEmpty(oriImgName1)) {
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName1, multipartFile.getBytes());
            // 상품 이미지
            String imgUrl = "/images/item/" + imgName;
            itemImg.update(oriImgName1, imgName, imgUrl);
            itemImgRepository.save(itemImg);
        }
    }
}
