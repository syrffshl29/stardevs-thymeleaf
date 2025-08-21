package org.example.stardevsthymeleaf.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import org.example.stardevsthymeleaf.dto.response.RespDetailTargetDto;
import org.example.stardevsthymeleaf.dto.response.RespTargetTabunganDto;
import org.example.stardevsthymeleaf.dto.response.RespTransaksiTabunganDto;
import org.example.stardevsthymeleaf.dto.validation.ValTargetTabunganDto;
import org.example.stardevsthymeleaf.httpclient.DetailService;
import org.example.stardevsthymeleaf.httpclient.TargetService;
import org.example.stardevsthymeleaf.httpclient.TransactionService;
import org.example.stardevsthymeleaf.utils.GlobalFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/target/detail")
public class DetailController {

    @Autowired
    private DetailService detailService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TargetService targetService;

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /** Helper ambil JWT dari request */
    private String extractJwt(WebRequest request, Model model) {
        String jwt = new GlobalFunction().tokenCheck(model, request);
        if ("redirect:/home".equals(jwt)) return null;
        return jwt;
    }

    /** Halaman detail target tabungan + riwayat transaksi */
    @GetMapping("/{targetId}")
    public String findDetailTarget(@PathVariable Long targetId, Model model, WebRequest request) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/home";

        try {
            // Ambil detail target tabungan
            ResponseEntity<Object> responseTarget = detailService.findDetailTarget(jwt, targetId);
            Map<String, Object> bodyTarget = (Map<String, Object>) responseTarget.getBody();
            if (bodyTarget == null || bodyTarget.get("data") == null) {
                return "redirect:/target";
            }
            Map<String, Object> dataTarget = (Map<String, Object>) bodyTarget.get("data");
            RespDetailTargetDto targetDetail = mapper.convertValue(dataTarget, RespDetailTargetDto.class);
            model.addAttribute("target", targetDetail);

            // Ambil riwayat transaksi berdasarkan targetId
            ResponseEntity<Object> responseTransaksi = transactionService.findByTargetId(jwt, targetId);
            Map<String, Object> bodyTransaksi = (Map<String, Object>) responseTransaksi.getBody();
            Object dataObj = bodyTransaksi.get("data");
            List<RespTransaksiTabunganDto> transaksiList;

            if (dataObj instanceof List) {
                transaksiList = mapper.convertValue(dataObj, new TypeReference<List<RespTransaksiTabunganDto>>() {});
            } else if (dataObj instanceof Map) {
                RespTransaksiTabunganDto single = mapper.convertValue(dataObj, RespTransaksiTabunganDto.class);
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
    /** Open form Edit target tabungan */
    @GetMapping("/e/{id}")
    public String openModalEdit(Model model, @PathVariable Long id, WebRequest request) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        try {
            ResponseEntity<Object> response = targetService.findById(jwt, id);
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            Map<String, Object> data = (Map<String, Object>) body.get("data");

            ValTargetTabunganDto valDto = mapper.convertValue(data, ValTargetTabunganDto.class);
            model.addAttribute("data", valDto);
            model.addAttribute("ids", id);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("data", new ValTargetTabunganDto());
            model.addAttribute("ids", id);
        }

        return "target/edit";
    }

    /** Update target tabungan */
    @PostMapping("/{id}")
    public String edit(@Valid @ModelAttribute("data") ValTargetTabunganDto valDto,
                       BindingResult bindingResult,
                       Model model,
                       @PathVariable Long id,
                       WebRequest request) {

        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        if (bindingResult.hasErrors()) {
            model.addAttribute("data", valDto);
            model.addAttribute("ids", id);
            return "target/detail";
        }

        try {
            detailService.update(jwt, id, valDto);
            return "redirect:/target/detail/{id}"; // redirect setelah update sukses
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("data", valDto);
            model.addAttribute("ids", id);
            return "target/edit";
        }
    }

    /** Delete target tabungan */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model, WebRequest request) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        try {
            detailService.delete(jwt, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/target";
    }
}