package org.example.stardevsthymeleaf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("target")
public class TargetController {

    @Autowired
    private TargetService targetService;

    /** Halaman utama target tabungan */
    @GetMapping
    public String defaultPage(Model model, WebRequest request) {
        String jwt = new GlobalFunction().tokenCheck(model, request);
        if ("redirect:/".equals(jwt)) return jwt;

        try {
            ResponseEntity<Object> response = targetService.findAll(jwt);
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) body.get("data");

            // Ubah menjadi list DTO
            List<ValTargetTabunganDto> targetList = dataList.stream()
                    .map(data -> new ObjectMapper().convertValue(data, ValTargetTabunganDto.class))
                    .toList();

            model.addAttribute("targetList", targetList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("targetList", List.of());
        }

        new GlobalFunction().insertGlobalAttribut(model, request, "TARGET TABUNGAN");
        return "main";
    }

    /** Open form Add target tabungan */
    @GetMapping("/a")
    public String openModalAdd(Model model, WebRequest request) {
        String jwt = new GlobalFunction().tokenCheck(model, request);
        if ("redirect:/".equals(jwt)) return jwt;

        model.addAttribute("data", new ValTargetTabunganDto());
        return "target/add";
    }

    /** Save target tabungan baru */
    @PostMapping
    public String save(@Valid @ModelAttribute("data") ValTargetTabunganDto valDto,
                       BindingResult bindingResult,
                       Model model,
                       WebRequest request) {
        String jwt = new GlobalFunction().tokenCheck(model, request);
        if ("redirect:/".equals(jwt)) return jwt;

        if (bindingResult.hasErrors()) {
            model.addAttribute("data", valDto);
            return "target/add";
        }

        try {
            targetService.save(jwt, valDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("data", valDto);
            return "target/add";
        }

        return "err-response/200";
    }

    /** Open form Edit target tabungan */
    @GetMapping("/e/{id}")
    public String openModalEdit(Model model, @PathVariable Long id, WebRequest request) {
        String jwt = new GlobalFunction().tokenCheck(model, request);
        if ("redirect:/".equals(jwt)) return jwt;

        try {
            ResponseEntity<Object> response = targetService.findById(jwt, id);
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            Map<String, Object> data = (Map<String, Object>) body.get("data");

            ValTargetTabunganDto valDto = new ObjectMapper().convertValue(data, ValTargetTabunganDto.class);
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
        String jwt = new GlobalFunction().tokenCheck(model, request);
        if ("redirect:/".equals(jwt)) return jwt;

        if (bindingResult.hasErrors()) {
            model.addAttribute("data", valDto);
            model.addAttribute("ids", id);
            return "target/edit";
        }

        try {
            targetService.update(jwt, id, valDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("data", valDto);
            model.addAttribute("ids", id);
            return "target/edit";
        }

        return "err-response/200";
    }

    /** Delete target tabungan */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model, WebRequest request) {
        String jwt = new GlobalFunction().tokenCheck(model, request);
        if ("redirect:/".equals(jwt)) return jwt;

        try {
            targetService.delete(jwt, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "err-response/200";
    }
}
