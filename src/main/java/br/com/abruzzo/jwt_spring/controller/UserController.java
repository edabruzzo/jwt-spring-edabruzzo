package br.com.abruzzo.jwt_spring.controller;


import br.com.abruzzo.jwt_spring.dto.UserDTO;
import br.com.abruzzo.jwt_spring.service.UserDetailServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/users")
public class UserController {


    private final UserDetailServiceImplementation userDetailsService;

    @GetMapping
    public List<UserDTO> listAllUsers(){
        return this.userDetailsService.listUsers();
    }


}
