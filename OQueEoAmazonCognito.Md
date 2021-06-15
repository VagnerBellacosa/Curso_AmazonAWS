# O que é o Amazon Cognito?

O Amazon Cognito fornece autenticação, autorização e gerenciamento de usuários para os seus aplicativos móveis e web. Seus usuários podem fazer login diretamente com um nome de usuário e uma senha ou por meio de terceiros, como o Facebook, a Amazon, o Google ou a Apple.

Os dois componentes principais do Amazon Cognito são os grupos de usuários e os grupos de identidades. Os grupos de usuários são diretórios de usuários que fornecem opções de cadastro e login para os usuários do seu aplicativo. Os grupos de identidade permitem que você conceda aos usuários acesso a outros serviços da AWS. Você pode usar grupos de identidades e grupos de usuários separadamente ou em conjunto.

Um grupo de usuários do Amazon Cognito e um grupo de identidades usados juntos

## Aqui, o objetivo é autenticar o usuário e, em seguida, conceder a ele acesso a outro serviço da AWS.

1. Na primeira etapa, o usuário do aplicativo faz login por meio de um grupo de usuários e recebe tokens desse grupo após uma autenticação bem-sucedida.

2. Em seguida, o aplicativo troca os tokens do grupo de usuários por credenciais da AWS por meio de um grupo de identidades.

3. Por fim, o usuário do aplicativo pode usar essas credenciais da AWS para acessar outros serviços da AWS, como o Amazon S3 ou o DynamoDB.


      Visão geral do Amazon Cognito
    
Para obter mais exemplos usando grupos de identidades e grupos de usuários, consulte Cenários comuns do Amazon Cognito.

O Amazon Cognito está em conformidade com SOC 1 a 3, PCI DSS, ISO 27001 e é qualificado para HIPAA-BAA. Para obter mais informações, consulte Serviços da AWS no escopo. Consulte também Considerações de dados regionais.

## Recursos do Amazon Cognito

### Grupos de usuários

Grupo de usuários é um diretório de usuários no Amazon Cognito. Com um grupo de usuários, os usuários podem fazer login no aplicativo para web ou dispositivos móveis por meio do Amazon Cognito ou de federação por meio de um Identity Provider (IdP — Provedor de identidade) terceiro. Quer os usuários façam login diretamente ou por meio de terceiros, todos os membros do grupo de usuários têm um perfil de diretório que você pode acessar por meio de um SDK.

Os grupos de usuários fornecem:

- Serviços de cadastro e login.
- Uma interface do usuário da web integrada e personalizável para login de usuários.
- Login social com Facebook, Google, Login with Amazon e Iniciar sessão com a Apple, bem como por meio de provedores de identidade SAML e OIDC a partir do seu grupo de usuários.
- Gerenciamento de diretório de usuários e perfis de usuário.
- Recursos de segurança como a autenticação multifator (MFA) verifica a existência de credenciais comprometidas, proteção de aquisição de conta e verificação de e-mail e telefone.
- Fluxos de trabalho personalizados e migração de usuários por meio do AWS Lambda.

Para obter mais informações sobre grupos de usuários, consulteConceitos básicos dos grupos de usuáriosO e aReferência da API dos grupos de usuários do Amazon Cognito.

### Grupos de identidades

Com um grupo de identidades, os usuários podem obter credenciais temporárias da AWS para acessar serviços da AWS, como o Amazon S3 e o DynamoDB. Os grupos de identidades oferecem suporte a usuários convidados anônimos, bem como os seguintes provedores de identidade que você pode usar para autenticar usuários para grupos de identidades:

- Grupos de usuários do Amazon Cognito
- Login social com Facebook, Google, Login with Amazon e Iniciar sessão com a Apple
- Provedores OpenID Connect (OIDC)
- Provedores de identidade SAML
- Identidades autenticadas pelo desenvolvedor

Para salvar informações de perfil do usuário, o grupo de identidades precisa ser integrado a um grupo de usuários.

Para obter mais informações sobre grupos de identidades, consulteConceitos básicos dos grupos de identidades do Amazon Cognito (identidades federadas)O e aReferência da API dos grupos de identidades do Amazon Cognito.

### Conceitos básicos do Amazon Cognito

Para obter um guia das tarefas principais e por onde começar, consulte Conceitos básicos do Amazon Cognito.

Para vídeos, artigos, documentações e aplicativos de exemplo, consulteRecursos do desenvolvedor do Amazon Cognito.

Para usar o Amazon Cognito, você precisa de uma conta da AWS. Para obter mais informações, consulte Usando o console do Amazon Cognito.

## Disponibilidade regional

O Amazon Cognito está disponível em várias regiões da AWS ao redor do mundo. Em cada região, o Amazon Cognito é distribuído em várias zonas de disponibilidade. Essas zonas de disponibilidade são fisicamente isoladas umas das outras, mas são unidas por conexões de rede privadas, de baixa latência, de alta taxa de transferência e altamente redundantes. Essas zonas de disponibilidade permitem que a AWS forneça serviços, incluindo o Amazon Cognito, com níveis muito altos de disponibilidade e redundância, além de minimizar a latência.

Para obter uma lista de todas as regiões onde o Amazon Cognito está disponível no momento, consulteRegiões e endpoints da AWSnoReferência geral da Amazon Web Services. Para saber mais sobre quantas zonas de disponibilidade estão disponíveis em cada região, consulte Infraestrutura global da AWS.


[Artigo original](https://docs.aws.amazon.com/pt_br/cognito/latest/developerguide/what-is-amazon-cognito.html)
