# Políticas gerenciadas pela AWS para funções de trabalho

Para começar a adicionar permissões às suas identidades do IAM (usuários, grupos de usuários e funções), você pode usar políticas gerenciadas pela AWS. É necessário tempo e experiência para criar políticas gerenciadas pelo cliente do IAM que forneçam à sua equipe apenas as permissões necessárias. As políticas gerenciadas pela AWS cobrem casos de uso comuns e estão disponíveis em sua conta da AWS. Para obter mais informações sobre políticas gerenciadas da AWS, consulte Políticas gerenciadas pela AWS.

Políticas gerenciadas pela AWS para funções de trabalho são projetadas para se alinhar de perto com funções de trabalho comuns no setor de TI. Você pode usar essas políticas para facilmente conceder as permissões necessárias para executar as tarefas esperadas de alguém com determinada função de trabalho. Essas políticas consolidam permissões para vários serviços em uma única política com a qual seja mais fácil trabalhar do que ter permissões espalhadas através de muitas políticas.

Você pode anexar políticas gerenciadas pela AWS, incluindo funções de trabalho, a qualquer identidade do IAM (usuários, grupo de usuários ou funções).

## Use funções para combinar serviços

Algumas das políticas usam funções de serviço do IAM para ajudá-lo a aproveitar os recursos encontrados em outros serviços da AWS. Essas políticas concedem acesso a iam:passrole, o que permite que um usuário com a política passe uma função para um serviço da AWS. Essa função delega permissões do IAM para o serviço da AWS executar ações em seu nome.

Você deve criar as funções de acordo com as suas necessidades. Por exemplo, a política de administrador de rede permite que um usuário com a política passe uma função chamada "flow-logs-vpc" para o serviço Amazon CloudWatch. O CloudWatch usa essa função para registrar e capturar o tráfego de IP para VPCs criadas pelo usuário.

Para seguir as melhores práticas de segurança, as políticas para funções de trabalho incluem filtros que limitam os nomes de funções válidas que podem ser transmitidas. Isso ajuda a evitar a concessão de permissões desnecessárias. Se seus usuários precisam das funções de serviço opcionais, você deve criar uma função que siga a convenção de nomenclatura especificada na política. Então, conceda permissões para a função. Ao concluir, o usuário pode configurar o serviço para usar a função, concedendo-lhe todas as permissões que a função oferece.

Nas seguintes seções, cada nome de política é um link para a página de detalhes da política no Console de gerenciamento da AWS. Lá, é possível consultar o documento de política e revisar as permissões que ela concede.

## Função de trabalho do administrador

### Nome da política gerenciada pela AWS: *AdministratorAccess*

**Caso de uso:** Este usuário tem acesso total e pode delegar permissões para todos os serviços e recursos na AWS.

**Atualizações da política:** AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

**Descrição da política:** Esta política concede todas as ações para todos os serviços da AWS e para todos os recursos na conta.

...
nota
Para um usuário ou uma função do IAM acessar o console do AWS Billing and Cost Management com as permissões nessa política, você deve primeiro ativar o usuário do IAM e o acesso da função. Para fazer isso, siga as instruções na Etapa 1 do tutorial sobre como delegar acesso ao console de faturamento.
...

## Função de trabalho de faturamento

### Nome da política gerenciada pela AWS: *Billing*

**Caso de uso:** Este usuário precisa visualizar informações de faturamento, configurar e autorizar pagamentos. O usuário pode monitorar os custos acumulados para cada serviço da AWS.

**Atualizações da política:** AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

**Descrição da política:** Esta política concede permissões completas para gerenciar faturamento, custos, meios de pagamento, orçamentos e relatórios.

...
nota
Para um usuário ou uma função do IAM acessar o console do AWS Billing and Cost Management com as permissões nessa política, você deve primeiro ativar o usuário do IAM e o acesso da função. Para fazer isso, siga as instruções na Etapa 1 do tutorial sobre como delegar acesso ao console de faturamento.
...

## Função de trabalho de administrador de banco de dados

### Nome da política gerenciada pela AWS: *DatabaseAdministrator*

**Caso de uso:** Este usuário define, configura e mantém bancos de dados na Nuvem AWS.

**Atualizações da política:** AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

**Descrição da política:** Esta política concede permissões para criar, configurar e manter bancos de dados. Isso inclui o acesso a serviços de banco de dados da AWS, como o Amazon DynamoDB, o Amazon Relational Database Service (RDS) e o Amazon Redshift. Visualize a política para obter a lista completa de serviços de banco de dados aos quais essa política oferece suporte.

Essa política de função de trabalho oferece suporte à capacidade de passar funções para serviços da AWS. A política permite a ação iam:PassRole somente para as funções listadas na tabela a seguir. Para obter mais informações, consulte Criar funções e anexar políticas (console) adiante neste tópico.

### Funções de serviço opcionais do IAM para a função de trabalho do administrador de banco de dados

 | Caso de uso | Nome da função (* é um curinga) | Tipo de função de serviço a selecionar | Selecione esta política gerenciada pela AWS | 
 | Permitir que o usuário monitore bancos de dados do RDS | rds-monitoring-role | Função do Amazon RDS para monitoramento avançado | AmazonRDSEnhancedMonitoringRole | 
 | Permitir que AWS Lambda monitore seu banco de dados e acesse bancos de dados externos | rdbms-lambda-access | Amazon EC2 | AWSLambda_FullAccess | 
 | Permitir que o Lambda faça upload de arquivos para clusters do Amazon S3 e do Amazon Redshift com o DynamoDB | lambda_exec_role | AWS Lambda | Criar uma nova política gerenciada conforme definido no Blog de Big Data da AWS | 
 | Permitir que funções do Lambda atuem como triggers para suas tabelas do DynamoDB | lambda-dynamodb-* | AWS Lambda | AWSLambdaDynamoDBExecutionRole | 
 | Permitir que funções do Lambda acessem o Amazon RDS em uma VPC | lambda-vpc-execution-role | Criar uma função com uma política de confiança conforme definido no AWS Lambda Developer Guide | AWSLambdaVPCAccessExecutionRole | 
 | Permitir que o AWS Data Pipeline acesse seus recursos da AWS | DataPipelineDefaultRole | Criar uma função com uma política de confiança conforme definido no Guia do desenvolvedor do AWS Data Pipeline | A documentação do AWS Data Pipeline lista as permissões necessárias para este caso de uso. Consulte Funções do IAM para AWS Data Pipeline | 
 | Permitir que seus aplicativos em execução em instâncias do Amazon EC2 acesses seus recursos da AWS | DataPipelineDefaultResourceRole | Criar uma função com uma política de confiança conforme definido no Guia do desenvolvedor do AWS Data Pipeline | AmazonEC2RoleforDataPipelineRole | 

# Função de trabalho de cientista de dados

## Nome da política gerenciada pela AWS: *DataScientist*

**Caso de uso:** Este usuário executa trabalhos e consultas do Hadoop. O usuário também acessa e analisa informações para análise de dados e inteligência de negócios.

**Atualizações da política:** AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

**Descrição da política:** Esta política concede permissões para criar, gerenciar e executar consultas em um cluster do Amazon EMR e executar análises de dados com ferramentas como o Amazon QuickSight. A política inclui o acesso a serviços adicionais de cientistas de dados, como o AWS Data Pipeline, o Amazon EC2, o Amazon Kinesis, o Amazon Machine Learning, e o SageMaker. Visualize a política para obter a lista completa de serviços de cientistas de dados aos quais essa política oferece suporte.

Essa política de função de trabalho oferece suporte à capacidade de passar funções para serviços da AWS. Uma declaração permite passar qualquer função para o SageMaker. Outra declaração permite a ação iam:PassRole somente para as funções listadas na tabela a seguir. Para obter mais informações, consulte Criar funções e anexar políticas (console) adiante neste tópico.

### Funções de serviço opcionais do IAM para a função de trabalho de cientista de dados

 | Caso de uso | Nome da função (* é um curinga) | Tipo de função de serviço a selecionar | Política gerenciada pela AWS a selecionar | 
 | Permitir que instâncias do Amazon EC2 acessem serviços e recursos adequados para clusters | EMR-EC2_DefaultRole | Amazon EMR para EC2 | AmazonElasticMapReduceforEC2Role | 
 | Permitir acesso ao Amazon EMR para acessar o serviço e recursos do Amazon EC2 para clusters | EMR_DefaultRole | Amazon EMR | AmazonEMRServicePolicy_v2 | 
 | Permitir que Kinesis Kinesis Data Analytics acesse fontes de dados de streaming | kinesis-* | Criar uma função com uma política de confiança conforme definido no Blog de Big Data da AWS. | Consulte o Blog de Big Data da AWS, que descreve quatro opções possíveis, dependendo do caso de uso | 
 | Permitir que o AWS Data Pipeline acesse seus recursos da AWS | DataPipelineDefaultRole | Criar uma função com uma política de confiança conforme definido no Guia do desenvolvedor do AWS Data Pipeline | A documentação do AWS Data Pipeline lista as permissões necessárias para este caso de uso. Consulte Funções do IAM para AWS Data Pipeline | 
 | Permitir que seus aplicativos em execução em instâncias do Amazon EC2 acesses seus recursos da AWS | DataPipelineDefaultResourceRole | Criar uma função com uma política de confiança conforme definido no Guia do desenvolvedor do AWS Data Pipeline | AmazonEC2RoleforDataPipelineRole | 

## Função de trabalho do usuário avançado do desenvolvedor

### Nome da política gerenciada pela AWS: *PowerUserAccess*

**Caso de uso:** Este usuário executa tarefas de desenvolvimento de aplicativos e pode criar e configurar recursos e serviços compatíveis com o desenvolvimento consciente de aplicativos da AWS.

**Atualizações da política:** AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

**Descrição da política:** A primeira instrução dessa política usa o elemento NotAction para permitir todas as ações para todos os serviços do AWS e para todos os recursos, exceto AWS Identity and Access Management e AWS Organizations. A segunda instrução concede permissões do IAM para criar uma função vinculada ao serviço. Isso é necessário para alguns serviços que devem acessar recursos em outro serviço, como um bucket do Amazon S3. Ela também concede permissões do Organizações para visualizar informações sobre a organização do usuário, incluindo o e-mail da conta de gerenciamento e as limitações da organização. Embora essa política limite IAM e Organizações conceda acesso, ela permite que o usuário execute todas as AWS SSO ações se AWS SSO estiver habilitado.

## Função de trabalho de administrador de rede

### Nome da política gerenciada pela AWS: *NetworkAdministrator*

**Caso de uso:** Este usuário é responsável pela configuração e manutenção dos recursos de rede da AWS.

**Atualizações da política:** AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

**Descrição da política:** Esta política concede permissões para criar e manter recursos de rede no Auto Scaling, Amazon EC2, AWS Direct Connect, Route 53, Amazon CloudFront, Elastic Load Balancing, AWS Elastic Beanstalk, Amazon SNS, CloudWatch, CloudWatch Logs, Amazon S3, IAM e Amazon Virtual Private Cloud.

Esta função de trabalho requer a capacidade de passar funções para serviços da AWS. A política concede iam:GetRole e iam:PassRole apenas para as funções listadas na seguinte tabela. Para obter mais informações, consulte Criar funções e anexar políticas (console) adiante neste tópico.

Funções de serviço opcionais do IAM para a função de trabalho do administrador de rede

 | Caso de uso | Nome da função (* é um curinga) | Tipo de função de serviço a selecionar | Política gerenciada pela AWS a selecionar  | 

 |  Permitir que a Amazon VPC crie e gerencie registros no CloudWatch Logs em nome do usuário para monitorar o tráfego de IP de entrada e saída de sua VPC | flow-logs-* | Criar uma função com uma política de confiança conforme definido no Guia do usuário da Amazon VPC | Este caso de uso não tem uma política gerenciada pela AWS existente, mas a documentação lista as permissões necessárias. Consulte Guia do usuário da Amazon VPC.  | 

## Acesso somente leitura

### Nome da política gerenciada pela AWS: *ReadOnlyAccess*

**Caso de uso:** esse usuário requer acesso somente leitura a todos os recursos de uma conta da AWS.

**Atualizações da política:** AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

**Descrição da política:** essa política concede permissões para listar, obter, descrever e exibir recursos e seus atributos. Ela não inclui funções de modificação, como criar ou excluir. Essa política inclui acesso somente leitura a serviços da AWS relacionados à segurança, como AWS Identity and Access Management e AWS Billing and Cost Management. Visualize a política para obter a lista completa de serviços e ações compatíveis com essa política.

## Função de trabalho do auditor de segurança

Nome da política gerenciada pela AWS: SecurityAudit

Caso de uso: Este usuário monitora contas quanto à conformidade com os requisitos de segurança. Este usuário pode acessar registros e eventos para investigar potenciais violações de segurança ou possíveis atividades maliciosas.

Atualizações da política: AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

Descrição da política: Esta política concede permissões para visualizar dados de configuração para muitos serviços da AWS e para revisar seus registros.

## Suporte a função de trabalho do usuário

### Nome da política gerenciada pela AWS: *SupportUser*

**Caso de uso:** Este usuário entra em contato com o Suporte da AWS cria casos de suporte e visualiza o status de casos existentes.

**Atualizações da política:** AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

**Descrição da política:** Esta política concede permissões para criar e atualizar casos do Suporte da AWS.

## Função de trabalho do administrador do sistema

### Nome da política gerenciada pela AWS: *SystemAdministrator*

**Caso de uso:** Este usuário configura e mantém recursos para as operações de desenvolvimento.

**Atualizações da política:** AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

**Descrição da política:** Esta política concede permissões para criar e manter recursos em uma grande variedade de serviços da AWS, incluindo AWS CloudTrail, Amazon CloudWatch, AWS CodeCommit, AWS CodeDeploy, AWS Config, AWS Directory Service, Amazon EC2, AWS Identity and Access Management, AWS Key Management Service, AWS Lambda, Amazon RDS, Route 53, Amazon S3, Amazon SES, Amazon SQS, AWS Trusted Advisor e Amazon VPC.

Esta função de trabalho requer a capacidade de passar funções para serviços da AWS. A política concede iam:GetRole e iam:PassRole apenas para as funções listadas na seguinte tabela. Para obter mais informações, consulte Criar funções e anexar políticas (console) adiante neste tópico.

### Funções de serviço opcionais do IAM para a função de trabalho do administrador de sistemas

 | Caso de uso | Nome da função (* é um curinga) | Tipo de função de serviço a selecionar | Política gerenciada pela AWS a selecionar | 
 | Permitir que aplicativos em execução em instâncias do EC2 em um cluster do Amazon ECS acessem o Amazon ECS | ecr-sysadmin-* | Função do Amazon EC2 para o EC2 Container Service | AmazonEC2ContainerServiceforEC2Role | 
 | Permitir que um usuário monitore bancos de dados | rds-monitoring-role | Função do Amazon RDS para monitoramento avançado | AmazonRDSEnhancedMonitoringRole | 
 | Permitir que aplicativos em execução em instâncias do EC2 acessem recursos da AWS. | ec2-sysadmin-* | Amazon EC2 | Política de amostra para a função que concede acesso a um bucket do S3 como mostrado no Guia do usuário do Amazon EC2 para instâncias do Linux; personalizar conforme necessário | 
 | Permitir que o Lambda leia streams do DynamoDB e grave em registros do CloudWatch | lambda-sysadmin-* | AWS Lambda | AWSLambdaDynamoDBExecutionRole | 

## Função de trabalho do usuário somente para visualização

### Nome da política gerenciada pela AWS: *ViewOnlyAccess*

**Caso de uso:** Este usuário pode visualizar uma lista de recursos e metadados básicos da AWS na conta em todos os serviços. O usuário não pode ler o conteúdo do recurso ou metadados além da cota e informações da lista para os recursos.

**Atualizações da política:** AWS mantém e atualiza esta política. Para obter um histórico de alterações desta política, visualize a política no console do IAM e escolha a guia Policy versions (Versões da política). Para obter mais informações sobre atualizações da política de função de trabalho, consulte Atualizações das políticas gerenciadas pela AWS para funções de trabalho.

**Descrição da política:** esta política concede a List*, Describe*, Get*, View* e Lookup* acesso a recursos para a maioria dos serviços da AWS. Para ver quais ações essa política inclui para cada serviço, consulte ViewOnlyAccess.

## Atualizações das políticas gerenciadas pela AWS para funções de trabalho

Todas essas políticas são mantidas pela AWS e atualizadas para oferecer suporte para novos serviços e novos recursos à medida que são adicionados pela AWS. Essas políticas não podem ser modificadas pelos clientes. Você pode fazer uma cópia da política e, em seguida, modificar a cópia, mas essa cópia não é atualizada automaticamente à medida que a AWS introduz novos serviços e operações de API.

Para uma política de função de trabalho, visualize o histórico de versões e a hora e a data de cada atualização no console da IAM. Para isso, use os links desta página para visualizar os detalhes da política. Escolha então a guia Policy versions (Versões da política) para ver as versões. Esta página mostra as últimas 25 versões de uma política. Para exibir todas as versões de uma política, chame o comando da AWS CLI get-policy-version ou a operação da API GetPolicyVersion.


[Artigo original](https://docs.aws.amazon.com/pt_br/IAM/latest/UserGuide/access_policies_job-functions.html)