package com.alura.forohub.domain.topico;

public record ObtenerTopicosDTO(Long id,
                                boolean status,
                                String titulo,
                                String mensaje) {

    public ObtenerTopicosDTO(Topico topico) {
        this(   topico.getId(),
                topico.isStatus(),
                topico.getTitulo(),
                topico.getMensaje()
        );
    }
}
