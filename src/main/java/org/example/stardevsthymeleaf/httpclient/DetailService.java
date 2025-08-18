package org.example.stardevsthymeleaf.httpclient;


import org.example.stardevsthymeleaf.dto.validation.ValDetailTargetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "detail-service", url = "http://localhost:8080")
public interface DetailService {
    @PutMapping("/detail")
    ResponseEntity<Object> save(@RequestHeader("Authorization")String token,
                                @RequestBody ValDetailTargetDto dto);
}
