package org.practice.shop.repository;

import org.practice.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // 현재 로그인한 회원의 cart엔티티 조회
    Cart findByMemberId(Long memberId);
}
