package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.NhaCungCap;
import com.nhahang.nhahang_web.service.NhaCungCapService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ncc")
public class NhaCungCapController {

    @Autowired
    private NhaCungCapService service; 

    @GetMapping
    public String showList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<NhaCungCap> list = (keyword != null && !keyword.isEmpty()) ? service.search(keyword) : service.getAll();
        model.addAttribute("list", list);
        model.addAttribute("ncc", new NhaCungCap());
        model.addAttribute("keyword", keyword);
        return "ncc";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("ncc") NhaCungCap ncc, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("list", service.getAll());
            model.addAttribute("keyword", "");
            return "ncc";
        }
        try {
            service.save(ncc);
        } catch (RuntimeException e) {
            model.addAttribute("list", service.getAll());
            model.addAttribute("error", e.getMessage());
            return "ncc";
        }
        return "redirect:/ncc";
}

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        NhaCungCap ncc = service.getById(id);
        List<NhaCungCap> list = service.getAll();
        model.addAttribute("ncc", ncc);
        model.addAttribute("list", list);
        return "ncc-edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("ncc") NhaCungCap ncc, BindingResult result, Model model) {
        List<NhaCungCap> list = service.getAll();

        // Kiểm tra trùng Email
        boolean emailExists = list.stream()
                .anyMatch(n -> n.getEmail().equalsIgnoreCase(ncc.getEmail()) && n.getId() != ncc.getId());

        if (emailExists) {
            result.rejectValue("email", "error.ncc", "Email đã được sử dụng");
        }

        // Kiểm tra trùng SĐT
        boolean phoneExists = list.stream()
                .anyMatch(n -> n.getSoDienThoai().equals(ncc.getSoDienThoai()) && n.getId() != ncc.getId());

        if (phoneExists) {
            result.rejectValue("soDienThoai", "error.ncc", "Số điện thoại đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("list", list);
            return "ncc-edit";
        }

        service.save(ncc);
        return "redirect:/ncc";
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/ncc";
    }
}
