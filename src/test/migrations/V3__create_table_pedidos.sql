CREATE TABLE pedidos
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id  BIGINT NOT NULL,
    status      VARCHAR(255)   DEFAULT NULL,
    data_pedido TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    total       DECIMAL(10, 2) DEFAULT NULL,
    FOREIGN KEY (cliente_id) REFERENCES usuarios (id)
);