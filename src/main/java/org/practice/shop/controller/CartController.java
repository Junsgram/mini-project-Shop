package org.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.practice.shop.dto.CartDetailDTO;
import org.practice.shop.dto.CartItemDTO;
import org.practice.shop.dto.CartOrderDTO;
import org.practice.shop.repository.CartRepository;
import org.practice.shop.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart")
    public @ResponseBody ResponseEntity addCart(@RequestBody CartItemDTO cartItemDTO,
                                                Principal principal) {
        String email = principal.getName();
        Long cartItemId;
        // exception error 처리
        try {
            cartItemId = cartService.addCart(cartItemDTO, email);
        } catch (Exception error) {
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    // 장바구니 목록 출력
    @GetMapping("/cart")
    public String cartList(Principal principal, Model model) {
        List<CartDetailDTO> result = cartService.getCartList(principal.getName());
        model.addAttribute("result", result);
        return "/cart/cartList";
    }

    // 장바구니 수량 변경
    @PostMapping("/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId") Long cartItemId,
                                                       @RequestParam("count") int count) {
        if (count <= 0) {
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else {
            cartService.updateCartItemCount(cartItemId, count);
            return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
        }
    }

    @DeleteMapping("/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId) {
        cartService.deleteCartitem(cartItemId);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    // 장바구니 아이템 주문 진행 및 장바구니 아이템 삭제
    @PostMapping("/cart/order")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDTO cartOrderDTO,
                                                      Principal principal) {
        List<CartOrderDTO> cartOrderDTOList = cartOrderDTO.getCartOrderDTOList();
        if(cartOrderDTOList == null || cartOrderDTOList.size()==0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.BAD_REQUEST);
        }
        Long orderId = cartService.orderCartItem(cartOrderDTOList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
