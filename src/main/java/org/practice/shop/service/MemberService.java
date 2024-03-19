package org.practice.shop.service;

import org.practice.shop.constant.Role;
import org.practice.shop.dto.MemberDTO;
import org.practice.shop.entity.Member;

public interface MemberService {
    // 회원가입
    Member saveMember(MemberDTO memberDTO);
    // 이메일 중복체크
    String validateMember(MemberDTO dto);

    default Member dtoToEntity(MemberDTO dto) {
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .name(dto.getName())
                .address(dto.getAddress())
                .role(Role.ADMIN)
                .build();
        return member;
    }
}
