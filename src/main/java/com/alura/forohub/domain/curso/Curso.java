package com.alura.forohub.domain.curso;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Table(name="cursos")
@Entity(name="Curso")
@Data
@Getter
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String categoria;

    public Curso(){}
    public Curso(RegistrarCursoDTO dto){
        this.nombre=dto.nombre();
        this.categoria=dto.categoria();
    }
}
