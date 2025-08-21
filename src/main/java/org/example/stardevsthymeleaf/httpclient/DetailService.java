package org.example.stardevsthymeleaf.httpclient;


import org.example.stardevsthymeleaf.dto.response.RespDetailTargetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "detail-service", url = "http://localhost:8080")
public interface DetailService {
    @GetMapping("/target/detail/{targetId}")
    ResponseEntity<Object>findDetailTarget(@RequestHeader("Authorization") String token,
                                               @PathVariable("targetId") Long id);
}
