package com.github.marvindaviddiaz.service;

import com.github.marvindaviddiaz.bo.Usuario;
import com.github.marvindaviddiaz.dao.AuthDao;
import com.github.marvindaviddiaz.dto.UsuarioDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.security.sasl.AuthenticationException;

@Singleton
public class AuthService {

    @Inject
    private AuthDao authDao;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioDTO login(String username, String password) throws AuthenticationException {

        UsuarioDTO user = authDao.getUser(username);
        if (user == null) {
            throw new AuthenticationException("Credenciales Inválidas");
        }

        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches) {
            throw new AuthenticationException("Credenciales Inválidas");
        }

        return user;
    }

//    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("Test123$"));
//    }
}
