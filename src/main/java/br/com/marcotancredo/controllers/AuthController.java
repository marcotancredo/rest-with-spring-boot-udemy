package br.com.marcotancredo.controllers;

import br.com.marcotancredo.data.model.User;
import br.com.marcotancredo.repository.UserRepository;
import br.com.marcotancredo.security.AccountCredentialsVO;
import br.com.marcotancredo.security.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Authenticate a user by credentials")
    @PostMapping(value = "/signin", produces = {"application/json", "application/xml","application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> create(@RequestBody AccountCredentialsVO credentialsVO) {
        try{
            String username = credentialsVO.getUsername();
            String password = credentialsVO.getPassword();

            Authentication authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            try {
                authenticationManager.authenticate(authenticationToken);
            } catch (Exception e) {
                e.printStackTrace();
            }

            User user = userRepository.findByUserName(username);

            String token;

            if (user != null) {
                token = jwtTokenProvider.createToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }

            HashMap<Object, Object> model = new HashMap<>();
            model.putIfAbsent("username", username);
            model.putIfAbsent("token", token);

            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

}
