package org.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.practice.shop.dto.OrderDTO;
import org.practice.shop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    /**
     * @ResponseBody 자바 객체를 http요청의 body로 전달
     * @RequestBody http요청의 본문 body에 담긴 내용을 자바 객체로 전달
     * @Controller 어노테이션이 선언된 클래스에서 메소드 인자로 principal 객체를 넘겨줄 경우
     *              해당 객체에 접근할 수 있다. principal 객체에는 현재 로그인한 회원의 이메일 정보를 조회
     */
    public @ResponseBody ResponseEntity order(@RequestBody OrderDTO orderDTO,
                                              Principal principal) {
        System.out.println("Order 호출");
        String email = principal.getName();
        Long orderId;
        try{
            orderId = orderService.order(orderDTO, email);
        }catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId,HttpStatus.OK);
    }
}
