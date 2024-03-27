package org.practice.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@SequenceGenerator(name = "cartItem_seq", sequenceName = "cartItem_seq", initialValue = 1, allocationSize = 1)
public class CartItem extends BaseEntity {
    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cartItem_seq")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private int count;

    // 담겨있는 상품일 때 기존 수량에 현재 담을 수량 더하기
    public void addCount(int count) {
        this.count += count;
    }

    // 수량이 변경될 때 수량 변경
    public void updateCount(int count) {
        this.count = count;
    }
}
