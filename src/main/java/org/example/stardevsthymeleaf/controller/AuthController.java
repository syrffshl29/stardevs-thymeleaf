package org.example.stardevsthymeleaf.controller;

import jakarta.validation.Valid;
import org.bouncycastle.util.encoders.Base64;
import org.example.stardevsthymeleaf.config.OtherConfig;
import org.example.stardevsthymeleaf.dto.validation.LoginDto;
import org.example.stardevsthymeleaf.dto.validation.RegisterDto;
import org.example.stardevsthymeleaf.dto.validation.VerifyRegisterDto;
import org.example.stardevsthymeleaf.httpclient.AuthService;
import org.example.stardevsthymeleaf.security.BcryptImpl;
import org.example.stardevsthymeleaf.utils.GenerateStringMenu;
import org.example.stardevsthymeleaf.utils.GlobalFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


/** WAKTU SUBMIT DATA UNTUK PROSES LOGIN, FORGOTPASSWORD, REGISTRASI */
@Controller
@RequestMapping("auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@ModelAttribute("data") @Valid LoginDto loginDto,
                        BindingResult result, Model model, WebRequest request){

        String decodePassword = new String(Base64.decode(loginDto.getPassword()));
        GlobalFunction.matchingPattern(decodePassword,"^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@_#\\-\\$])[A-Za-z\\d@_#\\-\\$]{8,15}$",
                "password","Format Tidak Valid","data",result);

        Boolean isValid = false;
        if(OtherConfig.getEnableAutomationTesting().equals("y")){
            isValid = loginDto.getCaptcha().equals(loginDto.getHiddenCaptcha());
        }else {
            isValid = BcryptImpl.verifyHash(loginDto.getCaptcha(), loginDto.getHiddenCaptcha());
        }
        if(result.hasErrors() || !isValid){
            if(!isValid){
                model.addAttribute("captchaMessage", "Invalid Captcha");
            }
            GlobalFunction.getCaptchaLogin(loginDto);
            model.addAttribute("data",loginDto);
            return "auth/login";
        }
        loginDto.setPassword(decodePassword);
        ResponseEntity<Object> response = null;
        String menuNavBar = "";
        String jwt = "";
        try{
            response = authService.login(loginDto);
            Map<String,Object> map = (Map<String, Object>) response.getBody();
            Map<String,Object> mapData = (Map<String, Object>) map.get("data");
            jwt = (String) mapData.get("token");
            List<Map<String,Object>> listMenu = (List<Map<String, Object>>) mapData.get("menu");
            menuNavBar = new GenerateStringMenu().stringMenu(listMenu);

        }catch(Exception e){
            System.out.println("Error Login "+e.getMessage());
            model.addAttribute("data",loginDto);
            model.addAttribute("notif","Error Choy !!");
            return "auth/login";
        }
        String username = loginDto.getUsername();
        request.setAttribute("MENU_NAVBAR",menuNavBar,1);
        request.setAttribute("USR_NAME",username,1);
        request.setAttribute("JWT",jwt,1);

        model.addAttribute("MENU_NAVBAR",menuNavBar);
        model.addAttribute("USR_NAME",username);
        return "auth/home";
    }

    @PostMapping("/register")
    public String register(Model model,
                        @RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String namaLengkap,
                        @RequestParam String noHp,
                        @RequestParam String email,
                        @RequestParam String alamat,
                        @RequestParam String tanggalLahir,
                        @RequestParam MultipartFile file
                        ){
        password = new String(Base64.decode(password));

        Boolean isOk = regisValidation(model,username,password,namaLengkap,noHp,email,alamat,tanggalLahir);
        if(!isOk){
            setDataDefault(model,username,password,namaLengkap,noHp,email,alamat,tanggalLahir);
            return "auth/register";
        }
        ResponseEntity<Object> response1 = null;
        ResponseEntity<Object> response2 = null;
        String otp = "";
        VerifyRegisterDto verifyRegisterDto = new VerifyRegisterDto();
        try{

            response1 = authService.registration(mapToRegisterDto(username,password,namaLengkap,noHp,email,alamat,tanggalLahir));
            System.out.println("Response 1 : "+response1.getBody());
            Map<String,Object> map = (Map<String, Object>) response1.getBody();
            Map<String,Object> mapData = (Map<String, Object>) map.get("data");
            String strId = mapData.get("id").toString();

            if(OtherConfig.getEnableAutomationTesting().equals("y")){
                Object obj = mapData.get("otp");
                otp = obj==null?"":obj.toString();
                verifyRegisterDto.setOtp(otp);
            }
            Long idUser = Long.parseLong(strId);
            response2 = authService.registrationUpload(idUser,file);
            System.out.println("Response 2 : "+response2.getBody());
        }catch (Exception e){
            e.getMessage();
        }
        verifyRegisterDto.setEmail(email);
        model.addAttribute("data",verifyRegisterDto);
        return "auth/verify-register";
    }

    @PostMapping("/verify-register")
    public String verifyRegister(
            @Valid @ModelAttribute("data") VerifyRegisterDto verifyRegisterDto,
            Model model){
        ResponseEntity<Object> response = null;
        try{
            response = authService.verifyRegis(verifyRegisterDto);
        }catch (Exception e){
            e.getMessage();
        }
        return "redirect:/";
    }

    private RegisterDto mapToRegisterDto(String username, String password,
                                      String namaLengkap, String noHp, String  email,
                                      String alamat, String tanggalLahir
    ){
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

    private Boolean regisValidation(Model model, String username,String password,
                                   String namaLengkap, String noHp,String  email,
                                   String alamat,String tanggalLahir
    ){
        Boolean isValid = true;
        int intCounter = 0;
        int countFalse =0;
        intCounter = GlobalFunction.matchingPattern(model, username,"^([a-z0-9\\.]{8,16})$","usernameMessage",
                "Format Huruf kecil ,numeric dan titik saja min 8 max 16 karakter, ex : paulch.1234")==true?0:1;
        countFalse += intCounter;
        intCounter = GlobalFunction.matchingPattern(model, password,"^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$","passwordMessage",
                "Format minimal 1 angka, 1 huruf kecil, 1 huruf besar, 1 spesial karakter (_ \"Underscore\", - \"Hyphen\", # \"Hash\", atau $ \"Dollar\" atau @ \"At\") setelah 4 kondisi min 9 max 16 alfanumerik, contoh : aB4$12345")?0:1;
        countFalse += intCounter;
        intCounter = GlobalFunction.matchingPattern(model, namaLengkap,"^[a-zA-Z\\s]{4,70}$","namaLengkapMessage",
                "Hanya Alfabet dan spasi Minimal 4 Maksimal 70")?0:1;
        countFalse += intCounter;

        if(countFalse != 0){
            isValid = false;
        }
        return isValid;
    }

    private void setDataDefault(Model model, String username,String password,
                                    String namaLengkap, String noHp,String  email,
                                    String alamat,String tanggalLahir
    ){
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        model.addAttribute("namaLengkap",namaLengkap);
        model.addAttribute("noHp",noHp);
        model.addAttribute("email",email);
        model.addAttribute("alamat",alamat);
        model.addAttribute("tanggalLahir",tanggalLahir);
    }
}
