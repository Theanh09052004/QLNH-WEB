package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.ChiTietDonHang;
import com.nhahang.nhahang_web.model.DonHang;
import com.nhahang.nhahang_web.service.ChiTietDonHangService;
import com.nhahang.nhahang_web.service.DonHangService;
import com.nhahang.nhahang_web.service.MonAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ctdonhang")
public class ChiTietDonHangController {

    @Autowired
    private ChiTietDonHangService chiTietDonHangService;

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private MonAnService monAnService;

    // ✅ Hiển thị danh sách chi tiết đơn hàng + form thêm
    @GetMapping("/donhang/{idDonHang}")
    public String viewByDonHang(@PathVariable int idDonHang,
                                @RequestParam(name = "keyword", required = false) String keyword,
                                Model model) {
        DonHang donHang = donHangService.getById(idDonHang);
        List<ChiTietDonHang> list = chiTietDonHangService.getByDonHang(donHang);

        if (keyword != null && !keyword.trim().isEmpty()) {
            list = list.stream()
                    .filter(ct -> ct.getMonAn().getTenMon().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }

        ChiTietDonHang ct = new ChiTietDonHang();
        ct.setDonHang(donHang); // ⚠️ Cần để Thymeleaf binding id đơn hàng vào form

        model.addAttribute("donHang", donHang);
        model.addAttribute("list", list);
        model.addAttribute("ctdonhang", ct);
        model.addAttribute("dsMonAn", monAnService.getAll());
        model.addAttribute("keyword", keyword);

        return "ctdonhang";
    }

    // ✅ Lấy giá món ăn qua AJAX
    @GetMapping("/gia-mon-an/{idMonAn}")
    @ResponseBody
    public double getGiaMonAn(@PathVariable int idMonAn) {
        return monAnService.getById(idMonAn).getGiaTien();
    }

    // ✅ Lưu chi tiết đơn hàng (đã fix lỗi mất idDonHang khi báo lỗi)
    @PostMapping("/save")
    public String save(@ModelAttribute("ctdonhang") ChiTietDonHang ct, Model model) {
        DonHang donHang = null;
        List<ChiTietDonHang> list = null;
        Integer idDonHang = null;

        try {
            // Lưu ID đơn hàng tạm
            if (ct.getDonHang() != null) {
                idDonHang = ct.getDonHang().getId();
            }

            // Gọi API lưu chi tiết
            chiTietDonHangService.save(ct);

            // ✅ Thành công → quay lại trang chi tiết đơn hàng
            if (idDonHang != null) {
                return "redirect:/ctdonhang/donhang/" + idDonHang;
            } else {
                return "redirect:/donhang";
            }

        } catch (org.springframework.web.client.HttpClientErrorException e) {
            // ⚠️ Nếu API trả lỗi (400 Bad Request)
            if (idDonHang != null) {
                donHang = donHangService.getById(idDonHang);
                list = chiTietDonHangService.getByDonHang(donHang);
            }

            // Lấy nội dung thông báo lỗi
            String responseBody = e.getResponseBodyAsString();
            String errorMessage = responseBody;

            if (responseBody.contains(":")) {
                errorMessage = responseBody.substring(responseBody.lastIndexOf(":") + 1)
                        .replace("\"", "")
                        .replace("}", "")
                        .trim();
            }
            if (errorMessage.toLowerCase().contains("message")) {
                errorMessage = errorMessage.replace("message", "")
                        .replace(":", "")
                        .trim();
            }

            // ⚙️ Tạo lại form và giữ lại id đơn hàng
            ChiTietDonHang newCT = new ChiTietDonHang();
            if (donHang != null) {
                newCT.setDonHang(donHang);
            }

            model.addAttribute("donHang", donHang);
            model.addAttribute("list", list);
            model.addAttribute("ctdonhang", newCT);
            model.addAttribute("dsMonAn", monAnService.getAll());
            model.addAttribute("error", errorMessage);

            return "ctdonhang";

        } catch (Exception e2) {
            // ⚠️ Lỗi khác
            if (idDonHang != null) {
                donHang = donHangService.getById(idDonHang);
                list = chiTietDonHangService.getByDonHang(donHang);
            }

            ChiTietDonHang newCT = new ChiTietDonHang();
            if (donHang != null) {
                newCT.setDonHang(donHang);
            }

            model.addAttribute("donHang", donHang);
            model.addAttribute("list", list);
            model.addAttribute("ctdonhang", newCT);
            model.addAttribute("dsMonAn", monAnService.getAll());
            model.addAttribute("error", "Đã xảy ra lỗi không xác định!");

            return "ctdonhang";
        }
    }

    // ✅ Xóa chi tiết đơn hàng
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        ChiTietDonHang ct = chiTietDonHangService.getById(id);
        if (ct != null && ct.getDonHang() != null) {
            int idDonHang = ct.getDonHang().getId();
            chiTietDonHangService.deleteById(id);
            return "redirect:/ctdonhang/donhang/" + idDonHang;
        }
        return "redirect:/donhang";
    }
}
