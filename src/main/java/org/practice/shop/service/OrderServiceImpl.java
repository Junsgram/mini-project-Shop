package org.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.practice.shop.dto.OrderDTO;
import org.practice.shop.dto.OrderHistDTO;
import org.practice.shop.dto.OrderItemDTO;
import org.practice.shop.entity.*;
import org.practice.shop.repository.ItemImgRepository;
import org.practice.shop.repository.ItemRepository;
import org.practice.shop.repository.MemberRepository;
import org.practice.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemImgRepository itemImgRepository;

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

    // 구매이력 조회
    @Override
    public List<OrderHistDTO> getOrderList(String email) {
        // OrderHistDTO를 리턴해야한다.
        // 조회 entitiy order를 리턴
        List<Order> orders = orderRepository.findOrders(email);
        // 조회한 orders list를 OrderHistDTO로 변환하여 반환 진행
        List<OrderHistDTO> histDTOList =
                orders.stream().map(order -> entityToDTO(order)).collect(Collectors.toList());
        return histDTOList;
    }

    public OrderHistDTO entityToDTO(Order order) {
        OrderHistDTO dto = OrderHistDTO.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate().toString())
                .orderStatus(order.getOrderStatus())
                .build();
        // OrderItems리스트 할당
        List<OrderItem> orderItems = order.getOrderItems();
        // 리스트안에는 엔티티가 할당되어있어 DTO로 변경해야한다.
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        for (OrderItem item : orderItems) {
            // 대표 이미지 조회했다 (item.id와 필드값 Y를 전달)
            ItemImg itemImg = itemImgRepository
                    .findByItemIdAndRepimgYn(item.getItem().getId(), "Y");
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .itemNm(item.getItem().getItemNm())
                    .count(item.getCount())
                    .imgUrl(itemImg.getImgUrl())
                    .orderPrice(item.getOrderPrice())
                    .build();
            // 리스트에 dto객체를 추가
            orderItemDTOList.add(orderItemDTO);
        }
        // ordderHistDTO의 필드값에 List<OrderItemDTO> 값을 세팅
        dto.setOrderItemDTOList(orderItemDTOList);
        return dto;
    }
    // 주문자와 사용자 일치 여부 확인
    @Override
    public boolean validateOrder(Long orderId, String email) {
        // 요청자 member객체 조회
        Member member = memberRepository.findByEmail(email);
        // 주문 객체 조회
        Order order = orderRepository.findById(orderId).get();
        Member orderMember = order.getMember();
        // 멤버 객체의 email값이 다른 지 비교
        if(!StringUtils.equals(member.getEmail(), orderMember.getEmail()))  {
            return false;
        }
        return true;
    }
    // 주문 취소 요청
    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.cancelOrder();
        orderRepository.save(order);
    }
}
