package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.Ban;
import com.nhahang.nhahang_web.service.BanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ban")
public class BanController {

    @Autowired
    private BanService service;

    @GetMapping
    public String view(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        model.addAttribute("ban", new Ban());
        model.addAttribute("list", keyword != null ? service.search(keyword) : service.getAll());
        model.addAttribute("keyword", keyword);
        return "ban";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("ban") Ban ban, BindingResult result, Model model) {
        if (service.existsByTenBan(ban.getTenBan())) {
            result.rejectValue("tenBan", null, "Tên bàn đã tồn tại!");
        }
        if (result.hasErrors()) {
            model.addAttribute("list", service.getAll());
            return "ban";
        }
        service.save(ban);
        return "redirect:/ban";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("ban", service.getById(id));
        return "ban-edit";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("ban") Ban ban, BindingResult result, Model model) {
        Ban existing = service.getById(ban.getId());
        if (!existing.getTenBan().equals(ban.getTenBan()) && service.existsByTenBan(ban.getTenBan())) {
            result.rejectValue("tenBan", null, "Tên bàn đã tồn tại!");
        }
        if (result.hasErrors())
            return "ban-edit";
        service.save(ban);
        return "redirect:/ban";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/ban";
    }

    @GetMapping("/toggle/{id}")
    public String toggleTrangThai(@PathVariable int id) {
        Ban b = service.getById(id);
        if (b != null) {
            switch (b.getTrangThai()) {
                case TRONG -> b.setTrangThai(Ban.TrangThaiBan.DANG_SU_DUNG);
                case DANG_SU_DUNG -> b.setTrangThai(Ban.TrangThaiBan.TRONG);
            }
            service.save(b);
        }
        return "redirect:/ban";
    }
}
