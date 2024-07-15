create table topicos(

    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(100) not null,
    fecha_creacion datetime not null,
    status tinyint not null,
    autor_id bigint not null,
    curso_id bigint not null,

    primary key(id),
    constraint fk_curso_id_cursos_id foreign key(curso_id) references cursos(id),
    constraint fk_topicos_autor_id_usuarios_id foreign key(autor_id) references usuarios(id)

);
