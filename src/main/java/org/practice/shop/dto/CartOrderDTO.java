package org.practice.shop.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CartOrderDTO {
    private Long CartItemId;
    // 장바구니의 아이템 정보를 여러개를 보낼 수 있어 List에 담아 보내줄 것이다.
    private List<CartOrderDTO> cartOrderDTOList;
}
