package org.practice.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.practice.shop.constant.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistDTO {
    // field
    private Long orderId;               // 주문아이디
    private String orderDate;           // 주문 날짜
    private OrderStatus orderStatus;    // 주문 상태

    @Builder.Default // null값이 지정되지 않도록 builder.default어노테이션 지정
    private List<OrderItemDTO> orderItemDTOList = new ArrayList<>(); // 주문 상품 리스트

    // method
    public void addOrderitemDTO(OrderItemDTO orderItemDTO) {
        orderItemDTOList.add(orderItemDTO);
    }
}
