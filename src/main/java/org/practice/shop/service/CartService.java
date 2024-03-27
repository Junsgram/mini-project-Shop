package org.practice.shop.service;

import org.practice.shop.dto.CartDetailDTO;
import org.practice.shop.dto.CartItemDTO;
import org.practice.shop.dto.CartOrderDTO;

import java.util.List;

public interface CartService {
    // 장바구니 추가하기 (카트 엔티티는 member를 참조하므로 memeber객체를 생성하기 위해 email 전달받기)
    public Long addCart(CartItemDTO cartItemDTO, String email);
    // 장바구니 리스트 조회하기
    public List<CartDetailDTO> getCartList(String email);
    // 장바구니 아이템 수량 변경
    public void updateCartItemCount(Long cartItemId, int count);
    // 카트 아이템 삭제
    public void deleteCartitem(Long cartItemId);
    // 장바구니 주문으로 저장
    public long orderCartItem(List<CartOrderDTO> cartOrderDTOList, String email);
}
