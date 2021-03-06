# Pol�ticas gerenciadas pela AWS para fun��es de trabalho

Para come�ar a adicionar permiss�es �s suas identidades do IAM (usu�rios, grupos de usu�rios e fun��es), voc� pode usar pol�ticas gerenciadas pela AWS. � necess�rio tempo e experi�ncia para criar pol�ticas gerenciadas pelo cliente do IAM que forne�am � sua equipe apenas as permiss�es necess�rias. As pol�ticas gerenciadas pela AWS cobrem casos de uso comuns e est�o dispon�veis em sua conta da AWS. Para obter mais informa��es sobre pol�ticas gerenciadas da AWS, consulte Pol�ticas gerenciadas pela AWS.

Pol�ticas gerenciadas pela AWS para fun��es de trabalho s�o projetadas para se alinhar de perto com fun��es de trabalho comuns no setor de TI. Voc� pode usar essas pol�ticas para facilmente conceder as permiss�es necess�rias para executar as tarefas esperadas de algu�m com determinada fun��o de trabalho. Essas pol�ticas consolidam permiss�es para v�rios servi�os em uma �nica pol�tica com a qual seja mais f�cil trabalhar do que ter permiss�es espalhadas atrav�s de muitas pol�ticas.

Voc� pode anexar pol�ticas gerenciadas pela AWS, incluindo fun��es de trabalho, a qualquer identidade do IAM (usu�rios, grupo de usu�rios ou fun��es).

## Use fun��es para combinar servi�os

Algumas das pol�ticas usam fun��es de servi�o do IAM para ajud�-lo a aproveitar os recursos encontrados em outros servi�os da AWS. Essas pol�ticas concedem acesso a iam:passrole, o que permite que um usu�rio com a pol�tica passe uma fun��o para um servi�o da AWS. Essa fun��o delega permiss�es do IAM para o servi�o da AWS executar a��es em seu nome.

Voc� deve criar as fun��es de acordo com as suas necessidades. Por exemplo, a pol�tica de administrador de rede permite que um usu�rio com a pol�tica passe uma fun��o chamada "flow-logs-vpc" para o servi�o Amazon CloudWatch. O CloudWatch usa essa fun��o para registrar e capturar o tr�fego de IP para VPCs criadas pelo usu�rio.

Para seguir as melhores pr�ticas de seguran�a, as pol�ticas para fun��es de trabalho incluem filtros que limitam os nomes de fun��es v�lidas que podem ser transmitidas. Isso ajuda a evitar a concess�o de permiss�es desnecess�rias. Se seus usu�rios precisam das fun��es de servi�o opcionais, voc� deve criar uma fun��o que siga a conven��o de nomenclatura especificada na pol�tica. Ent�o, conceda permiss�es para a fun��o. Ao concluir, o usu�rio pode configurar o servi�o para usar a fun��o, concedendo-lhe todas as permiss�es que a fun��o oferece.

Nas seguintes se��es, cada nome de pol�tica � um link para a p�gina de detalhes da pol�tica no Console de gerenciamento da AWS. L�, � poss�vel consultar o documento de pol�tica e revisar as permiss�es que ela concede.

## Fun��o de trabalho do administrador

### Nome da pol�tica gerenciada pela AWS: *AdministratorAccess*

**Caso de uso:** Este usu�rio tem acesso total e pode delegar permiss�es para todos os servi�os e recursos na AWS.

**Atualiza��es da pol�tica:** AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

**Descri��o da pol�tica:** Esta pol�tica concede todas as a��es para todos os servi�os da AWS e para todos os recursos na conta.

...
nota
Para um usu�rio ou uma fun��o do IAM acessar o console do AWS Billing and Cost Management com as permiss�es nessa pol�tica, voc� deve primeiro ativar o usu�rio do IAM e o acesso da fun��o. Para fazer isso, siga as instru��es na Etapa 1 do tutorial sobre como delegar acesso ao console de faturamento.
...

## Fun��o de trabalho de faturamento

### Nome da pol�tica gerenciada pela AWS: *Billing*

**Caso de uso:** Este usu�rio precisa visualizar informa��es de faturamento, configurar e autorizar pagamentos. O usu�rio pode monitorar os custos acumulados para cada servi�o da AWS.

**Atualiza��es da pol�tica:** AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

**Descri��o da pol�tica:** Esta pol�tica concede permiss�es completas para gerenciar faturamento, custos, meios de pagamento, or�amentos e relat�rios.

...
nota
Para um usu�rio ou uma fun��o do IAM acessar o console do AWS Billing and Cost Management com as permiss�es nessa pol�tica, voc� deve primeiro ativar o usu�rio do IAM e o acesso da fun��o. Para fazer isso, siga as instru��es na Etapa 1 do tutorial sobre como delegar acesso ao console de faturamento.
...

## Fun��o de trabalho de administrador de banco de dados

### Nome da pol�tica gerenciada pela AWS: *DatabaseAdministrator*

**Caso de uso:** Este usu�rio define, configura e mant�m bancos de dados na Nuvem AWS.

**Atualiza��es da pol�tica:** AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

**Descri��o da pol�tica:** Esta pol�tica concede permiss�es para criar, configurar e manter bancos de dados. Isso inclui o acesso a servi�os de banco de dados da AWS, como o Amazon DynamoDB, o Amazon Relational Database Service (RDS) e o Amazon Redshift. Visualize a pol�tica para obter a lista completa de servi�os de banco de dados aos quais essa pol�tica oferece suporte.

Essa pol�tica de fun��o de trabalho oferece suporte � capacidade de passar fun��es para servi�os da AWS. A pol�tica permite a a��o iam:PassRole somente para as fun��es listadas na tabela a seguir. Para obter mais informa��es, consulte Criar fun��es e anexar pol�ticas (console) adiante neste t�pico.

### Fun��es de servi�o opcionais do IAM para a fun��o de trabalho do administrador de banco de dados

 | Caso de uso | Nome da fun��o (* � um curinga) | Tipo de fun��o de servi�o a selecionar | Selecione esta pol�tica gerenciada pela AWS | 
 | Permitir que o usu�rio monitore bancos de dados do RDS | rds-monitoring-role | Fun��o do Amazon RDS para monitoramento avan�ado | AmazonRDSEnhancedMonitoringRole | 
 | Permitir que AWS Lambda monitore seu banco de dados e acesse bancos de dados externos | rdbms-lambda-access | Amazon EC2 | AWSLambda_FullAccess | 
 | Permitir que o Lambda fa�a upload de arquivos para clusters do Amazon S3 e do Amazon Redshift com o DynamoDB | lambda_exec_role | AWS Lambda | Criar uma nova pol�tica gerenciada conforme definido no Blog de Big Data da AWS | 
 | Permitir que fun��es do Lambda atuem como triggers para suas tabelas do DynamoDB | lambda-dynamodb-* | AWS Lambda | AWSLambdaDynamoDBExecutionRole | 
 | Permitir que fun��es do Lambda acessem o Amazon RDS em uma VPC | lambda-vpc-execution-role | Criar uma fun��o com uma pol�tica de confian�a conforme definido no AWS Lambda Developer Guide | AWSLambdaVPCAccessExecutionRole | 
 | Permitir que o AWS Data Pipeline acesse seus recursos da AWS | DataPipelineDefaultRole | Criar uma fun��o com uma pol�tica de confian�a conforme definido no Guia do desenvolvedor do AWS Data Pipeline | A documenta��o do AWS Data Pipeline lista as permiss�es necess�rias para este caso de uso. Consulte Fun��es do IAM para AWS Data Pipeline | 
 | Permitir que seus aplicativos em execu��o em inst�ncias do Amazon EC2 acesses seus recursos da AWS | DataPipelineDefaultResourceRole | Criar uma fun��o com uma pol�tica de confian�a conforme definido no Guia do desenvolvedor do AWS Data Pipeline | AmazonEC2RoleforDataPipelineRole | 

# Fun��o de trabalho de cientista de dados

## Nome da pol�tica gerenciada pela AWS: *DataScientist*

**Caso de uso:** Este usu�rio executa trabalhos e consultas do Hadoop. O usu�rio tamb�m acessa e analisa informa��es para an�lise de dados e intelig�ncia de neg�cios.

**Atualiza��es da pol�tica:** AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

**Descri��o da pol�tica:** Esta pol�tica concede permiss�es para criar, gerenciar e executar consultas em um cluster do Amazon EMR e executar an�lises de dados com ferramentas como o Amazon QuickSight. A pol�tica inclui o acesso a servi�os adicionais de cientistas de dados, como o AWS Data Pipeline, o Amazon EC2, o Amazon Kinesis, o Amazon Machine Learning, e o SageMaker. Visualize a pol�tica para obter a lista completa de servi�os de cientistas de dados aos quais essa pol�tica oferece suporte.

Essa pol�tica de fun��o de trabalho oferece suporte � capacidade de passar fun��es para servi�os da AWS. Uma declara��o permite passar qualquer fun��o para o SageMaker. Outra declara��o permite a a��o iam:PassRole somente para as fun��es listadas na tabela a seguir. Para obter mais informa��es, consulte Criar fun��es e anexar pol�ticas (console) adiante neste t�pico.

### Fun��es de servi�o opcionais do IAM para a fun��o de trabalho de cientista de dados

 | Caso de uso | Nome da fun��o (* � um curinga) | Tipo de fun��o de servi�o a selecionar | Pol�tica gerenciada pela AWS a selecionar | 
 | Permitir que inst�ncias do Amazon EC2 acessem servi�os e recursos adequados para clusters | EMR-EC2_DefaultRole | Amazon EMR para EC2 | AmazonElasticMapReduceforEC2Role | 
 | Permitir acesso ao Amazon EMR para acessar o servi�o e recursos do Amazon EC2 para clusters | EMR_DefaultRole | Amazon EMR | AmazonEMRServicePolicy_v2 | 
 | Permitir que Kinesis Kinesis Data Analytics acesse fontes de dados de streaming | kinesis-* | Criar uma fun��o com uma pol�tica de confian�a conforme definido no Blog de Big Data da AWS. | Consulte o Blog de Big Data da AWS, que descreve quatro op��es poss�veis, dependendo do caso de uso | 
 | Permitir que o AWS Data Pipeline acesse seus recursos da AWS | DataPipelineDefaultRole | Criar uma fun��o com uma pol�tica de confian�a conforme definido no Guia do desenvolvedor do AWS Data Pipeline | A documenta��o do AWS Data Pipeline lista as permiss�es necess�rias para este caso de uso. Consulte Fun��es do IAM para AWS Data Pipeline | 
 | Permitir que seus aplicativos em execu��o em inst�ncias do Amazon EC2 acesses seus recursos da AWS | DataPipelineDefaultResourceRole | Criar uma fun��o com uma pol�tica de confian�a conforme definido no Guia do desenvolvedor do AWS Data Pipeline | AmazonEC2RoleforDataPipelineRole | 

## Fun��o de trabalho do usu�rio avan�ado do desenvolvedor

### Nome da pol�tica gerenciada pela AWS: *PowerUserAccess*

**Caso de uso:** Este usu�rio executa tarefas de desenvolvimento de aplicativos e pode criar e configurar recursos e servi�os compat�veis com o desenvolvimento consciente de aplicativos da AWS.

**Atualiza��es da pol�tica:** AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

**Descri��o da pol�tica:** A primeira instru��o dessa pol�tica usa o elemento NotAction para permitir todas as a��es para todos os servi�os do AWS e para todos os recursos, exceto AWS Identity and Access Management e AWS Organizations. A segunda instru��o concede permiss�es do IAM para criar uma fun��o vinculada ao servi�o. Isso � necess�rio para alguns servi�os que devem acessar recursos em outro servi�o, como um bucket do Amazon S3. Ela tamb�m concede permiss�es do Organiza��es para visualizar informa��es sobre a organiza��o do usu�rio, incluindo o e-mail da conta de gerenciamento e as limita��es da organiza��o. Embora essa pol�tica limite IAM e Organiza��es conceda acesso, ela permite que o usu�rio execute todas as AWS SSO a��es se AWS SSO estiver habilitado.

## Fun��o de trabalho de administrador de rede

### Nome da pol�tica gerenciada pela AWS: *NetworkAdministrator*

**Caso de uso:** Este usu�rio � respons�vel pela configura��o e manuten��o dos recursos de rede da AWS.

**Atualiza��es da pol�tica:** AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

**Descri��o da pol�tica:** Esta pol�tica concede permiss�es para criar e manter recursos de rede no Auto Scaling, Amazon EC2, AWS Direct Connect, Route 53, Amazon CloudFront, Elastic Load Balancing, AWS Elastic Beanstalk, Amazon SNS, CloudWatch, CloudWatch Logs, Amazon S3, IAM e Amazon Virtual Private Cloud.

Esta fun��o de trabalho requer a capacidade de passar fun��es para servi�os da AWS. A pol�tica concede iam:GetRole e iam:PassRole apenas para as fun��es listadas na seguinte tabela. Para obter mais informa��es, consulte Criar fun��es e anexar pol�ticas (console) adiante neste t�pico.

Fun��es de servi�o opcionais do IAM para a fun��o de trabalho do administrador de rede

 | Caso de uso | Nome da fun��o (* � um curinga) | Tipo de fun��o de servi�o a selecionar | Pol�tica gerenciada pela AWS a selecionar  | 

 |  Permitir que a Amazon VPC crie e gerencie registros no CloudWatch Logs em nome do usu�rio para monitorar o tr�fego de IP de entrada e sa�da de sua VPC | flow-logs-* | Criar uma fun��o com uma pol�tica de confian�a conforme definido no Guia do usu�rio da Amazon VPC | Este caso de uso n�o tem uma pol�tica gerenciada pela AWS existente, mas a documenta��o lista as permiss�es necess�rias. Consulte Guia do usu�rio da Amazon VPC.  | 

## Acesso somente leitura

### Nome da pol�tica gerenciada pela AWS: *ReadOnlyAccess*

**Caso de uso:** esse usu�rio requer acesso somente leitura a todos os recursos de uma conta da AWS.

**Atualiza��es da pol�tica:** AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

**Descri��o da pol�tica:** essa pol�tica concede permiss�es para listar, obter, descrever e exibir recursos e seus atributos. Ela n�o inclui fun��es de modifica��o, como criar ou excluir. Essa pol�tica inclui acesso somente leitura a servi�os da AWS relacionados � seguran�a, como AWS Identity and Access Management e AWS Billing and Cost Management. Visualize a pol�tica para obter a lista completa de servi�os e a��es compat�veis com essa pol�tica.

## Fun��o de trabalho do auditor de seguran�a

Nome da pol�tica gerenciada pela AWS: SecurityAudit

Caso de uso: Este usu�rio monitora contas quanto � conformidade com os requisitos de seguran�a. Este usu�rio pode acessar registros e eventos para investigar potenciais viola��es de seguran�a ou poss�veis atividades maliciosas.

Atualiza��es da pol�tica: AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

Descri��o da pol�tica: Esta pol�tica concede permiss�es para visualizar dados de configura��o para muitos servi�os da AWS e para revisar seus registros.

## Suporte a fun��o de trabalho do usu�rio

### Nome da pol�tica gerenciada pela AWS: *SupportUser*

**Caso de uso:** Este usu�rio entra em contato com o Suporte da AWS cria casos de suporte e visualiza o status de casos existentes.

**Atualiza��es da pol�tica:** AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

**Descri��o da pol�tica:** Esta pol�tica concede permiss�es para criar e atualizar casos do Suporte da AWS.

## Fun��o de trabalho do administrador do sistema

### Nome da pol�tica gerenciada pela AWS: *SystemAdministrator*

**Caso de uso:** Este usu�rio configura e mant�m recursos para as opera��es de desenvolvimento.

**Atualiza��es da pol�tica:** AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

**Descri��o da pol�tica:** Esta pol�tica concede permiss�es para criar e manter recursos em uma grande variedade de servi�os da AWS, incluindo AWS CloudTrail, Amazon CloudWatch, AWS CodeCommit, AWS CodeDeploy, AWS Config, AWS Directory Service, Amazon EC2, AWS Identity and Access Management, AWS Key Management Service, AWS Lambda, Amazon RDS, Route 53, Amazon S3, Amazon SES, Amazon SQS, AWS Trusted Advisor e Amazon VPC.

Esta fun��o de trabalho requer a capacidade de passar fun��es para servi�os da AWS. A pol�tica concede iam:GetRole e iam:PassRole apenas para as fun��es listadas na seguinte tabela. Para obter mais informa��es, consulte Criar fun��es e anexar pol�ticas (console) adiante neste t�pico.

### Fun��es de servi�o opcionais do IAM para a fun��o de trabalho do administrador de sistemas

 | Caso de uso | Nome da fun��o (* � um curinga) | Tipo de fun��o de servi�o a selecionar | Pol�tica gerenciada pela AWS a selecionar | 
 | Permitir que aplicativos em execu��o em inst�ncias do EC2 em um cluster do Amazon ECS acessem o Amazon ECS | ecr-sysadmin-* | Fun��o do Amazon EC2 para o EC2 Container Service | AmazonEC2ContainerServiceforEC2Role | 
 | Permitir que um usu�rio monitore bancos de dados | rds-monitoring-role | Fun��o do Amazon RDS para monitoramento avan�ado | AmazonRDSEnhancedMonitoringRole | 
 | Permitir que aplicativos em execu��o em inst�ncias do EC2 acessem recursos da AWS. | ec2-sysadmin-* | Amazon EC2 | Pol�tica de amostra para a fun��o que concede acesso a um bucket do S3 como mostrado no Guia do usu�rio do Amazon EC2 para inst�ncias do Linux; personalizar conforme necess�rio | 
 | Permitir que o Lambda leia streams do DynamoDB e grave em registros do CloudWatch | lambda-sysadmin-* | AWS Lambda | AWSLambdaDynamoDBExecutionRole | 

## Fun��o de trabalho do usu�rio somente para visualiza��o

### Nome da pol�tica gerenciada pela AWS: *ViewOnlyAccess*

**Caso de uso:** Este usu�rio pode visualizar uma lista de recursos e metadados b�sicos da AWS na conta em todos os servi�os. O usu�rio n�o pode ler o conte�do do recurso ou metadados al�m da cota e informa��es da lista para os recursos.

**Atualiza��es da pol�tica:** AWS mant�m e atualiza esta pol�tica. Para obter um hist�rico de altera��es desta pol�tica, visualize a pol�tica no console do IAM e escolha a guia Policy versions (Vers�es da pol�tica). Para obter mais informa��es sobre atualiza��es da pol�tica de fun��o de trabalho, consulte Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho.

**Descri��o da pol�tica:** esta pol�tica concede a List*, Describe*, Get*, View* e Lookup* acesso a recursos para a maioria dos servi�os da AWS. Para ver quais a��es essa pol�tica inclui para cada servi�o, consulte ViewOnlyAccess.

## Atualiza��es das pol�ticas gerenciadas pela AWS para fun��es de trabalho

Todas essas pol�ticas s�o mantidas pela AWS e atualizadas para oferecer suporte para novos servi�os e novos recursos � medida que s�o adicionados pela AWS. Essas pol�ticas n�o podem ser modificadas pelos clientes. Voc� pode fazer uma c�pia da pol�tica e, em seguida, modificar a c�pia, mas essa c�pia n�o � atualizada automaticamente � medida que a AWS introduz novos servi�os e opera��es de API.

Para uma pol�tica de fun��o de trabalho, visualize o hist�rico de vers�es e a hora e a data de cada atualiza��o no console da IAM. Para isso, use os links desta p�gina para visualizar os detalhes da pol�tica. Escolha ent�o a guia Policy versions (Vers�es da pol�tica) para ver as vers�es. Esta p�gina mostra as �ltimas 25 vers�es de uma pol�tica. Para exibir todas as vers�es de uma pol�tica, chame o comando da AWS CLI get-policy-version ou a opera��o da API GetPolicyVersion.


[Artigo original](https://docs.aws.amazon.com/pt_br/IAM/latest/UserGuide/access_policies_job-functions.html)