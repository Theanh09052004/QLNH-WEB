package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.Ban;
import com.nhahang.nhahang_web.model.DonHang;
import com.nhahang.nhahang_web.service.BanService;
import com.nhahang.nhahang_web.service.DonHangService;
import com.nhahang.nhahang_web.service.KhachHangService;
import com.nhahang.nhahang_web.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
@RequestMapping("/donhang")
public class DonHangController {

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private BanService banService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    // ✅ DANH SÁCH ĐƠN HÀNG + DANH SÁCH BÀN TRỐNG
    @GetMapping
    public String list(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        List<DonHang> list = donHangService.search(keyword);

        // Đảm bảo không null khi load dữ liệu (cho hiển thị đúng bàn phụ)
        for (DonHang dh : list) {
            if (dh.getBansPhu() == null) {
                dh.setBansPhu(Collections.emptyList());
            }
        }

        model.addAttribute("donhangList", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("donhang", new DonHang());

        try {
            // ✅ Loại bỏ ký tự thừa, tránh lỗi /trong%0A
            String url = apiBaseUrl.trim() + "/ban/trong";
            List<Ban> banTrong = Arrays.asList(
                    Objects.requireNonNull(restTemplate.getForObject(url, Ban[].class))
            );

            // In log kiểm tra
            System.out.println("✅ API gọi: " + url);
            System.out.println("✅ Số bàn trống lấy được: " + banTrong.size());
            banTrong.forEach(b -> System.out.println(" - " + b.getTenBan() + " (" + b.getTrangThai() + ")"));

            model.addAttribute("bans", banTrong);
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi lấy danh sách bàn trống từ API, fallback sang getAll()");
            e.printStackTrace();
            model.addAttribute("bans", banService.getAll()); // fallback nếu API lỗi
        }

        model.addAttribute("khachhangs", khachHangService.getAll());
        model.addAttribute("nhanviens", nhanVienService.getAll());
        model.addAttribute("trangThaiList", DonHang.TrangThaiDonHang.values());
        return "donhang";
    }

    // ✅ THÊM MỚI ĐƠN HÀNG (có thể chọn nhiều bàn)
    @PostMapping("/add")
    public String add(
            @RequestParam("idBans") List<Integer> idBans,
            @RequestParam("idKhachHang") int idKhachHang,
            @RequestParam("idNhanVien") int idNhanVien
    ) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("idBans", idBans);
            body.put("idKhachHang", idKhachHang);
            body.put("idNhanVien", idNhanVien);
            body.put("tongTien", 0);

            String url = apiBaseUrl.trim() + "/donhang";
            restTemplate.postForObject(url, body, DonHang.class);

            System.out.println("✅ Đã gửi POST tạo đơn hàng với bàn: " + idBans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/donhang";
    }

    // ✅ CHỈNH SỬA ĐƠN HÀNG
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        DonHang dh = donHangService.getById(id);
        if (dh == null)
            return "redirect:/donhang";

        model.addAttribute("donhang", dh);
        model.addAttribute("bans", banService.getAll());
        model.addAttribute("khachhangs", khachHangService.getAll());
        model.addAttribute("nhanviens", nhanVienService.getAll());
        model.addAttribute("trangThaiList", DonHang.TrangThaiDonHang.values());
        return "donhang-edit";
    }

    // ✅ CẬP NHẬT ĐƠN HÀNG (có thể cập nhật lại danh sách bàn)
    @PostMapping("/update")
    public String update(
            @ModelAttribute("donhang") DonHang donHang,
            @RequestParam(value = "idBans", required = false) List<Integer> idBans
    ) {
        DonHang old = donHangService.getById(donHang.getId());
        if (old != null) {
            donHang.setMaDH(old.getMaDH());
            donHang.setNgayTao(old.getNgayTao());
            donHang.setTongTien(old.getTongTien());
            donHang.setTrangThai(old.getTrangThai());

            try {
                Map<String, Object> body = new HashMap<>();
                body.put("idBans", idBans != null ? idBans : Collections.emptyList());
                body.put("idKhachHang", donHang.getKhachHang().getId());
                body.put("idNhanVien", donHang.getNhanVien().getId());
                body.put("tongTien", donHang.getTongTien());

                String url = apiBaseUrl.trim() + "/donhang/" + donHang.getId();
                restTemplate.put(url, body);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/donhang";
    }

    // ✅ XÓA ĐƠN HÀNG (giải phóng tất cả bàn liên quan)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        DonHang dh = donHangService.getById(id);
        if (dh != null) {
            // Giải phóng bàn chính
            if (dh.getBan() != null) {
                Ban ban = banService.getById(dh.getBan().getId());
                if (ban != null) {
                    ban.setTrangThai(Ban.TrangThaiBan.TRONG);
                    banService.save(ban);
                }
            }
            // Giải phóng các bàn phụ
            if (dh.getBansPhu() != null) {
                for (Ban b : dh.getBansPhu()) {
                    Ban ban = banService.getById(b.getId());
                    if (ban != null) {
                        ban.setTrangThai(Ban.TrangThaiBan.TRONG);
                        banService.save(ban);
                    }
                }
            }
        }
        donHangService.delete(id);
        return "redirect:/donhang";
    }

    // ✅ THANH TOÁN ĐƠN HÀNG
    @GetMapping("/thanhtoan/{id}")
    public String thanhToan(@PathVariable int id) {
        try {
            String url = apiBaseUrl.trim() + "/donhang/thanhtoan/" + id;
            restTemplate.put(url, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/donhang";
    }
}
