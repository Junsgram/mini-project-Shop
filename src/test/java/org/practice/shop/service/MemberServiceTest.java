package org.practice.shop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.practice.shop.dto.MemberDTO;
import org.practice.shop.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("Member API 테스트")
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입 테스트")
    @Test
    void saveMemberTest() {
        // Given
        MemberDTO dto = MemberDTO.builder()
                .email("admin@green.com")
                .name("관리자")
                .password("1234")
                .address("울산시 남구 달동")
                .build();
        // When & Then
        Member member = memberService.saveMember(dto);
        System.out.println(member);
    }
}
