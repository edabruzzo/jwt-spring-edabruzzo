package br.com.abruzzo.jwt_spring.service;

import br.com.abruzzo.jwt_spring.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDTO = findUserByUsername(username);
        if(userDTO == null)
            throw new UsernameNotFoundException(username);
        return new User(userDTO.getUsername(),userDTO.getPassword(), Collections.emptyList());
    }


    private UserDTO findUserByUsername(String username) {
        UserDTO user = new UserDTO();
        user.setUsername("admin");
        user.setPassword(this.bCryptPasswordEncoder.encode("admin"));
        return user;
    }


    public List<UserDTO> listUsers(){
        ArrayList<UserDTO> usersList= new ArrayList<>();
        usersList.add(findUserByUsername("admin"));
        return usersList;
    }


}
