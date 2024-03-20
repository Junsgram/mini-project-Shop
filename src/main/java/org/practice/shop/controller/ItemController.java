package org.practice.shop.controller;

import org.practice.shop.dto.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {
    @GetMapping("/admin/item/new")
    public String itemAdd(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "/item/itemadd";
    }
}
