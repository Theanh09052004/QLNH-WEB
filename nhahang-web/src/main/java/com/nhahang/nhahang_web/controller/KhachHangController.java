package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.KhachHang;
import com.nhahang.nhahang_web.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/khachhang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    @GetMapping
    public String showList(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("list", khachHangService.search(keyword));
        model.addAttribute("khachhang", new KhachHang());
        return "khachhang";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("khachhang") @Valid KhachHang kh,
                    BindingResult result,
                    Model model,
                    @RequestParam(name = "keyword", required = false) String keyword) {

        if (khachHangService.existsByMaKH(kh.getMaKH())) {
            result.rejectValue("maKH", "error.khachhang", "Mã khách hàng đã tồn tại");
        }

        if (khachHangService.existsBySdt(kh.getSdt())) {
            result.rejectValue("sdt", "error.khachhang", "Số điện thoại đã tồn tại");
        }

        if (khachHangService.existsByEmail(kh.getEmail())) {
            result.rejectValue("email", "error.khachhang", "Email đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("list", khachHangService.search(keyword));
            model.addAttribute("keyword", keyword);
            return "khachhang";
        }

        khachHangService.add(kh);
        return "redirect:/khachhang";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        KhachHang kh = khachHangService.getById(id);
        if (kh == null) {
            return "redirect:/khachhang";
        }
        model.addAttribute("khachhang", kh);
        return "khachhang-edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("khachhang") @Valid KhachHang kh,
                        BindingResult result,
                        Model model) {

        KhachHang old = khachHangService.getById(kh.getId());

        if (!kh.getSdt().equals(old.getSdt()) && khachHangService.existsBySdt(kh.getSdt())) {
            result.rejectValue("sdt", "error.khachhang", "Số điện thoại đã tồn tại");
        }

        if (!kh.getEmail().equalsIgnoreCase(old.getEmail()) && khachHangService.existsByEmail(kh.getEmail())) {
            result.rejectValue("email", "error.khachhang", "Email đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("khachhang", kh);
            return "khachhang-edit";
        }

        khachHangService.update(kh);
        return "redirect:/khachhang";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        khachHangService.delete(id);
        return "redirect:/khachhang";
    }
}
