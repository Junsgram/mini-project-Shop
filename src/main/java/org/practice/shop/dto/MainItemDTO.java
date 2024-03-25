package org.practice.shop.dto;

import lombok.*;
import org.practice.shop.constant.ItemSellStatus;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainItemDTO {
    private Long id;
    private String itemNm;
    private int price;
    private String imgUrl;
    private int stockNumber;
    private ItemSellStatus itemSellStatus;
}
