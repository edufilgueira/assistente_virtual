# Controle de Pessoas

O objetivo deste projeto é levantar vários containers para os sistemas, e como boa pratica será levantado containers para o banco postgres, servidor tomcat com java SDK e servidor ruby on rails.

Esta aplicação foi desenvolvida em java Restfull API e tem como objetivo realizar o cadastro de pessoas de acordo com as especificações do teste enviado, usando as melhores praticas de desenvolvimentos no mercado, observando a estrutura de código legível e realização testes unitários.

**Containers docker necessários**
* Servidor enginx [AngularJS]
* OpenJDK [JAVA]
* Ruby2.4 [Rails]
* Banco de Dados [Postgres9.4]

![diagrama](https://user-images.githubusercontent.com/37155369/39466190-0bc064cc-4cfe-11e8-9e9d-4301b54ed331.jpg)

# Serviços implementados
Como a funcionalidade deste teste é construir uma api que fornece serviços para um cliente, desenvolvi uma visão com angular que consome a api via ajax, tendo em vista que o rails é uma plataforma bastante robusta para ser apenas uma consumidora de serviços.

### AngularJS
Desenvolvi uma visão com angularJs para apresentar a qualidade do código e a facilidade em desenvolver interface usando esta ferramenta. Esta camada está consumindo os serviços da API.

### Rails
O projeto com rails não esta consumindo os serviços da API, mas sim com as migrações padrão.

### API Engine
A Api foi desenvolvida em java e sua função é manter o cadastro de pessoas. Sua implementação foi feita com Spring Boot dando maior produtividade aos trabalhos realizados. Escolhi as ferramentas da Spring por estar familiarizado com o seu conceito e pela facilidade no desenvolvimento.

# Instalação

Entre com o _terminal_ no diretório que achar mais coveniente para executar a aplicação pelo docker-compose. Após a finalização desse passo a passo toda a aplicação estará pronta para uso.

**Execute o comando:**

* _docker-compose up -d_

**OBS: para que a aplicação funcione é necessários**
* Criar uma database '_pessoas_'
* Rodar os migrates do rails
  - docker-compose run app bundle exec rails g scaffold User nome:string email:string
  - docker-compose run app bundle exec rails db:migrate

### Links de acesso
* [AngularJS - http://localhost](http://localhost) 
* [Rails - http://localhost:3000](http://localhost:3000) 
* [API - http://localhost:8086/metodos](http://localhost:8086/metodos) 
