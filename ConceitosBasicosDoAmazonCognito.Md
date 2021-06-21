# Conceitos básicos do Amazon Cognito

Esta seção descreve as principais tarefas do Amazon Cognito e por onde começar. Para uma visão geral do Amazon Cognito, consulte: O que é o Amazon Cognito?.

Os dois componentes principais do Amazon Cognito são os grupos de usuários e os grupos de identidades. Os grupos de usuários são diretórios de usuários que fornecem opçães de cadastro e login para os usuários do seu aplicativo Web e móvel. Os grupos de identidade fornecem credenciais da AWS para conceder aos usuários acesso a outros serviços da AWS. Voce pode usar grupos de usuários e grupos de identidades separadamente ou em conjunto.

## Tarefas principais e por onde começar

### Adição de cadastro e login com um grupo de usuários

1. Criar um diretório de usuário com um grupo de usuários.
2. Adicionar um aplicativo para habilitar a interface do usuário hospedada.
3. Adicionar login social a um grupo de usuários.
4. Adicionar login por meio de provedores de identidade com base em SAML (IdPs) a um grupo de usuários.
5. Adicionar login por meio de IdPs OpenID Connect (OIDC) a um grupo de usuários.
6. Instalar um SDK de grupo de usuários.
7. Personalizar as páginas de login e cadastro da interface do usuário da web hospedada integrada.
8. Configurar recursos de segurança do grupo de usuários.
9. Personalizar fluxos de trabalho do grupo de usuários com triggers do Lambda.
10. Coletar dados e segmentar campanhas com a análise do Amazon Pinpoint.

### Gerenciamento de usuários em um grupo de usuários

- Cadastrar e confirmar contas de usuários.
- Criar contas de usuário como administrador.
- Gerenciar e pesquisar contas de usuário.
- Adicionar grupos a um grupo de usuários.
- Importar usuários para um grupo de usuários.

### Acesso a recursos

### Cenários comuns do Amazon Cognito:

- Autenticar com um grupo de usuários.
- Acessar recursos de back-end por meio de um grupo de usuários.
- Acessar API Gateway e Lambda por meio de um grupo de usuários.
- Acessar os serviços da AWS com um grupo de usuários e um grupo de identidades.
- Acessar os serviços da AWS por meio de terceiros e de um grupo de identidades.
- Acessar recursos do AWS AppSync por meio de um grupo de usuários ou de um grupo de identidades.

[Artigo original](https://docs.aws.amazon.com/pt_br/cognito/latest/developerguide/cognito-getting-started.html)