package org.practice.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.practice.shop.constant.ItemSellStatus;
import org.practice.shop.dto.ItemDTO;
import org.practice.shop.exception.OutOfStockException;


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


    // 업데이트 메소드 구현
    public void updateItem(ItemDTO dto) {
        this.itemNm =dto.getItemNm();
        this.price = dto.getPrice();
        this.stockNum = dto.getStockNumber();
        this.itemDetail = dto.getItemDetail();
        this.itemSellStatus = dto.getItemSellStatus();
    }

    // 상품의 재고를 주문 수량만큼 감소시키기
    public void removeStock(int stockNum) {
        int restStock = this.stockNum - stockNum;
        if(restStock < 0 ) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stockNum+")");
        }
        this.stockNum = restStock;
    }

    // 주문 취소 시 취소한 상품의 개수만큼 재고 수 더하기
    public void addStock(int stockNum) {
        this.stockNum += stockNum;
    }
}
