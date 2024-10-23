CREATE TABLE produtos(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    imagem_url VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    sabor VARCHAR(100) NOT NULL
);
