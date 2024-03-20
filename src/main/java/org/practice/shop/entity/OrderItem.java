package org.practice.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="order_item")
@Entity
@SequenceGenerator(name="order_item_seq",sequenceName = "order_item_seq",initialValue = 1, allocationSize = 1)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "order_item_seq")
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;
    private int count;
}
