package org.practice.shop.service;

import org.practice.shop.dto.OrderDTO;

public interface OrderService {
    public Long order(OrderDTO orderDTO, String email);
}
