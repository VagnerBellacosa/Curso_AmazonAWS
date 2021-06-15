# Uso de funções vinculadas a serviço

Uma função vinculada ao serviço é um tipo exclusivo de função do IAM que é vinculada diretamente a um serviço da AWS. As funções vinculadas a serviços são predefinidas pelo serviço e incluem todas as permissões que o serviço requer para chamar outros serviços da AWS em seu nome. O serviço vinculado também define como criar, modificar e excluir uma função vinculada a serviço. Um serviço pode criar ou excluir a função automaticamente. Ele pode permitir que você crie, modifique ou exclua a função como parte de um assistente ou processo no serviço. Ou pode exigir que você use o IAM para criar ou excluir a função. Independente do método, as funções vinculadas a serviço facilitam a configuração de um serviço, pois você não precisa adicionar manualmente as permissões necessárias para o serviço concluir as ações em seu nome.

O serviço vinculado define as permissões de suas funções vinculadas ao serviço e, a menos que definido em contrário, somente aquele serviço pode assumir as funções. As permissões definidas incluem a política de confiança e a política de permissões, e essa política não pode ser anexada a nenhuma outra entidade do IAM.

Você pode excluir as funções somente depois de primeiro excluir seus recursos relacionados. Isso protege seus recursos, pois você não pode remover inadvertidamente a permissão para acessar os recursos.

...
dica
Para obter informações sobre quais serviços oferecem suporte a funções vinculadas a serviços, consulte Serviços da AWS compatíveis com o IAM e procure os serviços que têm Sim na coluna Função vinculada ao serviço. Escolha um Sim com um link para exibir a documentação da função vinculada a serviço desse serviço.
..

## Permissões de função vinculada ao serviço

Configure as permissões para que uma entidade do IAM (usuário ou função) permita que o usuário ou função crie, edite ou exclua a função vinculada ao serviço.

...
nota
O ARN de uma função vinculada ao serviço inclui uma entidade principal do serviço que é indicada nas políticas abaixo como SERVICE-NAME.amazonaws.com. Não tente adivinhar a entidade principal do serviço, pois ela faz distinção entre maiúsculas e minúsculas, e o formato pode variar entre os serviços da AWS. Para visualizar a entidade principal do serviço, consulte a documentação da função vinculada ao serviço.
...

### Para permitir que uma entidade do IAM crie uma função vinculada ao serviço

Adicione a seguinte política à entidade do IAM que precisa criar a função vinculada ao serviço.

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

### Para permitir que uma entidade do IAM crie qualquer função vinculada ao serviço

Adicione a seguinte instrução à política de permissões da entidade do IAM que precisa criar uma função vinculada ao serviço ou qualquer função de serviço que inclua as políticas necessárias. Esta declaração de política não permite que a entidade do IAM anexe uma política à função.

...
{
    "Effect": "Allow",
    "Action": "iam:CreateServiceLinkedRole",
    "Resource": "arn:aws:iam::*:role/aws-service-role/*"
}
...

### Para permitir que uma entidade do IAM edite a descrição de todas as funções vinculadas ao serviço

Adicione a seguinte instrução à política de permissões da entidade do IAM que precisa editar uma descrição de uma função vinculada ao serviço ou qualquer função de serviço.

...
{
    "Effect": "Allow",
    "Action": "iam:UpdateRoleDescription",
    "Resource": "arn:aws:iam::*:role/aws-service-role/*"
}
...

### Para permitir que uma entidade do IAM exclua uma função vinculada ao serviço específica

Adicione a seguinte instrução à política de permissões para a entidade do IAM que precisa excluir a função vinculada ao serviço.

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

### Para permitir que uma entidade do IAM exclua qualquer função vinculada ao serviço

Adicione a seguinte instrução à política de permissões da entidade do IAM que precisa excluir uma função vinculada ao serviço, mas não a função de serviço.

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

### Para permitir que uma entidade do IAM transmita uma função existente para o serviço

Alguns serviços do AWS permitem que você transmita uma função existente para o serviço, ao invés de criar uma nova função vinculada ao serviço. Para fazer isso, um usuário deve ter permissões para transmitir a função para o serviço. Adicione a seguinte instrução à política de permissões para a entidade do IAM necessária para transmitir a função. Esta declaração de política também permite que a entidade visualize uma lista de funções a partir da qual elas podem escolher a função a ser transmitida. Para obter mais informações, consulte Conceder permissões a um usuário para passar uma função para um serviço da AWS.

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

### Transferência de permissões de função vinculada ao serviço

As permissões concedidas por uma função vinculada ao serviço são indiretamente transferíveis para outros usuários e funções. Quando você permitir que um serviço execute operações em outros serviços, o serviço de origem poderá usar essas permissões no futuro. Se outro usuário ou função tiver permissão para executar ações no serviço de origem, ele poderá assumir a função e acessar recursos em outros serviços. Isso significa que o outro usuário ou função pode acessar indiretamente os outros serviços.

Por exemplo, quando você cria uma instância de banco de dados do Amazon RDS, o RDS cria a função vinculada ao serviço para você. Essa função permite que o RDS chame o Amazon EC2, o Amazon SNS, o Amazon CloudWatch Logs, e o Amazon Kinesis em seu nome sempre que você editar a instância de banco de dados. Se você criar uma política para permitir que usuários e funções em sua conta ou em outra conta acessem essa instância do Amazon RDS, o RDS ainda poderá usar essa função para fazer alterações no EC2, no SNS, no CloudWatch Logs e no Kinesis em nome deles. O novo usuário ou função pode editar indiretamente recursos nesses outros serviços.

### Criar uma função vinculada ao serviço

O método que você usa para criar uma função vinculada ao serviço depende do serviço. Em alguns casos, você não precisa criar manualmente uma função vinculada ao serviço. Por exemplo, quando você conclui uma ação específica (como a criação de um recurso) no serviço, o serviço pode criar a função vinculada ao serviço para você. Ou, se você estava usando um serviço antes que ele começou a oferecer suporte a funções vinculadas ao serviço, o serviço pode ter criado automaticamente a função na sua conta. Para saber mais, consulte Uma nova função apareceu na minha conta da AWS.

Em outros casos, o serviço pode oferecer suporte à criação de uma função vinculada ao serviço manualmente usando o console, API ou CLI do serviço. Para obter informações sobre quais serviços oferecem suporte a funções vinculadas a serviços, consulte Serviços da AWS compatíveis com o IAM e procure os serviços que têm Sim na coluna Função vinculada ao serviço. Para saber se o serviço oferece suporte para criar a função vinculada ao serviço, escolha o link Sim para visualizar a documentação da função vinculada a esse serviço.

Se o serviço não permitir a criação da função, você pode usar o IAM para criar a função vinculada ao serviço.

...
#### Importante

As funções vinculadas a serviços contam para o limite das funções do IAM em uma conta da AWS, mas, se tiver atingido o limite, você ainda poderá criar esse tipo de função na conta. Somente as funções vinculadas ao serviço podem exceder o limite.
...

### Criar uma função vinculada ao serviço (console)

Antes de criar uma função vinculada ao serviço no IAM, descubra se o serviço vinculado cria automaticamente as funções vinculadas ao serviço. Além disso, aprenda se você pode criar a função do console, API ou CLI do serviço.

### Para criar uma função vinculada ao serviço (console)

1. Faça login no Console de gerenciamento da AWS e abra o console da IAM em https://console.aws.amazon.com/iam/.

2. No painel de navegação do console do IAM, selecione Roles (Funções). Então, escolha Criar função.

3. Escolha o tipo de função AWS Service (Serviço da AWS) e escolha o serviço que terá permissão para assumir essa função.

4. Escolha o caso de uso para o seu serviço. Se o serviço especificado tem apenas um caso de uso, ele é selecionado para você. Casos de uso são definidos pelo serviço para incluir a política de confiança exigida pelo serviço. Então, escolha Próximo: Permissões.

5. Escolha uma ou mais políticas de permissões a serem anexadas à função. Dependendo do caso de uso que você selecionou, o serviço pode executar uma das seguintes ações:

	- Definir as permissões usadas pela função

	- Permitir que você escolha um conjunto limitado de permissões

	- Permitir que você escolha a partir de quaisquer permissões

	- Permitir que você opte por não selecionar nenhuma política no momento, criar políticas mais tarde e, em seguida, anexá-las à função.

Marque a caixa ao lado da política que atribui as permissões que você deseja que a função tenha e escolha Next: Tags (Próximo: tags).

...
nota
As permissões que você especificar estarão disponíveis para qualquer entidade que usar a função. Por padrão, uma função não tem nenhuma permissões.
...

6. Selecione Next: Review. Você não pode anexar tags para funções vinculadas ao serviço durante a criação. Para obter mais informações sobre como usar tags no IAM, consulte Marcar recursos do IAM.

7. Para Nome da função, o grau de personalização do nome da função é definido pelo serviço. Se o serviço define o nome da função, essa opção não é editável. Em outros casos, o serviço pode definir um prefixo para a função e permitir que você digite um sufixo opcional.

 Se possível, insira um sufixo do nome da função a ser adicionado ao nome padrão. O sufixo ajuda a identificar a finalidade dessa função. Os nomes de função devem ser exclusivos em sua conta da AWS. Eles não são diferenciados por letras maiúsculas e minúsculas. Por exemplo, não é possível criar funções chamadas <service-linked-role-name>_SAMPLE e <service-linked-role-name>_sample. Como várias entidades podem fazer referência à função, não é possível editar o nome da função depois que ela é criada.

8. Opcional) Em Descrição da função, edite a descrição para a nova função vinculada ao serviço.

9. Revise a função e escolha Create role.

## Criar uma função vinculada ao serviço (AWS CLI)

Antes de criar uma função vinculada ao serviço no IAM, descubra se o serviço vinculado cria automaticamente as funções vinculadas ao serviço e se você pode criar a função na CLI do serviço. Se a CLI do serviço não for suportada, você pode usar comandos do IAM para criar uma função vinculada ao serviço com a política de confiança e políticas em linha de que o serviço precisa assumir a função.

### Para criar uma função vinculada ao serviço (AWS CLI)

Execute o seguinte comando:

...
aws iam create-service-linked-role --aws-service-name SERVICE-NAME.amazonaws.com
...

## Criar uma função vinculada ao serviço (API da AWS)

Antes de criar uma função vinculada ao serviço no IAM, descubra se o serviço vinculado cria automaticamente as funções vinculadas ao serviço e se você pode criar a função na API do serviço. Se a API do serviço não for suportada, você pode usar a API do AWS para criar uma função vinculada ao serviço com a política de confiança e políticas em linha de que o serviço precisa para assumir a função.

### Para criar uma função vinculada ao serviço (API da AWS)

Use a chamada de API CreateServiceLinkedRole. Na solicitação, especifique o nome do serviço na forma de SERVICE_NAME_URL.amazonaws.com.

Por exemplo, para criar a função vinculada ao serviço Lex Bots, use lex.amazonaws.com.

## Editar uma função vinculada ao serviço

O método que você usa para editar uma função vinculada ao serviço depende do serviço. Alguns serviços podem permitir que você edite as permissões para uma função vinculada ao serviço no console, API ou CLI do serviço. Contudo, depois que você cria uma função vinculada ao serviço, você não pode mudar o nome da função porque várias entidades podem fazer referência à função. Você pode editar a descrição de qualquer função do console, API ou CLI do IAM.

Para obter informações sobre quais serviços oferecem suporte a funções vinculadas a serviços, consulte Serviços da AWS compatíveis com o IAM e procure os serviços que têm Sim na coluna Função vinculada ao serviço. Para saber se o serviço oferece suporte a edição da função vinculada ao serviço, escolha o link Sim para visualizar a documentação da função vinculada desse serviço.

## Editar a descrição de uma função vinculada ao serviço (console)

Você pode usar o console do IAM para editar a descrição de uma função vinculada ao serviço.

### Para editar a descrição de uma função vinculada ao serviço (console)

1. No painel de navegação do console do IAM, selecione Roles (Funções).

2. Escolha o nome da função a ser modificada.

3. No extremo direito da Descrição da função, escolha Editar.

4. Digite uma nova descrição na caixa e escolha Salvar.

## Editar a descrição de uma função vinculada ao serviço (AWS CLI)

Você pode usar comandos do IAM na AWS CLI para editar a descrição de uma função vinculada ao serviço.

### Para alterar a descrição de uma função vinculada ao serviço (AWS CLI)

1. (Opcional) Para visualizar a descrição atual de uma função, execute os comandos a seguir:

...
aws iam get-role --role-name ROLE-NAME
...

Use o nome da função, não o nome de recurso da Amazon (ARN), para fazer referência às funções com os comandos da CLI. Por exemplo, se uma função tiver o seguinte nome de recurso da Amazon (ARN): arn:aws:iam::123456789012:role/myrole, você fará referência à função como myrole.

2. Para atualizar a descrição de uma função vinculada ao serviço, execute um dos seguintes comandos:

...
aws iam update-role --role-name ROLE-NAME --description OPTIONAL-DESCRIPTION
...

## Editar a descrição de uma função vinculada ao serviço (API da AWS)

Você pode usar a API do AWS para editar a descrição de uma função vinculada ao serviço.

### Para alterar a descrição de uma função (AWS API)

1. (Opcional) Para visualizar a descrição atual de a uma função, chame a seguinte operação e especifique o nome da função:

API do AWS: GetRole

2. Para atualizar a descrição de uma função, chame a seguinte operação e especifique o nome (e a descrição opcional) da função:

API da AWS: UpdateRole

## Excluir uma função vinculada ao serviço

O método que você usa para criar uma função vinculada ao serviço depende do serviço. Em alguns casos, você não precisa excluir manualmente uma função vinculada ao serviço. Por exemplo, quando você concluir uma ação específica (como a remoção de um recurso) no serviço, o serviço pode excluir a função vinculada ao serviço para você.

Em outros casos, o serviço pode oferecer suporte a exclusão de uma função vinculada ao serviço manualmente usando o console, API ou CLI do serviço.

Para obter informações sobre quais serviços oferecem suporte a funções vinculadas a serviços, consulte Serviços da AWS compatíveis com o IAM e procure os serviços que têm Sim na coluna Função vinculada ao serviço. Para saber se o serviço oferece suporte a exclusão a função vinculada ao serviço, escolha o link Sim para visualizar a documentação da função vinculada desse serviço.

Se o serviço não oferece suporte a exclusão da função, você pode excluir a função vinculada ao serviço do console, API ou CLI do IAM. Se você não precisar mais usar um recurso ou serviço que requer uma função vinculada a serviço, é recomendável excluí-la. Dessa forma, você não tem uma entidade não utilizada que não seja monitorada ativamente ou mantida. No entanto, você deve limpar sua função vinculada ao serviço antes de excluí-la.

## Limpar uma função vinculada ao serviço

Antes de você poder usar o IAM para excluir uma função vinculada ao serviço, você deve primeiro confirmar que a função não tem sessões ativas e remover quaisquer recursos usados pela função.

### Para verificar se a função vinculada ao serviço tem uma sessão ativa no console do IAM

1. Faça login no Console de gerenciamento da AWS e abra o console da IAM em https://console.aws.amazon.com/iam/.

2. No painel de navegação do console do IAM, selecione Roles (Funções). Então, escolha o nome (não a caixa de marcação) da função vinculada ao serviço.

3. Na página Resumo para a função selecionada, escolha a guia Consultor de Acesso.

4. Na guia Consultor de Acesso, revise a atividade recente para a função vinculada ao serviço.

...
nota
Se você não tem certeza se o serviço está usando a função vinculada ao serviço, pode tentar excluir a função. Se o serviço está usando a função, a exclusão falha e você pode visualizar as regiões em que a função está sendo usada. Se a função está sendo usada, você deve aguardar a sessão final antes de excluir a função. Você não pode revogar a sessão para uma função vinculada a serviço.
...

Para remover os recursos usados por uma função vinculada ao serviço

Para obter informações sobre quais serviços oferecem suporte a funções vinculadas a serviços, consulte Serviços da AWS compatíveis com o IAM e procure os serviços que têm Sim na coluna Função vinculada ao serviço. Para saber se o serviço oferece suporte a exclusão a função vinculada ao serviço, escolha o link Sim para visualizar a documentação da função vinculada desse serviço. Consulte a documentação daquele serviço para saber como remover usado pela sua função vinculada a esse serviço.

## Excluir uma função vinculada ao serviço (console)

Também é possível usar o console do IAM para excluir uma função vinculada ao serviço.

### Para excluir uma função vinculada ao serviço (console)

1. Faça login no Console de gerenciamento da AWS e abra o console da IAM em https://console.aws.amazon.com/iam/.

2. No painel de navegação do console do IAM, selecione Roles (Funções). Selecione a caixa de marcação ao lado do nome da função que você deseja excluir, não o nome ou a linha em si.

3. Em Ações da função na parte superior da página, escolha Excluir função.

4. Na caixa de diálogo de confirmação, revise as informações acessadas por último, que mostram quando cada uma das funções selecionadas acessou pela última vez um serviço da AWS. Isso ajuda você a confirmar se a função está ativo no momento. Se você deseja continuar, escolha Sim, excluir para enviar a função vinculada ao serviço para exclusão.

5. Acompanhe as notificações do console do IAM para monitorar o progresso da exclusão da função vinculada ao serviço. Como a exclusão da função vinculada ao serviço do IAM é assíncrona, depois de enviar a função para exclusão, a tarefa de exclusão pode ou não ser bem-sucedida.

- Se a tarefa for bem-sucedida, a função será removida da lista e uma notificação de sucesso será exibida na parte superior da página.

- Se a tarefa falhar, você pode escolher Visualizar detalhes ou Exibir recursos a partir das notificações para saber por que a exclusão falhou. Se a exclusão falhar porque a função está usando os recursos do serviço, a notificação inclui uma lista de recursos, se o serviço retorna essas informações. Você pode então limpar os recursos e submeter novamente a exclusão.

...
nota
Você pode repetir esse processo várias vezes, de acordo com as informações que o serviço retorna. Por exemplo, a função vinculada ao serviço pode usar seis recursos e seu serviço pode retornar informações sobre cinco deles. Se você limpar cinco recursos e enviar a função para exclusão novamente, a deleção falha e o serviço reporta o recurso remanescente. Um serviço pode retornar todos os recursos, alguns deles, ou pode não reportar nenhum recurso.
...

- Se a tarefa falhar e a notificação não inclui uma lista de recursos, o serviço pode não retornar essas informações. Para saber como limpar os recursos para esse serviço, consulte Serviços da AWS compatíveis com o IAM. Encontre o serviço na tabela e escolha o link Sim para visualizar a documentação da função vinculada desse serviço.

## Excluir uma função vinculada ao serviço (AWS CLI)

Você pode usar comandos do IAM na AWS CLI para excluir uma função vinculada ao serviço.

### Para excluir uma função vinculada ao serviço (AWS CLI)

1. Se você não souber o nome da função vinculada ao serviço que deseja excluir, digite o seguinte comando para listar as funções e seus Nomes de recurso da Amazon em sua conta:

...
aws iam get-role --role-name role-name
...

Use o nome da função, não o nome de recurso da Amazon (ARN), para fazer referência às funções com os comandos da CLI. Por exemplo, se uma função tiver o seguinte nome de recurso da Amazon (ARN): arn:aws:iam::123456789012:role/myrole, você fará referência à função como myrole.

2. Como uma função vinculada ao serviço não podem ser excluída se estiver sendo usada ou tem recursos associados, você deve enviar uma solicitação de exclusão. Essa solicitação pode ser negada se essas condições não forem atendidas. Você deve capturar o deletion-task-id da resposta para verificar o estado da tarefa de exclusão. Digite o seguinte comando para enviar uma solicitação de exclusão de função vinculada ao serviço:

...
aws iam delete-service-linked-role --role-name role-name
... 

3. Digite o seguinte comando para verificar o estado da tarefa de exclusão:

...
aws iam get-service-linked-role-deletion-status --deletion-task-id deletion-task-id
...

O status da tarefa de exclusão pode ser NOT_STARTED, IN_PROGRESS, SUCCEEDED, ou FAILED. Se a exclusão falhar, a chamada retorna o motivo de falha para que você possa solucionar problemas. Se a exclusão falhar porque a função está usando os recursos do serviço, a notificação inclui uma lista de recursos, se o serviço retorna essas informações. Você pode então limpar os recursos e submeter novamente a exclusão.

...
nota
Você pode repetir esse processo várias vezes, de acordo com as informações que o serviço retorna. Por exemplo, a função vinculada ao serviço pode usar seis recursos e seu serviço pode retornar informações sobre cinco deles. Se você limpar cinco recursos e enviar a função para exclusão novamente, a deleção falha e o serviço reporta o recurso remanescente. Um serviço pode retornar todos os recursos, alguns deles, ou pode não reportar nenhum recurso. Para saber como limpar os recursos para um serviço que não reporta nenhum recurso, consulte Serviços da AWS compatíveis com o IAM. Encontre o serviço na tabela e escolha o link Sim para visualizar a documentação da função vinculada desse serviço.
...

### Excluir uma função vinculada ao serviço (API da AWS)

É possível usar a API do AWS para excluir uma função vinculada ao serviço.

#### Para excluir uma função vinculada ao serviço (AWS API)

1. Para enviar uma solicitação de exclusão de uma função vinculada ao serviço, chame DeleteServiceLinkedRole. Na solicitação, especifique o nome da função.

Como uma função vinculada ao serviço não podem ser excluída se estiver sendo usada ou tem recursos associados, você deve enviar uma solicitação de exclusão. Essa solicitação pode ser negada se essas condições não forem atendidas. Você deve capturar o DeletionTaskId da resposta para verificar o estado da tarefa de exclusão.

2. Para verificar o status da exclusão, chame GetServiceLinkedRoleDeletionStatus. Na solicitação, especifique o DeletionTaskId.

O status da tarefa de exclusão pode ser NOT_STARTED, IN_PROGRESS, SUCCEEDED, ou FAILED. Se a exclusão falhar, a chamada retorna o motivo de falha para que você possa solucionar problemas. Se a exclusão falhar porque a função está usando os recursos do serviço, a notificação inclui uma lista de recursos, se o serviço retorna essas informações. Você pode então limpar os recursos e submeter novamente a exclusão.

...
nota
Você pode repetir esse processo várias vezes, de acordo com as informações que o serviço retorna. Por exemplo, a função vinculada ao serviço pode usar seis recursos e seu serviço pode retornar informações sobre cinco deles. Se você limpar cinco recursos e enviar a função para exclusão novamente, a deleção falha e o serviço reporta o recurso remanescente. Um serviço pode retornar todos os recursos, alguns deles, ou pode não reportar nenhum recurso. Para saber como limpar os recursos para um serviço que não reporta nenhum recurso, consulte Serviços da AWS compatíveis com o IAM. Encontre o serviço na tabela e escolha o link Sim para visualizar a documentação da função vinculada desse serviço.
...

[Artigo original](https://docs.aws.amazon.com/pt_br/IAM/latest/UserGuide/using-service-linked-roles.html)