package org.practice.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cart_item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@SequenceGenerator(name="cartItem_seq",sequenceName = "cartItem_seq",initialValue = 1,allocationSize = 1)
public class CartItem {
    @Id
    @Column(name="cart_item_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator="cartItem_seq")
    private Long id;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;
    private int count;
}
