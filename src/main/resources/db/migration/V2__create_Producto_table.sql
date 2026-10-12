create table producto(
    id Bigint AUTO_INCREMENT primary key,
    nombre varchar(150) not null,
    descripcion varchar(255),
    precio Decimal(10,2) not null,
    cantidad_stock int not null,
    categoria_id Bigint not null,

    CONSTRAINT fk_producto_categoria
            Foreign key(categoria_id)
            References categorias(id)
)