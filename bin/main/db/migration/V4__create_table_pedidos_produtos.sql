CREATE TABLE pedidos_produtos
(
    pedido_id   BIGINT NOT NULL,
    produtos_id BIGINT NOT NULL,
    quantidade  INT DEFAULT 1,
    PRIMARY KEY (pedido_id, produtos_id),
    FOREIGN KEY (pedido_id) REFERENCES pedidos (id),
    FOREIGN KEY (produtos_id) REFERENCES produtos (id)
);
