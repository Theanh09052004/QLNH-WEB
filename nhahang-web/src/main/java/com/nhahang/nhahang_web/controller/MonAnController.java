package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.MonAn;
import com.nhahang.nhahang_web.model.LoaiMonAn;
import com.nhahang.nhahang_web.service.MonAnService;
import com.nhahang.nhahang_web.service.LoaiMonAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/monan")
public class MonAnController {

    @Autowired
    private MonAnService monAnService;

    @Autowired
    private LoaiMonAnService loaiMonAnService;

    @GetMapping
    public String showList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<MonAn> list = monAnService.search(keyword);
        List<LoaiMonAn> loaiList = loaiMonAnService.getAll();
        model.addAttribute("list", list);
        model.addAttribute("monAn", new MonAn());
        model.addAttribute("loaiList", loaiList);
        model.addAttribute("keyword", keyword);
        return "monan";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute MonAn monAn, Model model) {
        if (monAnService.existsByTenMon(monAn.getTenMon())) {
            model.addAttribute("errorAdd", "Tên món ăn đã tồn tại!");
            model.addAttribute("list", monAnService.getAll());
            model.addAttribute("loaiList", loaiMonAnService.getAll());
            return "monan";
        }

        monAnService.add(monAn);
        return "redirect:/monan";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        MonAn monAn = monAnService.getById(id);
        List<LoaiMonAn> loaiList = loaiMonAnService.getAll();
        model.addAttribute("monAn", monAn);
        model.addAttribute("loaiList", loaiList);
        return "monan-edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MonAn monAn, Model model) {
        List<MonAn> list = monAnService.getAll();
        for (MonAn m : list) {
            if (!m.getId().equals(monAn.getId()) &&
                m.getTenMon().equalsIgnoreCase(monAn.getTenMon())) {
                model.addAttribute("errorEdit", "Tên món ăn đã tồn tại!");
                model.addAttribute("loaiList", loaiMonAnService.getAll());
                model.addAttribute("monAn", monAn); // giữ lại dữ liệu cũ
                return "monan-edit";
            }
        }

        monAnService.update(monAn);
        return "redirect:/monan";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        monAnService.delete(id);
        return "redirect:/monan";
    }
}
