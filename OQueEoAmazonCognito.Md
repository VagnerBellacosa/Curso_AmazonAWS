# O que � o Amazon Cognito?

O Amazon Cognito fornece autentica��o, autoriza��o e gerenciamento de usu�rios para os seus aplicativos m�veis e web. Seus usu�rios podem fazer login diretamente com um nome de usu�rio e uma senha ou por meio de terceiros, como o Facebook, a Amazon, o Google ou a Apple.

Os dois componentes principais do Amazon Cognito s�o os grupos de usu�rios e os grupos de identidades. Os grupos de usu�rios s�o diret�rios de usu�rios que fornecem op��es de cadastro e login para os usu�rios do seu aplicativo. Os grupos de identidade permitem que voc� conceda aos usu�rios acesso a outros servi�os da AWS. Voc� pode usar grupos de identidades e grupos de usu�rios separadamente ou em conjunto.

Um grupo de usu�rios do Amazon Cognito e um grupo de identidades usados juntos

## Aqui, o objetivo � autenticar o usu�rio e, em seguida, conceder a ele acesso a outro servi�o da AWS.

1. Na primeira etapa, o usu�rio do aplicativo faz login por meio de um grupo de usu�rios e recebe tokens desse grupo ap�s uma autentica��o bem-sucedida.

2. Em seguida, o aplicativo troca os tokens do grupo de usu�rios por credenciais da AWS por meio de um grupo de identidades.

3. Por fim, o usu�rio do aplicativo pode usar essas credenciais da AWS para acessar outros servi�os da AWS, como o Amazon S3 ou o DynamoDB.


      Vis�o geral do Amazon Cognito
    
Para obter mais exemplos usando grupos de identidades e grupos de usu�rios, consulte Cen�rios comuns do Amazon Cognito.

O Amazon Cognito est� em conformidade com SOC 1 a 3, PCI DSS, ISO 27001 e � qualificado para HIPAA-BAA. Para obter mais informa��es, consulte Servi�os da AWS no escopo. Consulte tamb�m Considera��es de dados regionais.

## Recursos do Amazon Cognito

### Grupos de usu�rios

Grupo de usu�rios � um diret�rio de usu�rios no Amazon Cognito. Com um grupo de usu�rios, os usu�rios podem fazer login no aplicativo para web ou dispositivos m�veis por meio do Amazon Cognito ou de federa��o por meio de um Identity Provider (IdP � Provedor de identidade) terceiro. Quer os usu�rios fa�am login diretamente ou por meio de terceiros, todos os membros do grupo de usu�rios t�m um perfil de diret�rio que voc� pode acessar por meio de um SDK.

Os grupos de usu�rios fornecem:

- Servi�os de cadastro e login.
- Uma interface do usu�rio da web integrada e personaliz�vel para login de usu�rios.
- Login social com Facebook, Google, Login with Amazon e Iniciar sess�o com a Apple, bem como por meio de provedores de identidade SAML e OIDC a partir do seu grupo de usu�rios.
- Gerenciamento de diret�rio de usu�rios e perfis de usu�rio.
- Recursos de seguran�a como a autentica��o multifator (MFA) verifica a exist�ncia de credenciais comprometidas, prote��o de aquisi��o de conta e verifica��o de e-mail e telefone.
- Fluxos de trabalho personalizados e migra��o de usu�rios por meio do AWS Lambda.

Para obter mais informa��es sobre grupos de usu�rios, consulteConceitos b�sicos dos grupos de usu�riosO e aRefer�ncia da API dos grupos de usu�rios do Amazon Cognito.

### Grupos de identidades

Com um grupo de identidades, os usu�rios podem obter credenciais tempor�rias da AWS para acessar servi�os da AWS, como o Amazon S3 e o DynamoDB. Os grupos de identidades oferecem suporte a usu�rios convidados an�nimos, bem como os seguintes provedores de identidade que voc� pode usar para autenticar usu�rios para grupos de identidades:

- Grupos de usu�rios do Amazon Cognito
- Login social com Facebook, Google, Login with Amazon e Iniciar sess�o com a Apple
- Provedores OpenID Connect (OIDC)
- Provedores de identidade SAML
- Identidades autenticadas pelo desenvolvedor

Para salvar informa��es de perfil do usu�rio, o grupo de identidades precisa ser integrado a um grupo de usu�rios.

Para obter mais informa��es sobre grupos de identidades, consulteConceitos b�sicos dos grupos de identidades do Amazon Cognito (identidades federadas)O e aRefer�ncia da API dos grupos de identidades do Amazon Cognito.

### Conceitos b�sicos do Amazon Cognito

Para obter um guia das tarefas principais e por onde come�ar, consulte Conceitos b�sicos do Amazon Cognito.

Para v�deos, artigos, documenta��es e aplicativos de exemplo, consulteRecursos do desenvolvedor do Amazon Cognito.

Para usar o Amazon Cognito, voc� precisa de uma conta da AWS. Para obter mais informa��es, consulte Usando o console do Amazon Cognito.

## Disponibilidade regional

O Amazon Cognito est� dispon�vel em v�rias regi�es da AWS ao redor do mundo. Em cada regi�o, o Amazon Cognito � distribu�do em v�rias zonas de disponibilidade. Essas zonas de disponibilidade s�o fisicamente isoladas umas das outras, mas s�o unidas por conex�es de rede privadas, de baixa lat�ncia, de alta taxa de transfer�ncia e altamente redundantes. Essas zonas de disponibilidade permitem que a AWS forne�a servi�os, incluindo o Amazon Cognito, com n�veis muito altos de disponibilidade e redund�ncia, al�m de minimizar a lat�ncia.

Para obter uma lista de todas as regi�es onde o Amazon Cognito est� dispon�vel no momento, consulteRegi�es e endpoints da AWSnoRefer�ncia geral da Amazon Web Services. Para saber mais sobre quantas zonas de disponibilidade est�o dispon�veis em cada regi�o, consulte Infraestrutura global da AWS.


[Artigo original](https://docs.aws.amazon.com/pt_br/cognito/latest/developerguide/what-is-amazon-cognito.html)
