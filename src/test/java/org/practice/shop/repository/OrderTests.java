package org.practice.shop.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.practice.shop.constant.ItemSellStatus;
import org.practice.shop.entity.Item;
import org.practice.shop.entity.Order;
import org.practice.shop.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class OrderTests {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager em; // 엔티티 객체를 관리해주는 인터페이스

    @DisplayName("order과, order_item의 cascade Test")
    @Test
    void cascadeTest() {
        createOrder();
    }

    @Test
    @DisplayName("고아 객체 삭제하기")
    @Transactional
    public void orPahnRemoveTest() {
        Order order = this.createOrder();
        order.getOrderItems().remove(0);
        System.out.println(order);
        em.flush();
        em.clear();
        Long orderItemId = order.getOrderItems().get(0).getId();
        Optional<OrderItem> result = orderItemRepository.findById(orderItemId);
        System.out.println("클래스 명 : " + result.get().getOrder().getClass());
    }

    public Order createOrder() {
        Order order = new Order();
        for(int i=0; i<3; i++) {
            Item item = Item.builder()
                    .price(100000)
                    .itemNm("테스트 상품")
                    .stockNum(10)
                    .itemDetail("테스트 상품 상세설명 " + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .build();
            itemRepository.save(item);
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .count(10)
                    .order(order)
                    .orderPrice(1000000)
                    .build();
            order.getOrderItems().add(orderItem);
        }
        orderRepository.save(order);
        return order;
    }
}
