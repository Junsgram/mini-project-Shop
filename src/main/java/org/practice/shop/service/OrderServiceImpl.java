package org.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.practice.shop.dto.OrderDTO;
import org.practice.shop.entity.Item;
import org.practice.shop.entity.Member;
import org.practice.shop.entity.Order;
import org.practice.shop.entity.OrderItem;
import org.practice.shop.repository.ItemRepository;
import org.practice.shop.repository.MemberRepository;
import org.practice.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    @Override
    public Long order(OrderDTO orderDTO, String email) {
        // 상품 아이디로 상품 조회
        Item item = itemRepository.findById(orderDTO.getItemId()).get();
        // email로 멤버 조회(회원 조회)
        Member member = memberRepository.findByEmail(email);
        // 상품 주문 아이템들 조회
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .count(orderDTO.getCount())
                .orderPrice(item.getPrice())
                .build();
        orderItemList.add(orderItem);
        // 아이템의 재고 수량이 주문 수량 만큼 감소해야한다.
        item.removeStock(orderDTO.getCount());
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);
        return order.getId();
    }

}
