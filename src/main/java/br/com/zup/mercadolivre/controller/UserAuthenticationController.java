package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.dto.response.AuthenticationTokenOutputDto;
import br.com.zup.mercadolivre.security.TokenManager;
import br.com.zup.mercadolivre.dto.request.LoginInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserAuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationTokenOutputDto> authenticate(@RequestBody LoginInputDTO loginInfo) {

        UsernamePasswordAuthenticationToken authenticationToken = loginInfo.build();

        try {
            Authentication authentication = authManager.authenticate(authenticationToken);
            String jwt = tokenManager.generateToken(authentication);

            AuthenticationTokenOutputDto tokenResponse = new AuthenticationTokenOutputDto("Bearer", jwt);
            return ResponseEntity.ok(tokenResponse);

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }
}
