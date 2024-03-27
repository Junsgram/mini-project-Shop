package org.practice.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTO {
    private Long cartItemId;        // 장바구니 상품 아이디
    private String itemNm;          // 상품 이름
    private int price;              // 상품 가격
    private int count;              // 수량
    private String imgUrl;          // 상품 이미지 경로
}
