# Gateway de API

## Qual é a função de um gateway de API?

O gateway de API é uma ferramenta de gerenciamento de APIs que fica entre o cliente e uma coleção de serviços de back-end.

Ele funciona como um proxy inverso, que aceita todas as chamadas da interface de programação de aplicações (API), agrega os vários serviços necessários para realizá-las e retorna o resultado apropriado.

A maioria das APIs corporativas é implantada por meio de gateways de API. Esses gateways normalmente gerenciam tarefas comuns que são usadas em um sistema de serviços de API, como autenticação de usuários, limitação de taxa e estatísticas.

Assista sob demanda à demonstração sobre segurança e gerenciamento de APIs

## Por que usar um gateway de API?

Em seu uso mais básico, um serviço de API aceita uma solicitação remota e retorna uma resposta. Mas na vida real nada é tão simples. Pense nas várias preocupações que você tem ao hospedar APIs em grande escala.

- Você quer proteger suas APIs da utilização excessiva e de abusos, e por isso usa um serviço de autenticação e limitação de taxa. 
- Você quer entender como as pessoas usam suas APIs, então inclui ferramentas de monitoramento e análise.
- Se você tem APIs monetizadas, será preciso conectar-se a um sistema de faturamento.
- Se tiver adotado uma arquitetura de microsserviços, uma única solicitação pode exigir chamadas para dezenas de aplicações distintas.
- Com o tempo, você incluirá novos serviços de API e descontinuará outros, mas seus clientes ainda vão querer encontrar todos os seus serviços no mesmo lugar.

Seu desafio é oferecer aos seus clientes uma experiência simples e confiável diante de toda essa complexidade. Um gateway de API é uma maneira de desacoplar a interface do cliente da sua implementação de back-end. Quando um cliente faz uma solicitação, o gateway de API a divide em várias solicitações, as direciona para os locais adequados, produz uma resposta e faz o monitoramento.

### A função do gateway no gerenciamento da API

Um gateway de API faz parte do sistema de gerenciamento da API. Ele intercepta todas as solicitações de entrada e as envia por meio desse sistema, que processa diversas funções necessárias.

A função exata do gateway varia de uma implementação para outra. Algumas funções comuns incluem autenticação, roteamento, limitação de taxa, faturamento, monitoramento, análise, políticas, alertas e segurança.

### Como um gateway de API dá suporte ao DevOps e a ambientes serverless

Em organizações que adotam uma abordagem de DevOps, os desenvolvedores usam microsserviços para criar e implantar aplicações de maneira rápida e iterativa. As APIs são um dos meios mais usados na comunicação entre microsserviços.

Além disso, o desenvolvimento moderno de nuvem, incluindo o modelo serverless, depende das APIs para provisionamento de infraestrutura. Com um gateway de API, é possível implantar funções serverless e gerenciá-las.

Em geral, à medida que integração e interconectividade ganham mais importância, o mesmo acontece com as APIs. Da forma semelhante, à medida que a complexidade das APIs e o uso delas aumenta, o mesmo acontece com o gateway de API.

[Artigo original](https://www.redhat.com/pt-br/topics/api/what-does-an-api-gateway-do)