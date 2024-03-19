package org.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.practice.shop.dto.MemberDTO;
import org.practice.shop.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    // field
    private final MemberService memberService;

    // constructor

    //method
    @GetMapping("/join")
    public String memberForm() {
        return "member/memberForm";
    }
    @PostMapping("/join")
    public String memberForm(MemberDTO dto) {
        memberService.saveMember(dto);
        return "redirect:/";
    }
    @PostMapping("/emailcheck")
    public ResponseEntity<String> memberEmailCheck(MemberDTO dto){
        String result = memberService.validateMember(dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/login")
    public String loginMember() {
        return "/member/memberLogin";
    }
    @GetMapping("/login/error")
    public String loginMember(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLogin";
    }
}
