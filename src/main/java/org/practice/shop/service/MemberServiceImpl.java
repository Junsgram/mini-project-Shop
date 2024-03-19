package org.practice.shop.service;


import lombok.RequiredArgsConstructor;
import org.practice.shop.dto.MemberDTO;
import org.practice.shop.entity.Member;
import org.practice.shop.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    public final MemberRepository memberRepository;
    public final PasswordEncoder passwordEncoder;
    @Override
    public Member saveMember(MemberDTO memberDTO) {
        // 인터페이스에 선언된 dtoToEntity메소드를 호출해서 member entity객체를 리턴한다.
        Member member = dtoToEntity(memberDTO);
        // member객체의 password필드의 값은 암호화한 값으로 변경 한다.
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        return memberRepository.save(member);
    }

    @Override
    public String validateMember(MemberDTO dto) {
        // 이메일로 찾은 member객체가 있으면 ok를 없으면 fail를 리턴
        Member findMember = memberRepository.findByEmail(dto.getEmail());
        // ok가 리턴되면 아이디가 있다라는 의미
        if(findMember != null) {
            return "ok";
        }
        // 등록 가능한 이메일
        return "fail";
    }
}
