package ru.dartinc.restclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class UserController {

/*    @GetMapping("/main")
    public String mainPage(HttpServletRequest request, @Value("${server.rest.url}") String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpSession httpSession=request.getSession(false);
        AuthenticationResponseDto user=(AuthenticationResponseDto)httpSession.getAttribute("user");
        UserDto userDto = null;
        headers.add("Authorization",user.getToken());
        //HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
        HttpEntity<UserDto> entity=new HttpEntity(userDto,headers);
        try{
            ResponseEntity<UserDto> result= restTemplate.exchange(url+"1", HttpMethod.GET, entity, UserDto.class);
            return "redirect:/admin";
        } catch(HttpClientErrorException e){
            return "redirect:/user";
        }
    }*/
    @GetMapping("/user")
    public String userHello(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        try {
            String roles = (String) session.getAttribute("roles");
            if (roles.contains("ROLE_USER")) {
                return "userPage";
            } else {
                return "error403";
            }
        } catch (Exception e) {
            return "error403";
        }
    }

    @GetMapping("/admin")
    public String adminPage(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        try {
            String roles = (String) session.getAttribute("roles");
            if (roles.contains("ROLE_ADMIN")) {
                return "admin";
            } else {

                return "error403";
            }
        } catch (Exception e) {
            return "error403";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("token");
        session.removeAttribute("roles");
        return "index";
    }

    @GetMapping("/error")
    public String getError(){
        return "error";
    }

}
