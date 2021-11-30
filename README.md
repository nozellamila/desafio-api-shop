# Sobre o projeto - Desafio Automação - APIs REST
### Este é um projeto de automação de testes que visa atender aos requisitos do desafio da Base2 de automação de APIs REST. Os testes foram feitos na camada de serviço da API [shop-prototype](https://github.com/nozellamila/shop-prototype) utilizando a linguagem Java.

# Tabela de conteúdos
<!--ts-->
   * [Sobre o projeto](#sobre-o-projeto---desafio-automação---apis-rest)
   * [Tabela de conteúdos](#tabela-de-conteúdos)
   * [Objeto de teste](#objeto-de-teste)
   * [Suíte de testes](#suíte-de-testes)
   * [Execução dos testes](#execução-dos-testes)
      * [Execução local](#execução-local)
        * [Pré-requisitos](#pré-requisitos)
        * [Execução e visualização do relatório de testes](#execução-e-visualização-do-relatório-de-testes)
      * [Execução na ferramenta de integração contínua](#execução-na-ferramenta-de-integração-contínua)
   * [Gerenciamento de massa de dados](#gerenciamento-de-massa-de-dados)
   * [Lista de tecnologias utilizadas](#lista-de-tecnologias-utilizadas)
<!--te-->

# Objeto de teste
O sistema sob teste deste projeto é um protótipo de loja, foi inspirado nos endpoints e regras da API [petstore](https://petstore.swagger.io/). Trata-se de um crud para compra, gerenciamento de produtos e usuários. Existem três endpoints cobertos pela automação aqui apresentada: Users, Carts, Products. Foi coberto pela automação fluxos principais, exceções, permissões e autenticação. Documentação disponível [aqui](https://shop-prototype-mila.herokuapp.com/swagger-ui/index.html).
# Suíte de testes
Foram construídos 52 testes, separados por endpoint e por verbo disponível em cada recurso. Para testes de parâmetros inválidos e busca de recursos utilizando filtros foi utilizada a abordagem de Data-Driven Testing, consumindo dados de planilhas. Para validar o formato correto de data foi utilizado a asserção com regex, veja, por exemplo, o teste de [registro de compra](https://github.com/nozellamila/desafio-api-shop/blob/master/src/test/java/com/desafioapishop/tests/cart/RegisterCartTests.java).
# Execução dos testes
Os testes podem ser executados localmente ou através de um pipeline de testes. A suíte pode ainda ser executada em diferentes ambientes, utilizando diferentes parâmetros contidos em um [arquivo de configuração](https://github.com/nozellamila/desafio-api-shop/blob/master/src/test/globalParameters.properties). O projeto contém também o recurso de paralelismo e possibilidade de geração de um relatório com os resultados dos testes.
## Execução local
### Pré-requisitos
Para realizar a execução local é preciso ter o [Java 15](https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html) e o [Maven](https://maven.apache.org/download.cgi) instalados e configurados na máquina.

### Execução e visualização do relatório de testes
Para rodar os testes com maven, basta clonar o projeto na máquina, acessar a raiz do projeto e executar o comando 'mvn test'. Para visualizar o relatório com Allure Reports localmente é preciso tê-lo instalado e configurado previamente.

A [documentação](https://docs.qameta.io/allure/#_installing_a_commandline) sugere utilizar o [Scoop](http://scoop.sh/) para facilitar a instalação e configuração no ambiente Windows.

Com o Allure commandline configurado, basta escrever o comando 'allure serve <path-to-allure-results>' no cmd. Este caminho é o diretório raiz do projeto + target\allure-results.

## Execução na ferramenta de integração contínua
Os testes são executados sempre que um novo commit é feito. A configuração da pipeline foi feita utilizando o github actions, seu arquivo .yml pode ser visto [aqui](https://github.com/nozellamila/desafio-api-shop/blob/master/.github/workflows/maven.yml)
  
# Gerenciamento de massa de dados
A ideia inicial para gerenciar a massa de dados dos testes era prover um ambiente limpo, com dados iniciais carregados neste ambiente. Por isso a API shop-prototype foi feita e utilizou o docker para fazer sua compilação e deploy. Como essa estratégia não foi bem sucedida, a API foi deployada no Heroku e seu endereço foi utilizado na automação.
  

# Lista de tecnologias utilizadas
- Java
- Maven
- Maven Surefire Plugin
- Junit
- Rest Assured
- Lombok
- OpenCsv
- Allure Reports
  


