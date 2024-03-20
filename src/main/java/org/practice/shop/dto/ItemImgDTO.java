package org.practice.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemImgDTO {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String regImgYn;
}
