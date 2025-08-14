package org.example.stardevsthymeleaf.controller;

import jakarta.validation.Valid;
import org.example.stardevsthymeleaf.config.OtherConfig;
import org.example.stardevsthymeleaf.dto.validation.LoginDto;
import org.example.stardevsthymeleaf.dto.validation.VerifyRegisterDto;
import org.example.stardevsthymeleaf.dto.validation.RegisterDto;
import org.example.stardevsthymeleaf.httpclient.AuthService;
import org.example.stardevsthymeleaf.security.BcryptImpl;
import org.example.stardevsthymeleaf.utils.GenerateStringMenu;
import org.example.stardevsthymeleaf.utils.GlobalFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /** LOGIN */
    @PostMapping("/login")
    public String login(@ModelAttribute("data") @Valid LoginDto loginDto,
                        BindingResult result, Model model, WebRequest request) {

        // Tidak ada Base64 decode, langsung pakai password plain text
        String rawPassword = loginDto.getPassword();

        // Validasi pola password
        GlobalFunction.matchingPattern(rawPassword,
                "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@_#\\-\\$])[A-Za-z\\d@_#\\-\\$]{8,15}$",
                "password", "Format Tidak Valid", "data", result);

        // Validasi captcha
        boolean isValidCaptcha = OtherConfig.getEnableAutomationTesting().equals("y")
                ? loginDto.getCaptcha().equals(loginDto.getHiddenCaptcha())
                : BcryptImpl.verifyHash(loginDto.getCaptcha(), loginDto.getHiddenCaptcha());

        if (result.hasErrors() || !isValidCaptcha) {
            if (!isValidCaptcha) {
                model.addAttribute("captchaMessage", "Invalid Captcha");
            }
            GlobalFunction.getCaptchaLogin(loginDto);
            model.addAttribute("data", loginDto);
            return "auth/login";
        }

        // Password tetap raw karena akan diverifikasi oleh service / DB
        loginDto.setPassword(rawPassword);

        ResponseEntity<Object> response;
        String menuNavBar;
        String jwt;

        try {
            response = authService.login(loginDto);
            Map<String, Object> map = (Map<String, Object>) response.getBody();
            Map<String, Object> mapData = (Map<String, Object>) map.get("data");
            jwt = (String) mapData.get("token");
            List<Map<String, Object>> listMenu = (List<Map<String, Object>>) mapData.get("menu");
            menuNavBar = new GenerateStringMenu().stringMenu(listMenu);
        } catch (Exception e) {
            System.out.println("Error Login " + e.getMessage());
            model.addAttribute("data", loginDto);
            model.addAttribute("notif", "Error Choy !!");
            return "auth/login";
        }

        String username = loginDto.getUsername();
        request.setAttribute("MENU_NAVBAR", menuNavBar, 1);
        request.setAttribute("USR_NAME", username, 1);
        request.setAttribute("JWT", jwt, 1);

        model.addAttribute("MENU_NAVBAR", menuNavBar);
        model.addAttribute("USR_NAME", username);
        return "target";
    }

    /** REGISTER */
    @PostMapping("/register")
    public String register(Model model,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String namaLengkap,
                           @RequestParam String noHp,
                           @RequestParam String email,
                           @RequestParam String alamat,
                           @RequestParam String tanggalLahir,
                           @RequestParam MultipartFile file) {

        // Jangan decode Base64, langsung validasi + hash
        boolean isOk = registerValidation(model, username, password, namaLengkap, noHp, email, alamat, tanggalLahir);
        if (!isOk) {
            setDataDefault(model, username, password, namaLengkap, noHp, email, alamat, tanggalLahir);
            return "auth/register";
        }

        // Hash password dengan bcrypt sebelum kirim ke service

        ResponseEntity<Object> response1;
        ResponseEntity<Object> response2;
        String otp = "";
        VerifyRegisterDto verifyRegisterDto = new VerifyRegisterDto();

        try {
            response1 = authService.registration(mapToRegisterDto(username, password, namaLengkap, noHp, email, alamat, tanggalLahir));
            System.out.println("Response 1 : " + response1.getBody());
            Map<String, Object> map = (Map<String, Object>) response1.getBody();
            Map<String, Object> mapData = (Map<String, Object>) map.get("data");
            String strId = mapData.get("id").toString();

            if (OtherConfig.getEnableAutomationTesting().equals("y")) {
                Object obj = mapData.get("otp");
                otp = obj == null ? "" : obj.toString();
                verifyRegisterDto.setOtp(otp);
            }

            Long idUser = Long.parseLong(strId);
            response2 = authService.registrationUpload(idUser, file);
            System.out.println("Response 2 : " + response2.getBody());
        } catch (Exception e) {
            e.getMessage();
        }

        verifyRegisterDto.setEmail(email);
        model.addAttribute("data", verifyRegisterDto);
        return "auth/verify-register";
    }

    @PostMapping("/verify-register")
    public String verifyRegister(@Valid @ModelAttribute("data") VerifyRegisterDto verifyRegisterDto, Model model) {
        ResponseEntity<Object> response;
        try {
            response = authService.verifyRegister(verifyRegisterDto);
        } catch (Exception e) {
            e.getMessage();
        }
        return "redirect:/";
    }

    private RegisterDto mapToRegisterDto(String username, String password,
                                         String namaLengkap, String noHp, String email,
                                         String alamat, String tanggalLahir) {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(username);
        registerDto.setPassword(password);
        registerDto.setNamaLengkap(namaLengkap);
        registerDto.setNoHp(noHp);
        registerDto.setEmail(email);
        registerDto.setAlamat(alamat);
        registerDto.setTanggalLahir(LocalDate.parse(tanggalLahir));
        return registerDto;
    }

    private Boolean registerValidation(Model model, String username, String password,
                                    String namaLengkap, String noHp, String email,
                                    String alamat, String tanggalLahir) {
        boolean isValid = true;
        int countFalse = 0;
        countFalse += GlobalFunction.matchingPattern(model, username, "^([a-z0-9\\.]{8,16})$", "usernameMessage",
                "Format Huruf kecil ,numeric dan titik saja min 8 max 16 karakter, ex : paulch.1234") ? 0 : 1;
        countFalse += GlobalFunction.matchingPattern(model, password, "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$", "passwordMessage",
                "Format minimal 1 angka, 1 huruf kecil, 1 huruf besar, 1 spesial karakter (_ \"Underscore\", - \"Hyphen\", # \"Hash\", atau $ \"Dollar\" atau @ \"At\") setelah 4 kondisi min 9 max 16 alfanumerik, contoh : aB4$12345") ? 0 : 1;
        countFalse += GlobalFunction.matchingPattern(model, namaLengkap, "^[a-zA-Z\\s]{4,70}$", "namaLengkapMessage",
                "Hanya Alfabet dan spasi Minimal 4 Maksimal 70") ? 0 : 1;

        if (countFalse != 0) {
            isValid = false;
        }
        return isValid;
    }

    private void setDataDefault(Model model, String username, String password,
                                String namaLengkap, String noHp, String email,
                                String alamat, String tanggalLahir) {
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("namaLengkap", namaLengkap);
        model.addAttribute("noHp", noHp);
        model.addAttribute("email", email);
        model.addAttribute("alamat", alamat);
        model.addAttribute("tanggalLahir", tanggalLahir);
    }
}
