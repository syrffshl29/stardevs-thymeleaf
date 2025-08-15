//package org.example.stardevsthymeleaf.controller;
//
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.example.stardevsthymeleaf.config.OtherConfig;
//import org.example.stardevsthymeleaf.dto.validation.LoginDto;
//import org.example.stardevsthymeleaf.utils.GlobalFunction;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.context.request.WebRequest;
//
//
//@Controller
//@RequestMapping("/")
//public class DashboardController {
//
//    @GetMapping("Login")
//    public String index(Model model){
//
//        LoginDto loginDTO = new LoginDto();
//        GlobalFunction.getCaptchaLogin(loginDTO);
//        loginDTO.setUsername("syarifaf29");
//        if(OtherConfig.getEnableAutomationTesting().equals("y")){
//            loginDTO.setCaptcha(loginDTO.getHiddenCaptcha());
//        }
//        model.addAttribute("data",loginDTO);
//        return "auth/login";
//    }
//
//    @GetMapping("/")
//    public String home(Model model, WebRequest request){
//
//        String username = (String) request.getAttribute("USR_NAME",1);
//        String menuNavbar = (String) request.getAttribute("MENU_NAVBAR",1);
//
//        model.addAttribute("USR_NAME", username);
//        model.addAttribute("MENU_NAVBAR", menuNavbar);
//        return "home";
//    }
//
//    @GetMapping("/register")
//    public String regis(){
//
//        return "auth/register";
//    }
//
//    @GetMapping("/logout")
//    public String destroySession(HttpServletRequest request){
//        request.getSession().invalidate();
//        return "redirect:/";
//    }
//}
