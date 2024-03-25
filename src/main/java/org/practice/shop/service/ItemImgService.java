package org.practice.shop.service;

import org.practice.shop.dto.ItemImgDTO;
import org.practice.shop.entity.ItemImg;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ItemImgService {
    // 이미지 저장
    void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception;
    // item_id에 해당하는 itemImg 조회
    List<ItemImg> findByItemid(Long ItemId);
    // 수정하기
    void updateItemImg(Long itemImgId, MultipartFile multipartFile) throws Exception;


    // entity -> dto로 변환
    default ItemImgDTO entityToDto(ItemImg itemImg) {
        ItemImgDTO dto = ItemImgDTO.builder()
                .id(itemImg.getId())
                .imgName(itemImg.getImgName())
                .oriImgName(itemImg.getOriImgName())
                .imgUrl(itemImg.getImgUrl())
                .regImgYn(itemImg.getRepimgYn())
                .build();
        return dto;
    }
}
