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
public class OrderItem extends BaseEntity{
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

    // 수량 * 가격 = 가격을 리턴
    public int getTotalPrice() {
        return orderPrice * count;
    }
    // 주문 취소 시 상품의 재고 더하기
    public void cancel() {
        this.getItem().addStock(count);
    }
}
