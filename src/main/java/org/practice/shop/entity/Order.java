package org.practice.shop.entity;


import jakarta.persistence.*;
import lombok.*;
import org.practice.shop.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "orders")
@Entity
@SequenceGenerator(name = "order_seq", sequenceName = "order_seq", initialValue = 1, allocationSize = 1)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "order_seq")
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;           // 회원 참조

    private LocalDateTime orderDate; // 주문 일자

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;  // 주문 상태

    // 양방향 toString 제거
    @ToString.Exclude
    // 양뱡향 관계일 경우 FK(외래키) 지정한 쪽이 주인으로 지정되어 mappedBy로 알려주는 것이다.
    // orphanRemoval은 List중 1개를 삭제할 수 있게 true로 값을 지정(고아 객체 제거하는 옵션) - cascade는 List를 모두 삭제해야한다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();
}
