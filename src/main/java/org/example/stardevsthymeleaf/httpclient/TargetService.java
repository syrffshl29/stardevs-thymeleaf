package org.example.stardevsthymeleaf.httpclient;

import feign.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.example.stardevsthymeleaf.dto.validation.ValTargetTabunganDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "target-service", url = "http://localhost:8080")
public interface TargetService {

    /** Simpan target tabungan baru */
    @PostMapping("/target")
    ResponseEntity<Object> save(@RequestHeader("Authorization") String token,
                                @RequestBody ValTargetTabunganDto dto);

    /** Ambil semua target tabungan */
    @GetMapping("/target/all")
    ResponseEntity<Object> findAll(@RequestHeader("Authorization") String token);

    /** Ambil target tabungan berdasarkan id */
    @GetMapping("/target/{id}")
    ResponseEntity<Object> findById(@RequestHeader("Authorization") String token,
                                    @PathVariable("id") Long id);
}
