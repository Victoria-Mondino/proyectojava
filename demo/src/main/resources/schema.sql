CREATE TABLE IF NOT EXISTS cliente (
    clienteid BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    fecha_registro TIMESTAMP
);

CREATE TABLE IF NOT EXISTS producto (
    productoid BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    fecha_actualizacion TIMESTAMP
);

CREATE TABLE IF NOT EXISTS comprobante (
    comprobanteid BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    clienteid BIGINT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    cantidad_productos INT NOT NULL,
    FOREIGN KEY (clienteid) REFERENCES cliente(clienteid)
);

CREATE TABLE IF NOT EXISTS linea (
    lineaid BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    productoid BIGINT NOT NULL,
    comprobanteid BIGINT NOT NULL,
    FOREIGN KEY (productoid) REFERENCES producto(productoid),
    FOREIGN KEY (comprobanteid) REFERENCES comprobante(comprobanteid)
);