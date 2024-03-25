package org.practice.shop.service;

import org.practice.shop.dto.OrderDTO;
import org.practice.shop.dto.OrderHistDTO;

import java.util.List;

public interface OrderService {
    // 주문하기 버튼 서비스
     Long order(OrderDTO orderDTO, String email);

    // 구매 이력 조회
     List<OrderHistDTO> getOrderList(String email);

}
