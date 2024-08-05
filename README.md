<h1>Aluguel de Imoveis</h1>
    <p>O projeto de aluguel de imoveis tem o proposito de fazer o cliente cadastrar um imovel para alugar e tambem poder alugar um imovel de um outro cliente, podendo avaliar e reservar eles.</p>
    <li>Projeto feito pelo Mateus</li>
     <h2>Ferramentas Utilizadas</h2>
    <ul>
        <li><strong>Java JDK 17</strong>: Linguagem de programação principal.</li>
        <li><strong>IntelliJ IDEA</strong>: IDE para desenvolvimento.</li>
        <li><strong>PostgreSQL</strong>: Banco de dados relacional.</li>
        <li><strong>Maven</strong>: Ferramenta de gerenciamento de dependências.</li>
        <li><strong>Spring Boot</strong>: Framework para criação de aplicativos Java.</li>
        <li><strong>Postman</strong>: Utilizado para teste de API.</li>
        <li><strong>Swagger</strong>: Utilizado para documentação de API.</li>
    </ul>
    <h2>Configuração do Banco de dados</h2>
    <ul>
                <li>Certifique-se de que o PostgreSQL esteja em execução.</li>
                <li>Crie um banco de dados para o projeto.</li>
                <li>Atualize as credenciais do banco de dados no arquivo <code>application.yaml</code>:
                    <pre><code>    url: jdbc:postgresql://localhost:5432/imovelalguel
    username: postgres
    password: 1505</code></pre>
                </li>
            </ul>
        </li>
  <h3><strong>Código do banco pra utilizar</strong></h3>
<li>Cliente</li>
    <pre><code> CREATE TABLE cliente (
    id INTEGER PRIMARY KEY,
    nome VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    senha VARCHAR NOT NULL
);
    </code></pre>
    <li>Imovel</li>
    <pre><code>CREATE TABLE imovel (
    id INTEGER PRIMARY KEY,
    tipoimovel VARCHAR NOT NULL,
    cepimovel VARCHAR NOT NULL,
    enderecoimovel VARCHAR NOT NULL,
    estadoimovel VARCHAR NOT NULL,
    cidadeimovel VARCHAR NOT NULL,
    bairroimovel VARCHAR NOT NULL,
    areaimovel NUMERIC NOT NULL,
    tituloimovel VARCHAR NOT NULL,
    descricaoimovel TEXT,
    preco NUMERIC NOT NULL,
    clienteid INTEGER NOT NULL
    );
    </code></pre>
    <li>Reserva</li>
    <pre><code>CREATE TABLE reserva (
    id INTEGER PRIMARY KEY,
    idcliente INTEGER NOT NULL,
    idimovel INTEGER NOT NULL,
    datainicio VARCHAR NOT NULL,
    datafim VARCHAR NOT NULL,
    status VARCHAR NOT NULL,
    FOREIGN KEY (idcliente) REFERENCES cliente(id),
    FOREIGN KEY (idimovel) REFERENCES imovel(id)
);
    </code></pre>
    <li>Avaliação</li>
    <pre><code>CREATE TABLE avaliacao (
    id SERIAL PRIMARY KEY,
    idimovel INTEGER NOT NULL,
    idcliente INTEGER NOT NULL,
    nota INTEGER NOT NULL,
    comentarios TEXT
);
    </code></pre>
    
    
<h2>Endpoints disponíveis para teste no Postman</h2>
    <h3>Cliente</h3>
    <ul>
        <li><code>POST /api/cliente</code>: Cria um novo Cliente.</li>
        <li><code>GET /api/cliente/{id}</code>: Retorna um cliente específico pelo ID.</li>
    </ul>
     <ul>
    <h3>Imovel</h3>
        <li><code>POST /imoveis</code>: Cria um novo imovel.</li>
        <li><code>GET /imoveis</code>: Retorna todos os imovel .</li>
        <li><code>GET /imoveis/{estado}</code>: Retorna um imovel específico pelo estado.</li>
        <li><code>PUT /imoveis/{id}</code>: Atualiza um imovel existente.</li>
        <li><code>DELETE/imoveis/{id}</code>: Remove um imovel.</li>
    </ul>
     <ul>
    <h3>Reserva</h3>
        <li><code>POST /reservas</code>: Cria um novo imovel.</li>
        <li><code>GET /reservas/{id}</code>: Retorna uma reserva especifica pelo ID.</li>
        <li><code>PUT /reservas{id}</code>: Atualiza uma reserva existente.</li>
        <li><code>DELETE/reservas/{id}</code>: Remove uma reserva.</li>
    </ul>
     <ul>
    <h3>Avaliação</h3>
        <li><code>POST /avaliacoes</code>: Cria uma avaliação.</li>
        <li><code>GET /avaliacoes</code>: Gera todas as avaliações .</li>
        <li><code>GET /avaliacoes/imovel/{idImovel}</code>: Retorna as avaliações de um imovel específico</li>
        <li><code>PUT /avaliacoes/{id}</code>: Atualiza uma avaliação existente.</li>
        <li><code>DELETE/avaliacoes/{id}</code>: Remove uma avaliaçcao.</li>
    </ul>
    
