package org.example.stardevsthymeleaf.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import org.example.stardevsthymeleaf.dto.response.RespTransaksiTabunganDto;
import org.example.stardevsthymeleaf.dto.validation.ValTransactionDto;
import org.example.stardevsthymeleaf.dto.validation.ValWithDrawDto;
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
@RequestMapping("transaksi")
public class TransactionController {

    @Autowired
    private TransactionService transactionService; // Feign Client

    private final ObjectMapper mapper = new ObjectMapper();

    /** Helper untuk ambil JWT dari request */
    private String extractJwt(WebRequest request, Model model) {
        String jwt = new GlobalFunction().tokenCheck(model, request);
        if ("redirect:/".equals(jwt)) return null;
        return jwt;
    }

    /** Helper untuk convert body response ke List */
    private List<RespTransaksiTabunganDto> mapToTransactionList(Object bodyData) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.convertValue(bodyData, new TypeReference<List<RespTransaksiTabunganDto>>() {});
    }

    /** Halaman utama transaksi */
    @GetMapping
    public String defaultPage(Model model, WebRequest request) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        try {
            ResponseEntity<Object> response = transactionService.findAll(jwt);
            Object bodyObj = response.getBody();

            List<RespTransaksiTabunganDto> transaksiList = List.of(); // default kosong

            if (bodyObj instanceof Map<?, ?> bodyMap) {
                // Jika respons berupa objek dengan key "data"
                Object data = bodyMap.get("data");
                if (data != null) {
                    transaksiList = convertToTransactionList(data);
                }
            } else if (bodyObj instanceof List<?>) {
                // Jika respons langsung berupa list
                transaksiList = convertToTransactionList(bodyObj);
            }

            model.addAttribute("transaksiList", transaksiList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("transaksiList", List.of());
        }

        return "transaction";
    }

    /**
     * Helper untuk konversi objek menjadi List<RespTransaksiTabunganDto>
     */
    private List<RespTransaksiTabunganDto> convertToTransactionList(Object source) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.convertValue(source, new TypeReference<List<RespTransaksiTabunganDto>>() {});
    }


    /** Open form Add transaksi */
    @GetMapping("/add/{targetId}")
    public String openModalAdd(@PathVariable Long targetId, Model model, WebRequest request) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        ValTransactionDto valDto = new ValTransactionDto();
        valDto.setTargetTabunganId(targetId); // langsung isi targetId supaya form tahu targetnya
        model.addAttribute("data", valDto);

        return "transaksi/add"; // pakai template yang sama
    }
    @GetMapping("/withdraw/{targetId}")
    public String openModalAddWithTarget(@PathVariable Long targetId, Model model, WebRequest request) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        ValWithDrawDto valDto = new ValWithDrawDto();
        valDto.setTargetTabunganId(targetId); // set targetId supaya form tahu targetnya
        model.addAttribute("data", valDto);

        return "transaksi/withdraw"; // pakai template yang sama
    }

    /** Save transaksi baru */
    @PostMapping("/add/{targetId}")
    public String save(@Valid @ModelAttribute("data") ValTransactionDto valDto,
                       BindingResult bindingResult,
                       Model model,
                       WebRequest request) {

        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        if (bindingResult.hasErrors()) {
            model.addAttribute("data", valDto);
            return "transaksi/add";
        }

        try {
            transactionService.save(jwt, valDto);
            return "redirect:/target/detail/{targetId}"; // redirect setelah save sukses
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("data", valDto);
            return "transaksi/add";
        }

    }

    @PostMapping("/withdraw/{targetId}")
    public String withdraw(@Valid @ModelAttribute("data") ValWithDrawDto valDto,
                           BindingResult bindingResult,
                           Model model,
                           WebRequest request) {

        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        if (bindingResult.hasErrors()) {
            model.addAttribute("data", valDto);
            return "transaksi/withdraw";
        }

        try {
            transactionService.withdraw(jwt, valDto);
            return "redirect:/target/detail/{targetId}"; // redirect setelah save sukses
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("data", valDto);
            return "transaksi/add";
        }
    }
    /** Open form Edit transaksi */
    @GetMapping("/e/{id}")
    public String openModalEdit(Model model, @PathVariable Long id, WebRequest request) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        try {
            ResponseEntity<Object> response = transactionService.findById(jwt, id);
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            Map<String, Object> data = (Map<String, Object>) body.get("data");

            ValTransactionDto valDto = mapper.convertValue(data, ValTransactionDto.class);
            model.addAttribute("data", valDto);
            model.addAttribute("ids", id);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("data", new ValTransactionDto());
            model.addAttribute("ids", id);
        }

        return "transaksi/edit";
    }

    /** Update transaksi */
    @PostMapping("/{id}")
    public String edit(@Valid @ModelAttribute("data") ValTransactionDto valDto,
                       BindingResult bindingResult,
                       Model model,
                       @PathVariable Long id,
                       WebRequest request) {

        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        if (bindingResult.hasErrors()) {
            model.addAttribute("data", valDto);
            model.addAttribute("ids", id);
            return "transaksi/edit";
        }

        try {
            transactionService.update(jwt, id, valDto);
            return "redirect:/transaksi"; // redirect setelah update sukses
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("data", valDto);
            model.addAttribute("ids", id);
            return "transaksi/edit";
        }
    }

    /** Delete transaksi */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model, WebRequest request) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        try {
            transactionService.delete(jwt, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/transaksi";
    }
}
