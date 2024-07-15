package com.alura.forohub.domain.topico;

import com.alura.forohub.domain.curso.RegistrarCursoDTO;
import com.alura.forohub.domain.usuario.RegistrarUsuarioDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarTopicoDTO(
        @NotBlank
        @NotNull
        String titulo,
        @NotBlank
        @NotNull
        String mensaje,
        @JsonAlias("usuario")
        @NotNull
        @Valid
        RegistrarUsuarioDTO autor,
        @JsonAlias("curso")
        @NotNull
        @Valid
        RegistrarCursoDTO curso
){
}
