package org.example.stardevsthymeleaf.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.stardevsthymeleaf.dto.response.RespTargetTabunganDto;
import org.example.stardevsthymeleaf.dto.response.RespTransactionDto;
import org.example.stardevsthymeleaf.httpclient.TargetService;
import org.example.stardevsthymeleaf.httpclient.TransactionService;
import org.example.stardevsthymeleaf.utils.GlobalFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/target/detail")
public class DetailController {

    @Autowired
    private TargetService targetService;

    @Autowired
    private TransactionService transactionService;

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /** Helper ambil JWT dari request */
    private String extractJwt(WebRequest request, Model model) {
        String jwt = new GlobalFunction().tokenCheck(model, request);
        if ("redirect:/".equals(jwt)) return null;
        return jwt;
    }

    /** Halaman detail target tabungan + riwayat transaksi */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, WebRequest request) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        try {
            // Ambil detail target tabungan
            ResponseEntity<Object> responseTarget = targetService.findById(jwt, id);
            Map<String, Object> bodyTarget = (Map<String, Object>) responseTarget.getBody();
            if (bodyTarget == null || bodyTarget.get("data") == null) {
                return "redirect:/target";
            }
            Map<String, Object> dataTarget = (Map<String, Object>) bodyTarget.get("data");
            RespTargetTabunganDto targetDetail = mapper.convertValue(dataTarget, RespTargetTabunganDto.class);
            model.addAttribute("target", targetDetail);

            // Ambil riwayat transaksi berdasarkan targetId
            ResponseEntity<Object> responseTransaksi = transactionService.findByTargetId(jwt, id);
            Map<String, Object> bodyTransaksi = (Map<String, Object>) responseTransaksi.getBody();
            Object dataObj = bodyTransaksi.get("data");
            List<RespTransactionDto> transaksiList;

            if (dataObj instanceof List) {
                transaksiList = mapper.convertValue(dataObj, new TypeReference<List<RespTransactionDto>>() {});
            } else if (dataObj instanceof Map) {
                RespTransactionDto single = mapper.convertValue(dataObj, RespTransactionDto.class);
                transaksiList = List.of(single);
            } else {
                transaksiList = List.of(); // fallback kosong
            }

            model.addAttribute("transaksiList", transaksiList);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/target";
        }

        return "target/detail"; // file: resources/templates/target/detail.html
    }
}
