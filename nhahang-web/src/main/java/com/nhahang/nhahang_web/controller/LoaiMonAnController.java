package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.LoaiMonAn;
import com.nhahang.nhahang_web.service.LoaiMonAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/loaimonan")
public class LoaiMonAnController {

    @Autowired
    private LoaiMonAnService service;

    @GetMapping
    public String showList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<LoaiMonAn> list = service.search(keyword);
        model.addAttribute("list", list);
        model.addAttribute("loaiMonAn", new LoaiMonAn());
        model.addAttribute("keyword", keyword);
        return "loaimonan";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute LoaiMonAn loai, Model model) {
        if (service.isTenLoaiExists(loai.getTenLoai())) {
            model.addAttribute("errorAdd", "Tên loại đã tồn tại!");
            model.addAttribute("list", service.getAll());
            model.addAttribute("keyword", ""); // giữ keyword nếu có
            return "loaimonan";
        }

        service.add(loai);
        return "redirect:/loaimonan";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        LoaiMonAn loai = service.getById(id);
        model.addAttribute("loaiMonAn", loai);
        return "loaimonan-edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute LoaiMonAn loai, Model model) {
        List<LoaiMonAn> all = service.getAll();
        for (LoaiMonAn l : all) {
            if (l.getId() != loai.getId() &&
                l.getTenLoai().equalsIgnoreCase(loai.getTenLoai())) {
                model.addAttribute("errorEdit", "Tên loại đã tồn tại!");
                model.addAttribute("loaiMonAn", loai);
                return "loaimonan-edit";
            }
        }

        service.update(loai);
        return "redirect:/loaimonan";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/loaimonan";
    }
}
