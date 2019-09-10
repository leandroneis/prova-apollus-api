# Prova Apollus Api
API com Java e Spring Boot.
### Detalhes da API RESTful
A API RESTful contém as seguintes características:  
* Projeto criado com Spring Boot e Java 8
* Banco de dados MySQL com JPA e Spring Data JPA
* Autenticação e autorização com Spring Security e tokens JWT (JSON Web Token)
* Migração de banco de dados com Flyway

### Como executar a aplicação
Certifique-se de ter o Maven instalado e adicionado ao PATH de seu sistema operacional, assim como o Git.
```
git clone https://github.com/leandroneis/prova-apollus-api.git
cd prova-apollus-api
mvn spring-boot:run
Acesse os endpoints através da url http://localhost:8080
```
### Importando o projeto no Eclipse ou STS
No terminal, execute a seguinte operação:
```
mvn eclipse:eclipse
```
No Eclipse/STS, importe o projeto como projeto Maven.

### Configuração do Banco de Dados
* Alterar no application.properties as configurações ou deixar seu usuário como root e a senha como root.
* Já possui um cadastro com usuário admin@admin.com.br e a senha admin.

