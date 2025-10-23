package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.NhapHang;
import com.nhahang.nhahang_web.service.MonAnService;
import com.nhahang.nhahang_web.service.NhapHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/nhaphang")
public class NhapHangController {

    @Autowired
    private NhapHangService nhapHangService;

    @Autowired
    private MonAnService monAnService;

    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<NhapHang> list = nhapHangService.getAll();

        // ✅ Lọc theo từ khóa (tên món)
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.toLowerCase();
            list = list.stream()
                    .filter(n -> n.getMonAn() != null && n.getMonAn().getTenMon().toLowerCase().contains(kw))
                    .collect(Collectors.toList());
        }

        // ✅ Tính tổng tiền
        double tongTien = list.stream()
                .mapToDouble(n -> n.getSoLuong() * n.getGiaNhap())
                .sum();

        model.addAttribute("list", list);
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("dsMonAn", monAnService.getAll());
        model.addAttribute("nhapHang", new NhapHang());
        model.addAttribute("keyword", keyword); // giữ lại từ khóa

        return "nhaphang";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute NhapHang nhapHang) {
        nhapHang.setNgayNhap(LocalDate.now());
        nhapHangService.save(nhapHang);
        return "redirect:/nhaphang";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        nhapHangService.delete(id);
        return "redirect:/nhaphang";
    }
}
