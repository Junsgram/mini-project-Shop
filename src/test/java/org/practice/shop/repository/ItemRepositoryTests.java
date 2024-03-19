package org.practice.shop.repository;

import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.practice.shop.constant.ItemSellStatus;
import org.practice.shop.entity.Item;
import org.practice.shop.entity.QItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.stream.IntStream;

@DisplayName("JPA 테스트")
@SpringBootTest
public class ItemRepositoryTests {
    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("등록 테스트")
    // @Test
    public void createItemTest() {
        IntStream.rangeClosed(1,30).forEach(i -> {
            Item item = Item.builder()
                    .itemNum("테스트상품"+i)
                    .price(10000+i)
                    .itemDetail("테스트 상품 상세"+i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .stockNum(10+i).build();
            itemRepository.save(item);
        });
    }
    @DisplayName("상품명으로 조회")
    @Test
    void findbyItemNumTest() {
        List<Item> item = itemRepository.findByItemNum("테스트상품12");
        System.out.println(item);
        item.forEach(i -> {
            System.out.println(i.toString());
        });
    }

    @DisplayName("상품명 or 상품 상세설명으로 조회하기 테스트")
    @Test
    void findByItemNumOrItemDetailTest() {
        List<Item> result = itemRepository.findByItemNumOrItemDetail("테스트 상품3","테스트 상품 상세12");
        result.forEach(i ->{
            System.out.println(i.toString());
        });
    }

    @DisplayName("상품 가격 lessthan으로 조회")
    @Test
    void findbyPriceLessThanTest() {
        List<Item> result = itemRepository.findByPriceLessThanOrderByPriceAsc(10005);
        result.forEach(i ->{
            System.out.println(i.toString());
        });
    }
    @DisplayName("내림차순으로 조회")
    @Test
    void findbyPriceOrderByTest() {
        List<Item> result = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        result.forEach(i ->{
            System.out.println(i.toString());
        });
    }

    @DisplayName("쿼리어노테이션으로 상품 상세설명 조회")
    @Test
    void queryAnnotaionSelectTest() {
        List<Item> result = itemRepository.findByItemDetail("테스트");
        result.forEach(i->{
            System.out.println(i.toString());
        });
    }
    @DisplayName("쿼리어노테이션으로 상품 이름 조회")
    @Test
    void queryAnnotaionSelectTest2() {
        List<Item> result = itemRepository.findByItemNumByNative("5");
        result.forEach(i->{
            System.out.println(i.toString());
        });
    }

    @DisplayName("상품 Querydsl 조회 테스트")
    @Test
    void queryDsltest1() {
        // 쿼리에 들어갈 조건을 만들어주는 빌더
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세";
        int price = 10005;
        String itemSellStatus = "SELL";

        // where 절에 like 조건 할당
        booleanBuilder.and(item.itemDetail.like("%"+itemDetail+"%"));
        // 10005보다 가격이 높은 항목 조회
        booleanBuilder.and(item.price.gt(price));
        // 판매싱태가 SELL인 상태만
        booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        Pageable pageable = PageRequest.of(0,10,Sort.by("id").ascending());
        Page<Item> itemPaging = itemRepository.findAll(booleanBuilder,pageable);
        for(Item i : itemPaging) {
            System.out.println(i);
        }
    }
}
