package org.practice.shop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.practice.shop.dto.CartDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@DisplayName("장바구니 페이지 테스트")
@SpringBootTest
public class CartItemRepositoryTests {
    @Autowired
    private CartItemRepository cartItemRepository;

    @DisplayName("장바구니 상품 리스트 조회")
    @Test
    void givenCartId_whenFindCartDetailList_thenReturnListDetail() {
        // given
        List<CartDetailDTO> result = cartItemRepository.findCartDetailDTOList(1L);

        // when & then
        for(CartDetailDTO dto : result) {
            System.out.println("=====================");
            System.out.println(dto);
        }
    }
}
