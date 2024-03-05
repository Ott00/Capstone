package otmankarim.Capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.payloads.LoginResponseDTO;
import otmankarim.Capstone.payloads.UserDTO;
import otmankarim.Capstone.payloads.UserLoginDTO;
import otmankarim.Capstone.services.AuthSRV;

@RestController
@RequestMapping("/auth")
public class AuthCTRL {
    @Autowired
    public AuthSRV authSRV;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Validated UserLoginDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new LoginResponseDTO(authSRV.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    public User register(@RequestBody @Validated UserDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.authSRV.save(payload);
    }
}
