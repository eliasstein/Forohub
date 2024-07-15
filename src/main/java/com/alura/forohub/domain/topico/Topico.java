package com.alura.forohub.domain.topico;

import com.alura.forohub.domain.curso.Curso;
import com.alura.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Table(name="topicos")
@Entity(name="Topico")
@Data
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name="fecha_creacion")
    private LocalDateTime fechaCreacion;
    private boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="autor_id")
    private Usuario autor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="curso_id")
    private Curso curso;

    public Topico(){}
    public Topico(RegistrarTopicoDTO dto, Usuario user, Curso curso){
        this.fechaCreacion= LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        this.autor=user;
        this.curso = curso;
        this.status = true;
        this.mensaje = dto.mensaje();
        this.titulo=dto.titulo();
    }
    public void actualizarTopico(ActualizarTopicoDTO dto){
        this.status = dto.status();
        this.mensaje = dto.mensaje();
        this.titulo=dto.titulo();
    }
    public void desactivarTopico(){
        this.status=false;
    }

}
