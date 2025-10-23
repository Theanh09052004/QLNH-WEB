package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.NhanVien;
import com.nhahang.nhahang_web.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienService service;

    @GetMapping
    public String view(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        model.addAttribute("nhanvien", new NhanVien());
        model.addAttribute("list", keyword != null ? service.search(keyword) : service.getAll());
        model.addAttribute("keyword", keyword);
        return "nhanvien";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("nhanvien") NhanVien nv, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("list", service.getAll());
            return "nhanvien";
        }

        // Kiểm tra trùng mã NV
        if (service.existsMaNV(nv.getMaNV())) {
            result.rejectValue("maNV", null, "Mã nhân viên đã tồn tại");
        }

        // Kiểm tra trùng SĐT
        if (service.existsSdt(nv.getSdt())) {
            result.rejectValue("sdt", null, "Số điện thoại đã tồn tại");
        }

        // Kiểm tra trùng email
        if (service.existsEmail(nv.getEmail())) {
            result.rejectValue("email", null, "Email đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("list", service.getAll());
            return "nhanvien";
        }

        service.save(nv);
        return "redirect:/nhanvien";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("nhanvien", service.getById(id));
        return "nhanvien-edit";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("nhanvien") NhanVien nv, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "nhanvien-edit";
        }

        // Lấy dữ liệu gốc theo id
        NhanVien current = service.getById(nv.getId());

        // Nếu sdt thay đổi và đã tồn tại ở nhân viên khác → báo lỗi
        if (!nv.getSdt().equals(current.getSdt()) && service.existsSdt(nv.getSdt())) {
            result.rejectValue("sdt", null, "Số điện thoại đã tồn tại");
        }

        // Nếu email thay đổi và đã tồn tại ở nhân viên khác → báo lỗi
        if (!nv.getEmail().equals(current.getEmail()) && service.existsEmail(nv.getEmail())) {
            result.rejectValue("email", null, "Email đã tồn tại");
        }

        // Nếu có lỗi sau kiểm tra trùng
        if (result.hasErrors()) {
            model.addAttribute("nhanvien", nv);
            return "nhanvien-edit";
        }

        service.update(nv);
        return "redirect:/nhanvien";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/nhanvien";
    }
}
