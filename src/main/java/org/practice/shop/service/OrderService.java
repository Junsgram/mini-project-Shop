package org.practice.shop.service;

import org.practice.shop.dto.OrderDTO;
import org.practice.shop.dto.OrderHistDTO;

import java.util.List;

public interface OrderService {
    // 주문하기 버튼 서비스
     Long order(OrderDTO orderDTO, String email);

    // 구매 이력 조회
     List<OrderHistDTO> getOrderList(String email);

     // 주문 취소 요청자와 주문자가 일치한 지 확인해야한다.
    public boolean validateOrder(Long orderId, String email);
     // 주문 취소하기
    public void cancelOrder(Long orderId);

}
