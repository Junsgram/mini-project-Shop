package org.practice.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.practice.shop.constant.ItemSellStatus;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="item")
@Entity
@SequenceGenerator(name="my_item_seq", sequenceName ="item_seq", initialValue = 1, allocationSize = 1)
public class Item extends BaseEntity {
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_item_seq")
    private Long id; // 상품번호, 기본키

    @Column(length = 300, nullable = false)
    private String itemNm; // 상품명

    @Column(nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNum; // 재고수량

    @Lob // BLOB타입을 매핑해준다 ( 상세설명과 같은 작성할 것이 많은 것을 BLOCK타입으로 지정)
    @Column(nullable =false)
    private String itemDetail; // 상세설명

    @Enumerated(EnumType.STRING) // Enum타입에 String으로 넣겠다 라는 의미
    private ItemSellStatus itemSellStatus; // 상품판매상태 , SELL, SOLD_OUT
}
