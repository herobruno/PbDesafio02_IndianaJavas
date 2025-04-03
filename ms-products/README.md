# ms-products

## Descrição
Este projeto é um microsserviço desenvolvido em **Spring Boot** para gerenciamento de produtos e categorias. Ele inclui suporte para banco de dados PostgreSQL, validações, mapeamento de entidades e um ambiente de desenvolvimento com H2 para testes.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.4.1**
- **Maven**
- **PostgreSQL**
- **H2 Database** (ambiente de teste)
- **Lombok**
- **ModelMapper**

## Requisitos
- Java 17 recomendado
- Maven 3.6.3 recomendado
- PostgreSQL instalado e configurado

## Dependências
### Principais Dependências
- `spring-boot-starter-data-jpa`: Suporte para JPA.
- `spring-boot-starter-web`: Desenvolvimento de APIs REST.
- `spring-boot-starter-validation`: Validações de campos.
- `spring-boot-starter-webflux`: Suporte para programação reativa.
- `postgresql`: Conexão com banco de dados PostgreSQL.
- `h2`: Banco de dados em memória para testes.
- `lombok`: Redução de código repetitivo com anotações automáticas.
- `modelmapper`: Conversão de objetos DTO.

### Dependências de Teste
- `spring-boot-starter-test`: Testes unitários e de integração.



## Configuração do Banco de Dados
### PostgreSQL (Produção)
```
spring.datasource.url=jdbc:postgresql://localhost:5432/produtos
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### H2 (Teste)
```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

## Executando o Projeto
1. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```
2. Acesse o diretório do projeto:
   ```bash
   cd ms-products
   ```
3. Execute o comando para compilar e rodar:
   ```bash
   mvn spring-boot:run
   ```
4. O projeto estará disponível em:
   ```
   http://localhost:8080
   ```

## Testes
Para rodar os testes, utilize o comando:
```bash
mvn test
```


# API Endpoints - Products and Categories

Este documento descreve as rotas disponíveis nos controladores de produtos e categorias do serviço `msproducts`. Ele inclui exemplos de uso para cada endpoint.

---

## Produtos (Products)

### 1. Criar Produto
**POST /api/v1/products**

**Requisição:**
```json
{
    "name": "Produto1",
    "description": "Descrição",
    "price": 9.99,
    "imgUrl": "http://exemplo.com/imagem.jpg",
    "date": "2023-12-27T10:30:00",
    "categories": [
        {
            "id": 1,
            "name": "Categoria A"
        }
    ]
}
```

**Resposta:**
```json
{
    "name": "Produto1",
    "description": "Descrição",
    "price": 9.99,
    "imgUrl": "http://exemplo.com/imagem.jpg",
    "date": "2023-12-27T10:30:00",
    "categories": [
        {
            "id": 1,
        }
    ]
}
```

---

### 2. Buscar Produto por ID
**GET /api/v1/products/{id}**

**Resposta:**
```json
{
   "id": 1,
    "name": "Produto1",
    "description": "Descrição",
    "price": 9.99,
    "imgUrl": "http://exemplo.com/imagem.jpg",
    "date": "2023-12-27T10:30:00",
    "categories": [
        {
            "id": 1,
        }
    ]
}
```

---

### 3. Deletar Produto
**DELETE /api/v1/products/{id}**

**Resposta:**
- 204 No Content (se removido com sucesso)
- 404 Not Found (se o produto não for encontrado)

---

### 4. Listar Produtos com Paginação
**GET /api/v1/products**

**Parâmetros:**
- `page` (opcional): Página atual (padrão: 0)
- `linesPerPage` (opcional): Linhas por página (padrão: 5)
- `direction` (opcional): Ordenação ASC/DESC (padrão: ASC)
- `orderBy` (opcional): Campo de ordenação (padrão: name)

**Resposta:**
```json
{
  "content": [
{
    "name": "Produto1",
    "description": "Descrição",
    "price": 9.99,
    "imgUrl": "http://exemplo.com/imagem.jpg",
    "date": "2023-12-27T10:30:00",
    "categories": [
        {
            "id": 1,
        }
    ]
}
{
    "name": "Produto2",
    "description": "Descrição",
    "price": 1.99,
    "imgUrl": "http://exemplo.com/imagem.jpg",
    "date": "2023-12-27T10:30:00",
    "categories": [
        {
            "id": 1,
        }
    ]
}
  ],
  "totalPages": 1,
  "totalElements": 2,
  "size": 5,
  "number": 0
}
```

---

## Categorias (Categories)

### 1. Criar Categoria
**POST /api/v1/categories**

**Requisição:**
```json
{
  "name": "Categoria A"
}
```

**Resposta:**
```json
   "categories": [
        {
            "id": 1,
        }
    ]
```

---

### 2. Atualizar Categoria
**PUT /api/v1/categories/{id}**

**Requisição:**
```json
{
  "name": "Nova Categoria"
}
```

**Resposta:**
- 204 No Content (se atualizado com sucesso)

---

### 3. Buscar Categoria por ID
**GET /api/v1/categories/{id}**

**Resposta:**
```json
{
  "id": 1,
}
```

---

### 4. Listar Todas as Categorias
**GET /api/v1/categories**

**Resposta:**
```json
[
  {
    "id": 1,
  },
  {
    "id": 2,
  }
]
```

---

### 5. Deletar Categoria
**DELETE /api/v1/categories/{id}**

**Resposta:**
- 204 No Content (se removido com sucesso)


## Observações
- Todas as requisições que aceitam JSON devem incluir o cabeçalho `Content-Type: application/json`.
- Os endpoints exigem validações nos campos, retornando erros detalhados caso os dados sejam inválidos.
- É recomendado o uso de ferramentas como Postman ou cURL para testar as requisições.

## Relacionamento entre Produto e Categoria
A API possui um relacionamento do tipo "many-to-many" (muitos-para-muitos) entre as tabelas `tb_product` e `tb_category`. Esse relacionamento é estabelecido através da tabela intermediária `tb_product_category`. 

## Criar Produto com Categoria
Para criar um produto com uma categoria associada, é necessário primeiro criar a categoria no banco de dados. Após a criação da categoria, o ID gerado pode ser utilizado ao criar o produto. 


## Relatório de Cobertura de Testes JaCoCo
![image](https://github.com/user-attachments/assets/b4483abb-2a1a-4294-98ca-03b7aa3510e1)


