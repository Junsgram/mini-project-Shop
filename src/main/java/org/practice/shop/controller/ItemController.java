package org.practice.shop.controller;

import org.practice.shop.dto.ItemDTO;
import org.practice.shop.dto.PageRequestDTO;
import org.practice.shop.dto.PageResultDTO;
import org.practice.shop.entity.Item;
import org.practice.shop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemAdd(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "/item/itemadd";
    }

    @PostMapping("/admin/item/new")
    public String itemadd(ItemDTO itemDTO,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                          Model model) throws Exception {
        System.out.println(itemDTO.toString());
        // 첫 번째 이미지 파일이 없을 경우 다시 등록 페이지
        if (itemImgFileList.get(0).isEmpty()) {
            model.addAttribute("errorMsg", "첫 번째 이미지는 필수입니다.");
            return "/item/itemadd";
        }
        itemService.saveItem(itemDTO, itemImgFileList);
        return "redirect:/";
    }

    @GetMapping("/admin/item/list")
    public String itemList(Model model, PageRequestDTO pageRequestDTO) {
        PageResultDTO<ItemDTO, Item> result = itemService.getList(pageRequestDTO);
        model.addAttribute("result", result);
        return "/item/itemlist";
    }

    // 상품 상세 조회
    @GetMapping("/admin/item/{itemId}")
    public String itemDetail(@PathVariable("itemId") Long itemId, Model model) {
        ItemDTO itemDTO = itemService.getItem(itemId);
        System.out.println(itemDTO.toString());
        model.addAttribute("itemDTO", itemDTO);
        return "item/itemdetail";
    }

    // 상품 수정
    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@PathVariable("itemId") Long itemId, ItemDTO itemDTO,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) throws Exception {
        System.out.println(itemDTO.toString());
        itemService.updateItem(itemDTO, itemImgFileList);
        return "redirect:/admin/item/list";
    }
    // 상품 상세보기
    @GetMapping("/item/{itemId}")
    public String itemDetailView(@PathVariable("itemId")Long itemId, Model model) {
        ItemDTO itemDTO = itemService.getItem(itemId);
        model.addAttribute("itemDTO", itemDTO);
        return "item/detailView";
    }
}
