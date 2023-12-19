package com.webservice.library.controllers;

import com.webservice.library.data.vo.v1.security.AccountCredentialsVO;
import com.webservice.library.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Endpoint")
public class AuthController {

    @Autowired
    AuthServices authServices;

    @SuppressWarnings("rawTypes")
    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
        if(checkIfParamsIsNotNull(data))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        ResponseEntity token = authServices.signin(data);
        return token == null ? ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!") : token;
    }

    private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
}
