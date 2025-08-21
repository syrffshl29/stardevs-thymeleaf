package org.example.stardevsthymeleaf.httpclient;


import org.example.stardevsthymeleaf.dto.response.RespDetailTargetDto;
import org.example.stardevsthymeleaf.dto.validation.ValTargetTabunganDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "detail-service", url = "http://localhost:8080")
public interface DetailService {
    @GetMapping("/target/detail/{targetId}")
    ResponseEntity<Object>findDetailTarget(@RequestHeader("Authorization") String token,
                                               @PathVariable("targetId") Long id);
    /** Update target tabungan */
    @PostMapping("/target/{id}")
    ResponseEntity<Object> update(@RequestHeader("Authorization") String token,
                                  @PathVariable("id") Long id,
                                  @RequestBody ValTargetTabunganDto dto);

    /** Hapus target tabungan */
    @DeleteMapping("/target/{id}")
    ResponseEntity<Object> delete(@RequestHeader("Authorization") String token,
                                  @PathVariable("id") Long id);

}
