# Gateway de API

## Qual � a fun��o de um gateway de API?

O gateway de API � uma ferramenta de gerenciamento de APIs que fica entre o cliente e uma cole��o de servi�os de back-end.

Ele funciona como um proxy inverso, que aceita todas as chamadas da interface de programa��o de aplica��es (API), agrega os v�rios servi�os necess�rios para realiz�-las e retorna o resultado apropriado.

A maioria das APIs corporativas � implantada por meio de gateways de API. Esses gateways normalmente gerenciam tarefas comuns que s�o usadas em um sistema de servi�os de API, como autentica��o de usu�rios, limita��o de taxa e estat�sticas.

Assista sob demanda � demonstra��o sobre seguran�a e gerenciamento de APIs

## Por que usar um gateway de API?

Em seu uso mais b�sico, um servi�o de API aceita uma solicita��o remota e retorna uma resposta. Mas na vida real nada � t�o simples. Pense nas v�rias preocupa��es que voc� tem ao hospedar APIs em grande escala.

- Voc� quer proteger suas APIs da utiliza��o excessiva e de abusos, e por isso usa um servi�o de autentica��o e limita��o de taxa. 
- Voc� quer entender como as pessoas usam suas APIs, ent�o inclui ferramentas de monitoramento e an�lise.
- Se voc� tem APIs monetizadas, ser� preciso conectar-se a um sistema de faturamento.
- Se tiver adotado uma arquitetura de microsservi�os, uma �nica solicita��o pode exigir chamadas para dezenas de aplica��es distintas.
- Com o tempo, voc� incluir� novos servi�os de API e descontinuar� outros, mas seus clientes ainda v�o querer encontrar todos os seus servi�os no mesmo lugar.

Seu desafio � oferecer aos seus clientes uma experi�ncia simples e confi�vel diante de toda essa complexidade. Um gateway de API � uma maneira de desacoplar a interface do cliente da sua implementa��o de back-end. Quando um cliente faz uma solicita��o, o gateway de API a divide em v�rias solicita��es, as direciona para os locais adequados, produz uma resposta e faz o monitoramento.

### A fun��o do gateway no gerenciamento da API

Um gateway de API faz parte do sistema de gerenciamento da API. Ele intercepta todas as solicita��es de entrada e as envia por meio desse sistema, que processa diversas fun��es necess�rias.

A fun��o exata do gateway varia de uma implementa��o para outra. Algumas fun��es comuns incluem autentica��o, roteamento, limita��o de taxa, faturamento, monitoramento, an�lise, pol�ticas, alertas e seguran�a.

### Como um gateway de API d� suporte ao DevOps e a ambientes serverless

Em organiza��es que adotam uma abordagem de DevOps, os desenvolvedores usam microsservi�os para criar e implantar aplica��es de maneira r�pida e iterativa. As APIs s�o um dos meios mais usados na comunica��o entre microsservi�os.

Al�m disso, o desenvolvimento moderno de nuvem, incluindo o modelo serverless, depende das APIs para provisionamento de infraestrutura. Com um gateway de API, � poss�vel implantar fun��es serverless e gerenci�-las.

Em geral, � medida que integra��o e interconectividade ganham mais import�ncia, o mesmo acontece com as APIs. Da forma semelhante, � medida que a complexidade das APIs e o uso delas aumenta, o mesmo acontece com o gateway de API.

[Artigo original](https://www.redhat.com/pt-br/topics/api/what-does-an-api-gateway-do)