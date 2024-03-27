package org.practice.shop.repository;

import org.practice.shop.dto.CartDetailDTO;
import org.practice.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    // CartItem엔티티 조회 - Cart에 해당하는 아이템이 담겨있는 지 없는 지 확인
    // 담겨있다면 수량추가 없다면 객체 생성으로 진행
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new org.practice.shop.dto." +
            "CartDetailDTO(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci " +
            "join ci.item i " +
            "join ItemImg im on im.item.id = i.id " +
            "where ci.cart.id = :cartId " +
            "and im.repimgYn = 'Y' ")
    List<CartDetailDTO> findCartDetailDTOList(@Param("cartId") Long cartId);
}
