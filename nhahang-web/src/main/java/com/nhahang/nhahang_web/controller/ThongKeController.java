package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.dto.ThongKeDTO;
import com.nhahang.nhahang_web.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/thongke")
public class ThongKeController {

    @Autowired
    private ThongKeService thongKeService;

    @GetMapping
    public String index(
            @RequestParam(name = "kieu", defaultValue = "ngay") String kieu,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {

        List<ThongKeDTO> list;

        switch (kieu) {
            case "thang":
                list = thongKeService.thongKeThang(keyword);
                break;
            case "nam":
                list = thongKeService.thongKeNam(keyword);
                break;
            default:
                list = thongKeService.thongKeNgay(keyword);
        }

        model.addAttribute("kieu", kieu);
        model.addAttribute("keyword", keyword);
        model.addAttribute("list", list);
        return "thongke";
    }
}
