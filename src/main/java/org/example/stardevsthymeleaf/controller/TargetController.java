package org.example.stardevsthymeleaf.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.stardevsthymeleaf.dto.response.RespTargetTabunganDto;
import org.example.stardevsthymeleaf.dto.validation.ValTargetTabunganDto;
import org.example.stardevsthymeleaf.httpclient.TargetService;
import org.example.stardevsthymeleaf.utils.GlobalFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/target")
public class TargetController {

    @Autowired
    private TargetService targetService;

    private final ObjectMapper mapper = new ObjectMapper();

    /** Helper untuk ambil JWT dari request */
    private String extractJwt(WebRequest request, Model model) {
        String jwt = new GlobalFunction().tokenCheck(model, request);
        if ("redirect:/".equals(jwt)) return null;
        return jwt;
    }

    /** Helper untuk convert body response ke List */
    private List<RespTargetTabunganDto> mapToTargetList(Object bodyData) {
        ObjectMapper mapper = new ObjectMapper();

        // Daftarkan modul Java 8 date/time
        mapper.registerModule(new JavaTimeModule());

        // Supaya Jackson bisa menerima field yang namanya berbeda (misal targetName -> nama_target)
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.convertValue(bodyData, new TypeReference<List<RespTargetTabunganDto>>() {});
    }

    /** Halaman utama target tabungan */
    @GetMapping
    public String defaultPage(
            Model model,
            WebRequest request,
            @RequestParam(defaultValue = "targetName") String sort,
            @RequestParam(defaultValue = "asc") String dir
    ) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/target";

        try {
            ResponseEntity<Object> response = targetService.findAll(jwt);
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            List<RespTargetTabunganDto> targetList = mapToTargetList(body.get("data"));

            if (targetList != null) {
                // Hitung progress
                targetList.forEach(target -> {
                    double progress = 0;
                    if (target.getDanaTerkumpul() != null && target.getHargaTarget() != null && target.getHargaTarget() > 0) {
                        progress = target.getDanaTerkumpul() / target.getHargaTarget() * 100;
                    }
                    target.setProgress(progress);
                });

                // Sorting dinamis berdasarkan parameter 'sort' dan 'dir'
                Comparator<RespTargetTabunganDto> comparator;
                switch (sort) {
                    case "progress":
                        comparator = Comparator.comparing(RespTargetTabunganDto::getProgress);
                        break;
                    case "targetName":
                    default:
                        comparator = Comparator.comparing(RespTargetTabunganDto::getTargetName, String.CASE_INSENSITIVE_ORDER);
                        break;
                }

                if ("desc".equalsIgnoreCase(dir)) {
                    comparator = comparator.reversed();
                }

                targetList.sort(comparator);
            }

            model.addAttribute("targetList", targetList != null ? targetList : List.of());
            model.addAttribute("sort", sort);
            model.addAttribute("dir", dir);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("targetList", List.of());
            model.addAttribute("sort", "targetName");
            model.addAttribute("dir", "asc");
        }

        return "target";
    }


    /** Open form Add target tabungan */
    @GetMapping("/a")
    public String openModalAdd(Model model, WebRequest request) {
        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        model.addAttribute("data", new ValTargetTabunganDto());
        return "target/add";
    }

    /** Save target tabungan baru */
    @PostMapping
    public String save(@Valid @ModelAttribute("data") ValTargetTabunganDto valDto,
                       BindingResult bindingResult,
                       Model model,
                       WebRequest request) {

        String jwt = extractJwt(request, model);
        if (jwt == null) return "redirect:/";

        if (bindingResult.hasErrors()) {
            model.addAttribute("data", valDto);
            return "target/add";
        }

        try {
            targetService.save(jwt, valDto);
            return "redirect:/target"; // redirect setelah save sukses
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("data", valDto);
            return "target/add";
        }
    }


}
