package com.alura.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarTopicoDTO(
        @NotNull
        Boolean status,
        @NotBlank
        @NotNull
        String titulo,
        @NotBlank
        @NotNull
        String mensaje

) {



}
