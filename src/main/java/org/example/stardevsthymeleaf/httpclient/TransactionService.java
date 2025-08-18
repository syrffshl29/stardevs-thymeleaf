package org.example.stardevsthymeleaf.httpclient;

import org.example.stardevsthymeleaf.dto.response.RespTransactionDto;
import org.example.stardevsthymeleaf.dto.validation.ValTransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "transaction-service", url = "http://localhost:8080/transaksi")
public interface TransactionService {

        /** Ambil semua transaksi */
        @GetMapping("/all")
        ResponseEntity<Object> findAll(@RequestHeader("Authorization") String jwt);

        /** Ambil transaksi berdasarkan username */
        @GetMapping("/username/{username}")
        ResponseEntity<Object> findByUsername(@RequestHeader("Authorization") String jwt,
                                              @PathVariable("username") String username);

        /** Ambil transaksi berdasarkan ID */
        @GetMapping("/{id}")
        ResponseEntity<Object> findById(@RequestHeader("Authorization") String jwt,
                                        @PathVariable("id") Long id);

        /** Simpan transaksi baru */
        @PostMapping("/save")
        ResponseEntity<Object> save(@RequestHeader("Authorization") String jwt,
                                    @RequestBody ValTransactionDto valTransactionDto);

        /** Update transaksi */
        @PutMapping("/update/{id}")
        ResponseEntity<Object> update(@RequestHeader("Authorization") String jwt,
                                      @PathVariable("id") Long id,
                                      @RequestBody ValTransactionDto valTransactionDto);

        /** Delete transaksi */
        @DeleteMapping("/delete/{id}")
        ResponseEntity<Object> delete(@RequestHeader("Authorization") String jwt,
                                      @PathVariable("id") Long id);
    }
