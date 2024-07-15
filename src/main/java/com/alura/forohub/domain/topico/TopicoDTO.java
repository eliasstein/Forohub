package com.alura.forohub.domain.topico;

import com.alura.forohub.domain.curso.Curso;
import com.alura.forohub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record TopicoDTO(
                        Long id,
                        String titulo,
                        String mensaje,
                        LocalDateTime fechaCreacion,
                        boolean status,
                        Usuario autor,
                        Curso curso)
{
    public TopicoDTO(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.isStatus(),
                topico.getAutor(),
                topico.getCurso());
    }
}
