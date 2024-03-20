package org.practice.shop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.practice.shop.entity.Cart;
import org.practice.shop.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class CartRepositoryTest {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @DisplayName("장바구니 회원 Entity 매핑 조회")
    @Test
    void findCartAndMemberTest() {
        Member member = Member.builder()
                .address("울산시 남구")
                .name("수요일")
                .email("su@naver.com")
                .password(passwordEncoder.encode("1111"))
                .build();
        memberRepository.save(member);
        Cart cart = Cart.builder()
                .member(member)
                .build();
        cartRepository.save(cart);
    }


}
