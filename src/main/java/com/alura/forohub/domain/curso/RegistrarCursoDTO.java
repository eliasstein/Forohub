package com.alura.forohub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarCursoDTO(
        @NotBlank
        @NotNull
        String nombre,
        @NotBlank
        @NotNull
        String categoria
) {
}
