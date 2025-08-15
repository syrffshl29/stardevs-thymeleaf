//package org.example.stardevsthymeleaf.httpclient;
//
//
//import org.example.stardevsthymeleaf.dto.validation.LoginDto;
//import org.example.stardevsthymeleaf.dto.validation.RegisterDto;
//import org.example.stardevsthymeleaf.dto.validation.VerifyRegisterDto;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//
//@FeignClient(name = "auth-services",url = "${host.rest.api}"+"/auth")
//public interface AuthService {
//
//    @PostMapping("/login")
//    public ResponseEntity<Object> login(@RequestBody LoginDto LoginDto);
//
//    @PostMapping("/register")
//    public ResponseEntity<Object> registration(@RequestBody RegisterDto registerDto);
//
//    @PostMapping(value = "/register/upload/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Object> registrationUpload(@PathVariable Long id,@RequestPart MultipartFile file);
//
//    @PostMapping("/verify-register")
//    public ResponseEntity<Object> verifyRegister(@RequestBody VerifyRegisterDto verifyRegisterDto);
//}