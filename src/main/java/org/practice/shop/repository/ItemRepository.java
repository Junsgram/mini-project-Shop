package org.practice.shop.repository;

import org.practice.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {
    // 상품의 이름으로 조회하는 쿼리 메소드 추가
    List<Item> findByItemNum(String itemNum);

    // or조건으로 처리하기
    List<Item> findByItemNumOrItemDetail(String itemnum, String itemDetail);

    // Less Than 조건 - 매개변수보다 작은 값을 조회
    List<Item> findByPriceLessThanOrderByPriceAsc(Integer price);

    // 정렬 OrderBy필드명
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // 쿼리어노테이션을 활용환 조회
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
    @Query(value = "select * from Item i where i.item_num like %:item_num% order by i.price desc", nativeQuery = true)
    List<Item> findByItemNumByNative(@Param("item_num")String itemnum);


}
