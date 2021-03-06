# Uso de fun��es vinculadas a servi�o

Uma fun��o vinculada ao servi�o � um tipo exclusivo de fun��o do IAM que � vinculada diretamente a um servi�o da AWS. As fun��es vinculadas a servi�os s�o predefinidas pelo servi�o e incluem todas as permiss�es que o servi�o requer para chamar outros servi�os da AWS em seu nome. O servi�o vinculado tamb�m define como criar, modificar e excluir uma fun��o vinculada a servi�o. Um servi�o pode criar ou excluir a fun��o automaticamente. Ele pode permitir que voc� crie, modifique ou exclua a fun��o como parte de um assistente ou processo no servi�o. Ou pode exigir que voc� use o IAM para criar ou excluir a fun��o. Independente do m�todo, as fun��es vinculadas a servi�o facilitam a configura��o de um servi�o, pois voc� n�o precisa adicionar manualmente as permiss�es necess�rias para o servi�o concluir as a��es em seu nome.

O servi�o vinculado define as permiss�es de suas fun��es vinculadas ao servi�o e, a menos que definido em contr�rio, somente aquele servi�o pode assumir as fun��es. As permiss�es definidas incluem a pol�tica de confian�a e a pol�tica de permiss�es, e essa pol�tica n�o pode ser anexada a nenhuma outra entidade do IAM.

Voc� pode excluir as fun��es somente depois de primeiro excluir seus recursos relacionados. Isso protege seus recursos, pois voc� n�o pode remover inadvertidamente a permiss�o para acessar os recursos.

...
dica
Para obter informa��es sobre quais servi�os oferecem suporte a fun��es vinculadas a servi�os, consulte Servi�os da AWS compat�veis com o IAM e procure os servi�os que t�m Sim na coluna Fun��o vinculada ao servi�o. Escolha um Sim com um link para exibir a documenta��o da fun��o vinculada a servi�o desse servi�o.
..

## Permiss�es de fun��o vinculada ao servi�o

Configure as permiss�es para que uma entidade do IAM (usu�rio ou fun��o) permita que o usu�rio ou fun��o crie, edite ou exclua a fun��o vinculada ao servi�o.

...
nota
O ARN de uma fun��o vinculada ao servi�o inclui uma entidade principal do servi�o que � indicada nas pol�ticas abaixo como SERVICE-NAME.amazonaws.com. N�o tente adivinhar a entidade principal do servi�o, pois ela faz distin��o entre mai�sculas e min�sculas, e o formato pode variar entre os servi�os da AWS. Para visualizar a entidade principal do servi�o, consulte a documenta��o da fun��o vinculada ao servi�o.
...

### Para permitir que uma entidade do IAM crie uma fun��o vinculada ao servi�o

Adicione a seguinte pol�tica � entidade do IAM que precisa criar a fun��o vinculada ao servi�o.

...
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": "iam:CreateServiceLinkedRole",
            "Resource": "arn:aws:iam::*:role/aws-service-role/SERVICE-NAME.amazonaws.com/SERVICE-LINKED-ROLE-NAME-PREFIX*",
            "Condition": {"StringLike": {"iam:AWSServiceName": "SERVICE-NAME.amazonaws.com"}}
        },
        {
            "Effect": "Allow",
            "Action": [
                "iam:AttachRolePolicy",
                "iam:PutRolePolicy"
            ],
            "Resource": "arn:aws:iam::*:role/aws-service-role/SERVICE-NAME.amazonaws.com/SERVICE-LINKED-ROLE-NAME-PREFIX*"
        }
    ]
}
...

### Para permitir que uma entidade do IAM crie qualquer fun��o vinculada ao servi�o

Adicione a seguinte instru��o � pol�tica de permiss�es da entidade do IAM que precisa criar uma fun��o vinculada ao servi�o ou qualquer fun��o de servi�o que inclua as pol�ticas necess�rias. Esta declara��o de pol�tica n�o permite que a entidade do IAM anexe uma pol�tica � fun��o.

...
{
    "Effect": "Allow",
    "Action": "iam:CreateServiceLinkedRole",
    "Resource": "arn:aws:iam::*:role/aws-service-role/*"
}
...

### Para permitir que uma entidade do IAM edite a descri��o de todas as fun��es vinculadas ao servi�o

Adicione a seguinte instru��o � pol�tica de permiss�es da entidade do IAM que precisa editar uma descri��o de uma fun��o vinculada ao servi�o ou qualquer fun��o de servi�o.

...
{
    "Effect": "Allow",
    "Action": "iam:UpdateRoleDescription",
    "Resource": "arn:aws:iam::*:role/aws-service-role/*"
}
...

### Para permitir que uma entidade do IAM exclua uma fun��o vinculada ao servi�o espec�fica

Adicione a seguinte instru��o � pol�tica de permiss�es para a entidade do IAM que precisa excluir a fun��o vinculada ao servi�o.

...
{
    "Effect": "Allow",
    "Action": [
        "iam:DeleteServiceLinkedRole",
        "iam:GetServiceLinkedRoleDeletionStatus"
    ],
    "Resource": "arn:aws:iam::*:role/aws-service-role/SERVICE-NAME.amazonaws.com/SERVICE-LINKED-ROLE-NAME-PREFIX*"
}
...

### Para permitir que uma entidade do IAM exclua qualquer fun��o vinculada ao servi�o

Adicione a seguinte instru��o � pol�tica de permiss�es da entidade do IAM que precisa excluir uma fun��o vinculada ao servi�o, mas n�o a fun��o de servi�o.

...
{
    "Effect": "Allow",
    "Action": [
        "iam:DeleteServiceLinkedRole",
        "iam:GetServiceLinkedRoleDeletionStatus"
    ],
    "Resource": "arn:aws:iam::*:role/aws-service-role/*"
}
...

### Para permitir que uma entidade do IAM transmita uma fun��o existente para o servi�o

Alguns servi�os do AWS permitem que voc� transmita uma fun��o existente para o servi�o, ao inv�s de criar uma nova fun��o vinculada ao servi�o. Para fazer isso, um usu�rio deve ter permiss�es para transmitir a fun��o para o servi�o. Adicione a seguinte instru��o � pol�tica de permiss�es para a entidade do IAM necess�ria para transmitir a fun��o. Esta declara��o de pol�tica tamb�m permite que a entidade visualize uma lista de fun��es a partir da qual elas podem escolher a fun��o a ser transmitida. Para obter mais informa��es, consulte Conceder permiss�es a um usu�rio para passar uma fun��o para um servi�o da AWS.

...
{
    "Effect": "Allow",
    "Action": [
        "iam:ListRoles",
        "iam:PassRole"
    ],
    "Resource": "arn:aws:iam::123456789012:role/my-role-for-XYZ"
}
...

### Transfer�ncia de permiss�es de fun��o vinculada ao servi�o

As permiss�es concedidas por uma fun��o vinculada ao servi�o s�o indiretamente transfer�veis para outros usu�rios e fun��es. Quando voc� permitir que um servi�o execute opera��es em outros servi�os, o servi�o de origem poder� usar essas permiss�es no futuro. Se outro usu�rio ou fun��o tiver permiss�o para executar a��es no servi�o de origem, ele poder� assumir a fun��o e acessar recursos em outros servi�os. Isso significa que o outro usu�rio ou fun��o pode acessar indiretamente os outros servi�os.

Por exemplo, quando voc� cria uma inst�ncia de banco de dados do Amazon RDS, o RDS cria a fun��o vinculada ao servi�o para voc�. Essa fun��o permite que o RDS chame o Amazon EC2, o Amazon SNS, o Amazon CloudWatch Logs, e o Amazon Kinesis em seu nome sempre que voc� editar a inst�ncia de banco de dados. Se voc� criar uma pol�tica para permitir que usu�rios e fun��es em sua conta ou em outra conta acessem essa inst�ncia do Amazon RDS, o RDS ainda poder� usar essa fun��o para fazer altera��es no EC2, no SNS, no CloudWatch Logs e no Kinesis em nome deles. O novo usu�rio ou fun��o pode editar indiretamente recursos nesses outros servi�os.

### Criar uma fun��o vinculada ao servi�o

O m�todo que voc� usa para criar uma fun��o vinculada ao servi�o depende do servi�o. Em alguns casos, voc� n�o precisa criar manualmente uma fun��o vinculada ao servi�o. Por exemplo, quando voc� conclui uma a��o espec�fica (como a cria��o de um recurso) no servi�o, o servi�o pode criar a fun��o vinculada ao servi�o para voc�. Ou, se voc� estava usando um servi�o antes que ele come�ou a oferecer suporte a fun��es vinculadas ao servi�o, o servi�o pode ter criado automaticamente a fun��o na sua conta. Para saber mais, consulte Uma nova fun��o apareceu na minha conta da AWS.

Em outros casos, o servi�o pode oferecer suporte � cria��o de uma fun��o vinculada ao servi�o manualmente usando o console, API ou CLI do servi�o. Para obter informa��es sobre quais servi�os oferecem suporte a fun��es vinculadas a servi�os, consulte Servi�os da AWS compat�veis com o IAM e procure os servi�os que t�m Sim na coluna Fun��o vinculada ao servi�o. Para saber se o servi�o oferece suporte para criar a fun��o vinculada ao servi�o, escolha o link Sim para visualizar a documenta��o da fun��o vinculada a esse servi�o.

Se o servi�o n�o permitir a cria��o da fun��o, voc� pode usar o IAM para criar a fun��o vinculada ao servi�o.

...
#### Importante

As fun��es vinculadas a servi�os contam para o limite das fun��es do IAM em uma conta da AWS, mas, se tiver atingido o limite, voc� ainda poder� criar esse tipo de fun��o na conta. Somente as fun��es vinculadas ao servi�o podem exceder o limite.
...

### Criar uma fun��o vinculada ao servi�o (console)

Antes de criar uma fun��o vinculada ao servi�o no IAM, descubra se o servi�o vinculado cria automaticamente as fun��es vinculadas ao servi�o. Al�m disso, aprenda se voc� pode criar a fun��o do console, API ou CLI do servi�o.

### Para criar uma fun��o vinculada ao servi�o (console)

1. Fa�a login no Console de gerenciamento da AWS e abra o console da IAM em https://console.aws.amazon.com/iam/.

2. No painel de navega��o do console do IAM, selecione Roles (Fun��es). Ent�o, escolha Criar fun��o.

3. Escolha o tipo de fun��o AWS Service (Servi�o da AWS) e escolha o servi�o que ter� permiss�o para assumir essa fun��o.

4. Escolha o caso de uso para o seu servi�o. Se o servi�o especificado tem apenas um caso de uso, ele � selecionado para voc�. Casos de uso s�o definidos pelo servi�o para incluir a pol�tica de confian�a exigida pelo servi�o. Ent�o, escolha Pr�ximo: Permiss�es.

5. Escolha uma ou mais pol�ticas de permiss�es a serem anexadas � fun��o. Dependendo do caso de uso que voc� selecionou, o servi�o pode executar uma das seguintes a��es:

	- Definir as permiss�es usadas pela fun��o

	- Permitir que voc� escolha um conjunto limitado de permiss�es

	- Permitir que voc� escolha a partir de quaisquer permiss�es

	- Permitir que voc� opte por n�o selecionar nenhuma pol�tica no momento, criar pol�ticas mais tarde e, em seguida, anex�-las � fun��o.

Marque a caixa ao lado da pol�tica que atribui as permiss�es que voc� deseja que a fun��o tenha e escolha Next: Tags (Pr�ximo: tags).

...
nota
As permiss�es que voc� especificar estar�o dispon�veis para qualquer entidade que usar a fun��o. Por padr�o, uma fun��o n�o tem nenhuma permiss�es.
...

6. Selecione Next: Review. Voc� n�o pode anexar tags para fun��es vinculadas ao servi�o durante a cria��o. Para obter mais informa��es sobre como usar tags no IAM, consulte Marcar recursos do IAM.

7. Para Nome da fun��o, o grau de personaliza��o do nome da fun��o � definido pelo servi�o. Se o servi�o define o nome da fun��o, essa op��o n�o � edit�vel. Em outros casos, o servi�o pode definir um prefixo para a fun��o e permitir que voc� digite um sufixo opcional.

 Se poss�vel, insira um sufixo do nome da fun��o a ser adicionado ao nome padr�o. O sufixo ajuda a identificar a finalidade dessa fun��o. Os nomes de fun��o devem ser exclusivos em sua conta da AWS. Eles n�o s�o diferenciados por letras mai�sculas e min�sculas. Por exemplo, n�o � poss�vel criar fun��es chamadas <service-linked-role-name>_SAMPLE e <service-linked-role-name>_sample. Como v�rias entidades podem fazer refer�ncia � fun��o, n�o � poss�vel editar o nome da fun��o depois que ela � criada.

8. Opcional) Em Descri��o da fun��o, edite a descri��o para a nova fun��o vinculada ao servi�o.

9. Revise a fun��o e escolha Create role.

## Criar uma fun��o vinculada ao servi�o (AWS CLI)

Antes de criar uma fun��o vinculada ao servi�o no IAM, descubra se o servi�o vinculado cria automaticamente as fun��es vinculadas ao servi�o e se voc� pode criar a fun��o na CLI do servi�o. Se a CLI do servi�o n�o for suportada, voc� pode usar comandos do IAM para criar uma fun��o vinculada ao servi�o com a pol�tica de confian�a e pol�ticas em linha de que o servi�o precisa assumir a fun��o.

### Para criar uma fun��o vinculada ao servi�o (AWS CLI)

Execute o seguinte comando:

...
aws iam create-service-linked-role --aws-service-name SERVICE-NAME.amazonaws.com
...

## Criar uma fun��o vinculada ao servi�o (API da AWS)

Antes de criar uma fun��o vinculada ao servi�o no IAM, descubra se o servi�o vinculado cria automaticamente as fun��es vinculadas ao servi�o e se voc� pode criar a fun��o na API do servi�o. Se a API do servi�o n�o for suportada, voc� pode usar a API do AWS para criar uma fun��o vinculada ao servi�o com a pol�tica de confian�a e pol�ticas em linha de que o servi�o precisa para assumir a fun��o.

### Para criar uma fun��o vinculada ao servi�o (API da AWS)

Use a chamada de API CreateServiceLinkedRole. Na solicita��o, especifique o nome do servi�o na forma de SERVICE_NAME_URL.amazonaws.com.

Por exemplo, para criar a fun��o vinculada ao servi�o Lex Bots, use lex.amazonaws.com.

## Editar uma fun��o vinculada ao servi�o

O m�todo que voc� usa para editar uma fun��o vinculada ao servi�o depende do servi�o. Alguns servi�os podem permitir que voc� edite as permiss�es para uma fun��o vinculada ao servi�o no console, API ou CLI do servi�o. Contudo, depois que voc� cria uma fun��o vinculada ao servi�o, voc� n�o pode mudar o nome da fun��o porque v�rias entidades podem fazer refer�ncia � fun��o. Voc� pode editar a descri��o de qualquer fun��o do console, API ou CLI do IAM.

Para obter informa��es sobre quais servi�os oferecem suporte a fun��es vinculadas a servi�os, consulte Servi�os da AWS compat�veis com o IAM e procure os servi�os que t�m Sim na coluna Fun��o vinculada ao servi�o. Para saber se o servi�o oferece suporte a edi��o da fun��o vinculada ao servi�o, escolha o link Sim para visualizar a documenta��o da fun��o vinculada desse servi�o.

## Editar a descri��o de uma fun��o vinculada ao servi�o (console)

Voc� pode usar o console do IAM para editar a descri��o de uma fun��o vinculada ao servi�o.

### Para editar a descri��o de uma fun��o vinculada ao servi�o (console)

1. No painel de navega��o do console do IAM, selecione Roles (Fun��es).

2. Escolha o nome da fun��o a ser modificada.

3. No extremo direito da Descri��o da fun��o, escolha Editar.

4. Digite uma nova descri��o na caixa e escolha Salvar.

## Editar a descri��o de uma fun��o vinculada ao servi�o (AWS CLI)

Voc� pode usar comandos do IAM na AWS CLI para editar a descri��o de uma fun��o vinculada ao servi�o.

### Para alterar a descri��o de uma fun��o vinculada ao servi�o (AWS CLI)

1. (Opcional) Para visualizar a descri��o atual de uma fun��o, execute os comandos a seguir:

...
aws iam get-role --role-name ROLE-NAME
...

Use o nome da fun��o, n�o o nome de recurso da Amazon (ARN), para fazer refer�ncia �s fun��es com os comandos da CLI. Por exemplo, se uma fun��o tiver o seguinte nome de recurso da Amazon (ARN): arn:aws:iam::123456789012:role/myrole, voc� far� refer�ncia � fun��o como myrole.

2. Para atualizar a descri��o de uma fun��o vinculada ao servi�o, execute um dos seguintes comandos:

...
aws iam update-role --role-name ROLE-NAME --description OPTIONAL-DESCRIPTION
...

## Editar a descri��o de uma fun��o vinculada ao servi�o (API da AWS)

Voc� pode usar a API do AWS para editar a descri��o de uma fun��o vinculada ao servi�o.

### Para alterar a descri��o de uma fun��o (AWS API)

1. (Opcional) Para visualizar a descri��o atual de a uma fun��o, chame a seguinte opera��o e especifique o nome da fun��o:

API do AWS: GetRole

2. Para atualizar a descri��o de uma fun��o, chame a seguinte opera��o e especifique o nome (e a descri��o opcional) da fun��o:

API da AWS: UpdateRole

## Excluir uma fun��o vinculada ao servi�o

O m�todo que voc� usa para criar uma fun��o vinculada ao servi�o depende do servi�o. Em alguns casos, voc� n�o precisa excluir manualmente uma fun��o vinculada ao servi�o. Por exemplo, quando voc� concluir uma a��o espec�fica (como a remo��o de um recurso) no servi�o, o servi�o pode excluir a fun��o vinculada ao servi�o para voc�.

Em outros casos, o servi�o pode oferecer suporte a exclus�o de uma fun��o vinculada ao servi�o manualmente usando o console, API ou CLI do servi�o.

Para obter informa��es sobre quais servi�os oferecem suporte a fun��es vinculadas a servi�os, consulte Servi�os da AWS compat�veis com o IAM e procure os servi�os que t�m Sim na coluna Fun��o vinculada ao servi�o. Para saber se o servi�o oferece suporte a exclus�o a fun��o vinculada ao servi�o, escolha o link Sim para visualizar a documenta��o da fun��o vinculada desse servi�o.

Se o servi�o n�o oferece suporte a exclus�o da fun��o, voc� pode excluir a fun��o vinculada ao servi�o do console, API ou CLI do IAM. Se voc� n�o precisar mais usar um recurso ou servi�o que requer uma fun��o vinculada a servi�o, � recomend�vel exclu�-la. Dessa forma, voc� n�o tem uma entidade n�o utilizada que n�o seja monitorada ativamente ou mantida. No entanto, voc� deve limpar sua fun��o vinculada ao servi�o antes de exclu�-la.

## Limpar uma fun��o vinculada ao servi�o

Antes de voc� poder usar o IAM para excluir uma fun��o vinculada ao servi�o, voc� deve primeiro confirmar que a fun��o n�o tem sess�es ativas e remover quaisquer recursos usados pela fun��o.

### Para verificar se a fun��o vinculada ao servi�o tem uma sess�o ativa no console do IAM

1. Fa�a login no Console de gerenciamento da AWS e abra o console da IAM em https://console.aws.amazon.com/iam/.

2. No painel de navega��o do console do IAM, selecione Roles (Fun��es). Ent�o, escolha o nome (n�o a caixa de marca��o) da fun��o vinculada ao servi�o.

3. Na p�gina Resumo para a fun��o selecionada, escolha a guia Consultor de Acesso.

4. Na guia Consultor de Acesso, revise a atividade recente para a fun��o vinculada ao servi�o.

...
nota
Se voc� n�o tem certeza se o servi�o est� usando a fun��o vinculada ao servi�o, pode tentar excluir a fun��o. Se o servi�o est� usando a fun��o, a exclus�o falha e voc� pode visualizar as regi�es em que a fun��o est� sendo usada. Se a fun��o est� sendo usada, voc� deve aguardar a sess�o final antes de excluir a fun��o. Voc� n�o pode revogar a sess�o para uma fun��o vinculada a servi�o.
...

Para remover os recursos usados por uma fun��o vinculada ao servi�o

Para obter informa��es sobre quais servi�os oferecem suporte a fun��es vinculadas a servi�os, consulte Servi�os da AWS compat�veis com o IAM e procure os servi�os que t�m Sim na coluna Fun��o vinculada ao servi�o. Para saber se o servi�o oferece suporte a exclus�o a fun��o vinculada ao servi�o, escolha o link Sim para visualizar a documenta��o da fun��o vinculada desse servi�o. Consulte a documenta��o daquele servi�o para saber como remover usado pela sua fun��o vinculada a esse servi�o.

## Excluir uma fun��o vinculada ao servi�o (console)

Tamb�m � poss�vel usar o console do IAM para excluir uma fun��o vinculada ao servi�o.

### Para excluir uma fun��o vinculada ao servi�o (console)

1. Fa�a login no Console de gerenciamento da AWS e abra o console da IAM em https://console.aws.amazon.com/iam/.

2. No painel de navega��o do console do IAM, selecione Roles (Fun��es). Selecione a caixa de marca��o ao lado do nome da fun��o que voc� deseja excluir, n�o o nome ou a linha em si.

3. Em A��es da fun��o na parte superior da p�gina, escolha Excluir fun��o.

4. Na caixa de di�logo de confirma��o, revise as informa��es acessadas por �ltimo, que mostram quando cada uma das fun��es selecionadas acessou pela �ltima vez um servi�o da AWS. Isso ajuda voc� a confirmar se a fun��o est� ativo no momento. Se voc� deseja continuar, escolha Sim, excluir para enviar a fun��o vinculada ao servi�o para exclus�o.

5. Acompanhe as notifica��es do console do IAM para monitorar o progresso da exclus�o da fun��o vinculada ao servi�o. Como a exclus�o da fun��o vinculada ao servi�o do IAM � ass�ncrona, depois de enviar a fun��o para exclus�o, a tarefa de exclus�o pode ou n�o ser bem-sucedida.

- Se a tarefa for bem-sucedida, a fun��o ser� removida da lista e uma notifica��o de sucesso ser� exibida na parte superior da p�gina.

- Se a tarefa falhar, voc� pode escolher Visualizar detalhes ou Exibir recursos a partir das notifica��es para saber por que a exclus�o falhou. Se a exclus�o falhar porque a fun��o est� usando os recursos do servi�o, a notifica��o inclui uma lista de recursos, se o servi�o retorna essas informa��es. Voc� pode ent�o limpar os recursos e submeter novamente a exclus�o.

...
nota
Voc� pode repetir esse processo v�rias vezes, de acordo com as informa��es que o servi�o retorna. Por exemplo, a fun��o vinculada ao servi�o pode usar seis recursos e seu servi�o pode retornar informa��es sobre cinco deles. Se voc� limpar cinco recursos e enviar a fun��o para exclus�o novamente, a dele��o falha e o servi�o reporta o recurso remanescente. Um servi�o pode retornar todos os recursos, alguns deles, ou pode n�o reportar nenhum recurso.
...

- Se a tarefa falhar e a notifica��o n�o inclui uma lista de recursos, o servi�o pode n�o retornar essas informa��es. Para saber como limpar os recursos para esse servi�o, consulte Servi�os da AWS compat�veis com o IAM. Encontre o servi�o na tabela e escolha o link Sim para visualizar a documenta��o da fun��o vinculada desse servi�o.

## Excluir uma fun��o vinculada ao servi�o (AWS CLI)

Voc� pode usar comandos do IAM na AWS CLI para excluir uma fun��o vinculada ao servi�o.

### Para excluir uma fun��o vinculada ao servi�o (AWS CLI)

1. Se voc� n�o souber o nome da fun��o vinculada ao servi�o que deseja excluir, digite o seguinte comando para listar as fun��es e seus Nomes de recurso da Amazon em sua conta:

...
aws iam get-role --role-name role-name
...

Use o nome da fun��o, n�o o nome de recurso da Amazon (ARN), para fazer refer�ncia �s fun��es com os comandos da CLI. Por exemplo, se uma fun��o tiver o seguinte nome de recurso da Amazon (ARN): arn:aws:iam::123456789012:role/myrole, voc� far� refer�ncia � fun��o como myrole.

2. Como uma fun��o vinculada ao servi�o n�o podem ser exclu�da se estiver sendo usada ou tem recursos associados, voc� deve enviar uma solicita��o de exclus�o. Essa solicita��o pode ser negada se essas condi��es n�o forem atendidas. Voc� deve capturar o deletion-task-id da resposta para verificar o estado da tarefa de exclus�o. Digite o seguinte comando para enviar uma solicita��o de exclus�o de fun��o vinculada ao servi�o:

...
aws iam delete-service-linked-role --role-name role-name
... 

3. Digite o seguinte comando para verificar o estado da tarefa de exclus�o:

...
aws iam get-service-linked-role-deletion-status --deletion-task-id deletion-task-id
...

O status da tarefa de exclus�o pode ser NOT_STARTED, IN_PROGRESS, SUCCEEDED, ou FAILED. Se a exclus�o falhar, a chamada retorna o motivo de falha para que voc� possa solucionar problemas. Se a exclus�o falhar porque a fun��o est� usando os recursos do servi�o, a notifica��o inclui uma lista de recursos, se o servi�o retorna essas informa��es. Voc� pode ent�o limpar os recursos e submeter novamente a exclus�o.

...
nota
Voc� pode repetir esse processo v�rias vezes, de acordo com as informa��es que o servi�o retorna. Por exemplo, a fun��o vinculada ao servi�o pode usar seis recursos e seu servi�o pode retornar informa��es sobre cinco deles. Se voc� limpar cinco recursos e enviar a fun��o para exclus�o novamente, a dele��o falha e o servi�o reporta o recurso remanescente. Um servi�o pode retornar todos os recursos, alguns deles, ou pode n�o reportar nenhum recurso. Para saber como limpar os recursos para um servi�o que n�o reporta nenhum recurso, consulte Servi�os da AWS compat�veis com o IAM. Encontre o servi�o na tabela e escolha o link Sim para visualizar a documenta��o da fun��o vinculada desse servi�o.
...

### Excluir uma fun��o vinculada ao servi�o (API da AWS)

� poss�vel usar a API do AWS para excluir uma fun��o vinculada ao servi�o.

#### Para excluir uma fun��o vinculada ao servi�o (AWS API)

1. Para enviar uma solicita��o de exclus�o de uma fun��o vinculada ao servi�o, chame DeleteServiceLinkedRole. Na solicita��o, especifique o nome da fun��o.

Como uma fun��o vinculada ao servi�o n�o podem ser exclu�da se estiver sendo usada ou tem recursos associados, voc� deve enviar uma solicita��o de exclus�o. Essa solicita��o pode ser negada se essas condi��es n�o forem atendidas. Voc� deve capturar o DeletionTaskId da resposta para verificar o estado da tarefa de exclus�o.

2. Para verificar o status da exclus�o, chame GetServiceLinkedRoleDeletionStatus. Na solicita��o, especifique o DeletionTaskId.

O status da tarefa de exclus�o pode ser NOT_STARTED, IN_PROGRESS, SUCCEEDED, ou FAILED. Se a exclus�o falhar, a chamada retorna o motivo de falha para que voc� possa solucionar problemas. Se a exclus�o falhar porque a fun��o est� usando os recursos do servi�o, a notifica��o inclui uma lista de recursos, se o servi�o retorna essas informa��es. Voc� pode ent�o limpar os recursos e submeter novamente a exclus�o.

...
nota
Voc� pode repetir esse processo v�rias vezes, de acordo com as informa��es que o servi�o retorna. Por exemplo, a fun��o vinculada ao servi�o pode usar seis recursos e seu servi�o pode retornar informa��es sobre cinco deles. Se voc� limpar cinco recursos e enviar a fun��o para exclus�o novamente, a dele��o falha e o servi�o reporta o recurso remanescente. Um servi�o pode retornar todos os recursos, alguns deles, ou pode n�o reportar nenhum recurso. Para saber como limpar os recursos para um servi�o que n�o reporta nenhum recurso, consulte Servi�os da AWS compat�veis com o IAM. Encontre o servi�o na tabela e escolha o link Sim para visualizar a documenta��o da fun��o vinculada desse servi�o.
...

[Artigo original](https://docs.aws.amazon.com/pt_br/IAM/latest/UserGuide/using-service-linked-roles.html)