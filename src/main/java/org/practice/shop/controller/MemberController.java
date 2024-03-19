package org.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.practice.shop.dto.MemberDTO;
import org.practice.shop.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}
