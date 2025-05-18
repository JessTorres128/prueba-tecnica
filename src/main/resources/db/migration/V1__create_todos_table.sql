create table todos (
        id bigint not null auto_increment,
        descripcion varchar(255) not null,
        estado varchar(255) not null,
        fecha_limite date not null,
        primary key (id)
    ) engine=InnoDB