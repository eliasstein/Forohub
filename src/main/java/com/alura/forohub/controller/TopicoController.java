package com.alura.forohub.controller;

import com.alura.forohub.domain.curso.Curso;
import com.alura.forohub.domain.curso.CursoRepository;
import com.alura.forohub.domain.topico.*;
import com.alura.forohub.domain.usuario.Usuario;
import com.alura.forohub.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name="bearer-key")
public class TopicoController {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    @Operation(summary = "Permite registrar un topico")
    public ResponseEntity<Object> registrarTopico(@RequestBody @Valid RegistrarTopicoDTO datos){
        Curso curso = new Curso(datos.curso());
        Usuario usuario =new Usuario(datos.autor());
        usuario.setPass(passwordEncoder.encode(datos.autor().pass()));

        Optional<Curso> checkCurso = cursoRepository.findByNombreIgnoreCase(curso.getNombre());
        Optional<Usuario> checkUsuario = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());

        if (checkCurso.isPresent()) //si el curso existe
            curso=checkCurso.get(); //Cargamos el existente
        if (checkUsuario.isPresent())
            usuario=checkUsuario.get();
        cursoRepository.save(curso);
        usuarioRepository.save(usuario);
        Topico topico=new Topico(datos,usuario,curso);
        topicoRepository.save(topico);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    @Operation(summary = "Permite obtener todos los topicos")
    public ResponseEntity<Page<ObtenerTopicosDTO>> obtenerTopicos(@PageableDefault(size = 10,sort="fechaCreacion",direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion).map(ObtenerTopicosDTO::new));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Permite obtener un topico por id con todos sus datos")
    public ResponseEntity<TopicoDTO> listadoMedicos(@PathVariable Long id) {
        Optional<Topico> topico=topicoRepository.findById(id);
        if (topico.isPresent())
            return ResponseEntity.ok(new TopicoDTO(topico.get()));
        throw new EntityNotFoundException();
    }
    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Permite actualizar un topico pasando su id por parametro")
    public ResponseEntity<TopicoDTO> actualizarTopico(@RequestBody @Valid ActualizarTopicoDTO datos, @PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        if (topico!=null){
            topico.actualizarTopico(datos);
            return ResponseEntity.ok(new TopicoDTO(topico));
        }
        throw new EntityNotFoundException();
    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Permite hacer un delete logico pasando la id como parametro")
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }

}
