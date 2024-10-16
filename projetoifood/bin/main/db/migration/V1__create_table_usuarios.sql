CREATE TABLE usuarios(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    logradouro VARCHAR (255) NOT NULL,
    numero VARCHAR (255) NOT NULL,
    bairro VARCHAR (255) NOT NULL,
    cidade VARCHAR (255) NOT NULL,
    cep VARCHAR (10) NOT NULL,
    complemento VARCHAR (255) NOT NULL
);


