package org.example.stardevsthymeleaf.httpclient;

import feign.Response;
import org.example.stardevsthymeleaf.dto.validation.ValTargetTabunganDto;
import org.springframework.cloud.openfeign.FeignClient;
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

    /** Update target tabungan */
    @PutMapping("/target/{id}")
    ResponseEntity<Object> update(@RequestHeader("Authorization") String token,
                                  @PathVariable("id") Long id,
                                  @RequestBody ValTargetTabunganDto dto);

    /** Hapus target tabungan */
    @DeleteMapping("/target/{id}")
    ResponseEntity<Object> delete(@RequestHeader("Authorization") String token,
                                  @PathVariable("id") Long id);
}
