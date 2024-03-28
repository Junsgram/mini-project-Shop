package org.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.practice.shop.dto.CartDetailDTO;
import org.practice.shop.dto.CartItemDTO;
import org.practice.shop.dto.CartOrderDTO;
import org.practice.shop.dto.OrderDTO;
import org.practice.shop.entity.Cart;
import org.practice.shop.entity.CartItem;
import org.practice.shop.entity.Item;
import org.practice.shop.entity.Member;
import org.practice.shop.repository.CartItemRepository;
import org.practice.shop.repository.CartRepository;
import org.practice.shop.repository.ItemRepository;
import org.practice.shop.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final OrderService orderService;

    // cart 추가 (insert)
    @Override
    public Long addCart(CartItemDTO cartItemDTO, String email) {
        // 현재 로그인한 멤버가 필요,
        Member member = memberRepository.findByEmail(email);
        // 장바구니에 담을 상품 조회
        Item item = itemRepository.findById(cartItemDTO.getItemId()).get();
        // 현재 로그인한 장바구니 엔티티 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        item.removeStock(cartItemDTO.getCount());
        // 상품을 처음으로 장바구니에 담았을 때 해당 회원의 장바구니를 생성
        // 로그인한 회원의 cart가 없는경우 cart엔티티에 insert 생성 진행.
        if (cart == null) {
            cart = Cart.builder()
                    .member(member)
                    .build();
            cartRepository.save(cart);
        }
        // 카트 상품 내역 장바구니에 해당 아이템이 있는 지 확인
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        // 이미 담겨 있을 때
        if (cartItem != null) {
            // 전달받은 수량만 count에 더해주기
            cartItem.addCount(cartItemDTO.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
        // 담겨있지 않을 때
        else {
            CartItem cartItem1 = CartItem.builder()
                    .cart(cart)
                    .item(item)
                    .count(cartItemDTO.getCount())
                    .build();
            cartItemRepository.save(cartItem1);
            return cartItem1.getId();
        }
    }

    // 장바구니 목록
    @Override
    public List<CartDetailDTO> getCartList(String email) {
        // 장바구니에 상품이 없으면 빈 리스트로 호출하기 위해서 빈 리스트 생성
        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();
        // Member 객체 조회
        Member member = memberRepository.findByEmail(email);
        // Cart 객체 조회 후 id값 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        // 장바구니가 비어있다면 (Cart가 null) 빈 리스트를 호출
        if (cart == null) {
            return cartDetailDTOList;
        } else {
            cartDetailDTOList = cartItemRepository.findCartDetailDTOList(cart.getId());
            return cartDetailDTOList;
        }
    }

    // 장바구니 제품 수량 변경 시 DB 수량 변경
    @Override
    public void updateCartItemCount(Long cartItemId, int count) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).get();
        cartItem.updateCount(count);
        cartItemRepository.save(cartItem);
    }

    // 장바구니 아이템 x버튼 클릭 시 삭제
    @Override
    public void deleteCartitem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).get();
        cartItemRepository.delete(cartItem);
    }

    // 장바구니 주문
    @Override
    public long orderCartItem(List<CartOrderDTO> cartOrderDTOList, String email) {
        // 컨트롤러에서 전달받은 cartOrderDTOList -> List<OrderDTO>로 변환
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(CartOrderDTO cartOrderDTO : cartOrderDTOList) {
            // CartOrderDTO -> CartItemId 필드값이 존재
            CartItem cartItem = cartItemRepository.findById(cartOrderDTO.getCartItemId()).get();
            // OrderDTO -> itemId, count 필드값이 존재
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setItemId(cartItem.getItem().getId());
            orderDTO.setCount(cartItem.getCount());
            orderDTOList.add(orderDTO);
        }
        Long orderId = orderService.orders(orderDTOList, email);
        // 장바구니에서는 삭제
        for(CartOrderDTO cartOrderDTO : cartOrderDTOList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDTO.getCartItemId()).get();
            cartItemRepository.delete(cartItem);
        }
        return orderId;
    }
}
