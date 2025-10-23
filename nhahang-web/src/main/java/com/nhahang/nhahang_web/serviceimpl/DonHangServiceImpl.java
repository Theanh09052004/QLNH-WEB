package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.model.DonHang;
import com.nhahang.nhahang_web.service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DonHangServiceImpl implements DonHangService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}/donhang")
    private String apiUrl;

    @Override
    public List<DonHang> getAll() {
        return Arrays.asList(
            Objects.requireNonNull(restTemplate.getForObject(apiUrl, DonHang[].class))
        );
    }

    @Override
    public DonHang getById(int id) {
        return restTemplate.getForObject(apiUrl + "/" + id, DonHang.class);
    }

    @Override
    public void save(DonHang donHang) {
        try {
            // ✅ Map dữ liệu gửi đi cho API (tránh lỗi enum hoặc object lồng)
            Map<String, Object> body = new HashMap<>();
            body.put("id", donHang.getId());
            body.put("idKhachHang", donHang.getKhachHang() != null ? donHang.getKhachHang().getId() : null);
            body.put("idNhanVien", donHang.getNhanVien() != null ? donHang.getNhanVien().getId() : null);
            body.put("tongTien", donHang.getTongTien());
            body.put("idBans", donHang.getIdBans() != null ? donHang.getIdBans() : Collections.emptyList());
            body.put("idBanChinh", donHang.getIdBanChinh());
            body.put("trangThai", donHang.getTrangThai() != null ? donHang.getTrangThai().name() : "DANG_XU_LY");

            if (donHang.getId() == 0) {
                // ➕ Thêm mới
                restTemplate.postForObject(apiUrl, body, DonHang.class);
            } else {
                // ✏️ Cập nhật
                restTemplate.put(apiUrl + "/" + donHang.getId(), body);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi lưu đơn hàng: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            restTemplate.delete(apiUrl + "/" + id);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi xóa đơn hàng ID: " + id);
        }
    }

    @Override
    public String generateMaDH() {
        return "DH" + System.currentTimeMillis();
    }

    @Override
    public List<DonHang> search(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAll();
            }

            String url = apiUrl + "/search?keyword=" + keyword.trim();
            DonHang[] result = restTemplate.getForObject(url, DonHang[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi tìm kiếm đơn hàng: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void thanhToan(int id) {
        try {
            restTemplate.put(apiUrl + "/thanhtoan/" + id, null);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi thanh toán đơn hàng ID: " + id);
        }
    }
}
