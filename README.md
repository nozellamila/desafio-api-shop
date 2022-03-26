# Sobre o projeto - Desafio Automação - APIs REST
### Este é um projeto de automação de testes que visa atender aos requisitos do desafio da Base2 de automação de APIs REST. Os testes foram feitos na camada de serviço da API [shop-prototype](https://github.com/nozellamila/shop-prototype) utilizando a linguagem Java.

# Tabela de conteúdos
<!--ts-->
   * [Sobre o projeto](#sobre-o-projeto---desafio-automação---apis-rest)
   * [Tabela de conteúdos](#tabela-de-conteúdos)
   * [Objeto de teste](#objeto-de-teste)
   * [Requisitos realizados](#requisitos-realizados)
      * [Suíte de testes](#suíte-de-testes)
      * [Data-Driven testing](#data-driven-testing)
      * [Utilização de REGEX](#utilização-de-regex)
      * [Relatório de testes](#relatório-de-testes)
        * [Visualização do relatório de testes localmente](#visualização-do-relatório-de-testes-localmente)
        * [Visualização do relatório na ferramenta de CI](#visualização-do-relatório-na-ferramenta-de-ci)
      * [Gerenciamento de ambientes](#gerenciamento-de-ambientes)
      * [Gerenciamento de massa de dados](#gerenciamento-de-massa-de-dados)
      * [Execução paralela dos testes](#execução-paralela-dos-testes)
      * [Integração Contínua](#integração-contínua)
   * [Lista de tecnologias utilizadas](#lista-de-tecnologias-utilizadas)
<!--te-->

# Objeto de teste
O sistema sob teste deste projeto é um protótipo de loja, foi inspirado nos endpoints e regras da API [petstore](https://petstore.swagger.io/). Trata-se de um crud para compra, gerenciamento de produtos e usuários. Existem três endpoints cobertos pela automação aqui apresentada: Users, Carts, Products. Foi coberto pela automação fluxos principais, exceções, permissões e autenticação. Documentação disponível [aqui](https://shop-prototype-mila.herokuapp.com/swagger-ui/index.html).

# Requisitos realizados

## Suíte de testes
Foram construídos 52 testes, separados por endpoint e por verbo disponível em cada recurso. 

## Data-Driven testing
Para testes de parâmetros inválidos e busca de recursos utilizando filtros foi utilizada a abordagem de Data-Driven Testing, consumindo dados de planilhas. 

![image](https://user-images.githubusercontent.com/53572999/160219014-03edc4f5-bcae-47ec-8dfa-0835e4241df9.png)

![image](https://user-images.githubusercontent.com/53572999/160219348-2c504f22-91ba-4eac-884d-fb828710b340.png)

## Utilização de REGEX
Para validar o formato correto de data foi utilizado a asserção com regex, veja, por exemplo, o teste de [registro de compra](https://github.com/nozellamila/desafio-api-shop/blob/master/src/test/java/com/desafioapishop/tests/cart/RegisterCartTests.java).

![image](https://user-images.githubusercontent.com/53572999/160219108-de15b4f1-a645-4a71-bb26-e5088eea7001.png)

## Relatório de testes

### Visualização do relatório de testes localmente
Para rodar os testes com maven, basta clonar o projeto na máquina, acessar a raiz do projeto e executar o comando 'mvn test'. Para visualizar o relatório com Allure Reports localmente é preciso tê-lo instalado e configurado previamente.

A [documentação](https://docs.qameta.io/allure/#_installing_a_commandline) sugere utilizar o [Scoop](http://scoop.sh/) para facilitar a instalação e configuração no ambiente Windows.

Com o Allure commandline configurado, basta escrever o comando 'allure serve /home/path/to/project/target/allure-results/' no cmd, este comando irá iniciar um servidor e mostrará o resultado da execução dos testes realizados pelo comando 'mvn test'. O caminho mostrado (/home/path/to/project/target/allure-results/) é o diretório raiz do projeto + target\allure-results.

### Visualização do relatório na ferramenta de CI
Os testes são executados sempre que um novo commit é feito. A configuração da pipeline foi feita utilizando o Jenkins e com o plugin do Allure Reports é possível visualizar o relatório gerado sempre que o pipeline é rodado

![image](https://user-images.githubusercontent.com/53572999/160253332-0c424cb9-2eb7-4e73-9d0c-080283197231.png)

## Gerenciamento de ambientes
Através do arquivo "globalParameters.properties" é possível alternar o ambiente em que os testes estão sendo executados.

![image](https://user-images.githubusercontent.com/53572999/160219461-129697b5-f5d3-4f93-8f91-1bfacd5c7f88.png)

Utilizando este arquivo de configuração é possível alternar as variáveis facilmente na classe GlobalParameters.

![image](https://user-images.githubusercontent.com/53572999/160219483-53af63ac-4141-40ec-9fe4-c1affa54dd12.png)

## Gerenciamento de massa de dados
A ideia inicial para gerenciar a massa de dados dos testes era prover um ambiente limpo, com dados iniciais carregados neste ambiente. Por isso a API shop-prototype foi feita e utilizou o docker para fazer sua compilação e deploy. Como essa estratégia não foi bem sucedida, a API foi levantada localmente e seu endereço utilizado nos testes.

Sendo assim, o banco é limpo a cada execução e nova massa é adicionada:

![image](https://user-images.githubusercontent.com/53572999/160221152-23798ece-4c58-451d-9c1a-81ee2042da41.png)

## Execução paralela dos testes
Os testes são executados em paralelo utilizando a engine do Junit e sua configuração é mostrada abaixo

![image](https://user-images.githubusercontent.com/53572999/160253496-73af0e8e-29c8-4837-aabd-325a7f575eec.png)

## Integração Contínua 
A integração contínua foi feita com o Jenkins. A cada deploy da aplicação shop-prototype (objeto de teste deste projeto), o pipeline rodará automaticamente, executando os testes e disponibilizando o resultado em um relatório através do Allure Report.

![image](https://user-images.githubusercontent.com/53572999/160253385-3bccb46e-da9c-4d5a-ac9c-21af4cc0b39b.png)

# Lista de tecnologias utilizadas
- Java
- Maven
- Maven Surefire Plugin
- Junit
- Rest Assured
- Lombok
- OpenCsv
- Allure Reports
  


