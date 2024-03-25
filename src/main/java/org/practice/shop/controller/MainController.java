package org.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.practice.shop.dto.MainItemDTO;
import org.practice.shop.dto.PageRequestDTO;
import org.practice.shop.dto.PageResultDTO;
import org.practice.shop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {
    private final ItemService itemService;

    @GetMapping("/")
    public String mainPage(Model model, PageRequestDTO requestDTO) {
        PageResultDTO< MainItemDTO,Object[]> result = itemService.getMainList(requestDTO);
        model.addAttribute("result", result);
        List<MainItemDTO> lists = result.getDtoList();
        for(MainItemDTO dto: lists) {
            System.out.println(dto.toString());
        }
        return "main";
    }
}
