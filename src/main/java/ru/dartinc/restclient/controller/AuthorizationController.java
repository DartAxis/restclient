package ru.dartinc.restclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.dartinc.restclient.dto.AuthenticationRequestDto;
import ru.dartinc.restclient.dto.AuthenticationResponseDto;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class AuthorizationController {

    private final String serverUrlLogin;
    private final String startWord;

    public AuthorizationController(@Value("${server.rest.login.url}") String serverUrlLogin,
                                   @Value("${jwt.token.secret.start.word}") String startWord) {
        this.serverUrlLogin = serverUrlLogin;
        this.startWord = startWord;
    }

    @GetMapping(value = "/")
    public String auth(@ModelAttribute("message") String message) {
        return "index";
    }

    @PostMapping("/")
    public String getToken(AuthenticationRequestDto authenticationRequestDto, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<AuthenticationRequestDto> entity = new HttpEntity<>(authenticationRequestDto, headers);
        HttpSession session = request.getSession();
        try {
            ResponseEntity<AuthenticationResponseDto> result = restTemplate.exchange(serverUrlLogin, HttpMethod.POST, entity, AuthenticationResponseDto.class);
            AuthenticationResponseDto userDtoFromBody = result.getBody();
            String tokenCode = userDtoFromBody.getToken();
            userDtoFromBody.setToken(startWord + tokenCode);
            log.info("IN Authorizationcontroller : Sign in user : {}", userDtoFromBody.getUsername());
            log.info("IN Authorizationcontroller : Sign in with token {}", userDtoFromBody.getToken());
            log.info("IN Authorizationcontroller : Sign in with roles {}", userDtoFromBody.getRoles());
            session.setAttribute("username", userDtoFromBody.getUsername());
            session.setAttribute("token",userDtoFromBody.getToken());
            session.setAttribute("roles",userDtoFromBody.getRoles());

            if(userDtoFromBody.getRoles().contains("ROLE_ADMIN")){
                return "redirect:/admin";
            } else {
                return "redirect:/user";
            }

        } catch (HttpClientErrorException e) {
            System.out.println("вернулся статус ошибки");
            session.setAttribute("error","erro403");
            return "index";
        }
    }

    @GetMapping("/error403")
    public String error403(){
        return "error403";
    }
}
