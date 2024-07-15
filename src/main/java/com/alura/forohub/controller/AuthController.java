package com.alura.forohub.controller;

import com.alura.forohub.domain.usuario.*;
import com.alura.forohub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacion", description = "Nos permite registrarnos y loguearnos para obtener el token que nos da acceso a la api.")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @Operation(
            summary = "Permite loguearnos usando uno de los usuarios de la base de datos")
    @PostMapping("/login")
    public ResponseEntity getToken(@RequestBody @Valid LoginUsuarioDTO loginData){
        Authentication token = new UsernamePasswordAuthenticationToken(loginData.correo(),
                loginData.pass());
        var usuarioAutenticado=authenticationManager.authenticate(token);
        var jwtToken = tokenService.generarToken((Usuario)usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new AuthResponseDTO(jwtToken));
    }

    @Operation(
            summary = "Permite registrar un usuario en la base de datos")
    @PostMapping("/register")
    public ResponseEntity getToken(@RequestBody @Valid RegistrarUsuarioDTO registerData){
        Usuario user = new Usuario(registerData);
        user.setPass(passwordEncoder.encode(registerData.pass()));
        if(usuarioRepository.findByEmailIgnoreCase(registerData.correo()).isPresent())
            throw new ValidationException("El correo ya existe en la base de datos");
        usuarioRepository.save(user);
        return  ResponseEntity.ok().build();

    }

}
