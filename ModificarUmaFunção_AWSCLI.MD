# Modificar uma função (AWS CLI)

Você pode usar a AWS Command Line Interface para modificar uma função. Para alterar o conjunto de tags em uma função, consulte .

### Tópicos

- Modificar uma política de confiança de função (AWS CLI)
- Modificar uma política de permissões de função (AWS CLI)
- Modificar a descrição de uma função (AWS CLI)
- Modificar a duração máxima da sessão de uma função (AWS CLI)
- Modificar o limite de permissões de uma função (AWS CLI)

##Modificar uma política de confiança de função (AWS CLI)

Para alterar quem pode assumir uma função, você deve modificar a política de confiança da função. Você não pode modificar a política de confiança para uma *função vinculada a serviço*.

...
nota
Se um usuário for listado como principal em uma política de confiança da função, mas não puder assumir a função, verifique o limite de permissões do usuário. Se um limite de permissões for definido para o usuário, ele deverá permitir a ação sts:AssumeRole.
...

## Como modificar uma política de confiança da função (AWS CLI)

1. (Opcional) Se você não souber o nome da função que deseja modificar, execute o seguinte comando para listar as funções em sua conta:

aws iam list-roles

2. (Opcional) Para visualizar a política de confiança atual de uma função, execute o seguinte comando:

aws iam get-role

3. Para modificar as entidades principais confiáveis que podem acessar a função, crie um arquivo de texto com a política de confiança atualizada. É possível usar qualquer editor de texto para construir a política.

Por exemplo, a seguinte política de confiança mostra como fazer referência a duas contas da AWS no elemento Principal. Isso permite que os usuários de duas contas da AWS separadas assumam essa função.

...
{
    "Version": "2012-10-17",
    "Statement": {
        "Effect": "Allow",
        "Principal": {"AWS": [
            "arn:aws:iam::111122223333:root",
            "arn:aws:iam::444455556666:root"
        ]},
        "Action": "sts:AssumeRole"
    }
}
...

Se você especificar um principal em outra conta, adicionar uma conta à política de confiança de uma função é apenas metade da tarefa de estabelecer o relacionamento de confiança entre contas. Por padrão, nenhum usuário nas contas confiáveis pode assumir a função. O administrador da conta confiável recém-criada deve conceder aos usuários a permissão para assumir a função. Para fazer isso, o administrador deve criar ou editar uma política que está anexada ao usuário para permitir acesso ao usuário à ação sts:AssumeRole. Para obter mais informações, consulte o procedimento a seguir ou Conceder permissões a um usuário para alternar funções.

4. Para usar o arquivo que você acabou de criar para atualizar a política de confiança, execute o seguinte comando:

aws iam update-assume-role-policy


## Para permitir que os usuários em uma conta externa confiável usem a função (AWS CLI)

Para obter mais informações e detalhes sobre esse procedimento, consulte Conceder permissões a um usuário para alternar funções.

1. Crie um arquivo JSON que contenha uma política de permissões que concede permissões para assumir a função. Por exemplo, a seguinte política contém as permissões necessárias mínimas:

...
{
  "Version": "2012-10-17",
  "Statement": {
    "Effect": "Allow",
    "Action": "sts:AssumeRole",
    "Resource": "arn:aws:iam::ACCOUNT-ID-THAT-CONTAINS-ROLE:role/ROLE-NAME"
  }
}
...

Substitua o ARN na instrução pelo ARN da função que o usuário pode assumir.

2. Execute o comando a seguir para fazer upload do arquivo JSON que contém a política de confiança para o IAM:

*aws iam create-policy*

O resultado desse comando inclui o ARN da política. Anote esse ARN, pois você precisará dele em uma etapa posterior.

3. Decida qual usuário ou grupo ao qual anexar a política. Se você não souber o nome do usuário ou do grupo pretendido, use um dos seguintes comandos para listar os usuários ou os grupos em sua conta:

*aws iam list-users*

*aws iam list-groups*

4. Use um dos seguintes comandos para anexar a política criada na etapa anterior ao usuário ou ao grupo:

*aws iam attach-user-policy*

*aws iam attach-group-policy*

## Modificar uma política de permissões de função (AWS CLI)

### Para alterar as permissões permitidas pela função, modifique a política de permissões da função (ou políticas). Você não pode modificar a política de permissões para uma função vinculada a serviço no IAM. Pode ser possível modificar a política de permissões no serviço que depende da função. Para verificar se um serviço oferece suporte a este recurso, consulte Serviços da AWS compatíveis com o IAM e procure os serviços que têm Sim na coluna Funções vinculadas ao serviço. Escolha um Sim com um link para exibir a documentação da função vinculada a serviço desse serviço.

Para alterar as permissões permitidas por uma função (AWS CLI)

1. (Opcional) Para visualizar as permissões atuais associadas a uma função, execute um dos comandos a seguir:

*aws iam list-role-policies* para listar as políticas em linha

*aws iam list-attached-role-policies* para listar as políticas gerenciadas

2. O comando para atualizar as permissões para a função será diferente se você estiver atualizando uma política gerenciada ou uma política em linha.

Para atualizar uma política gerenciada, execute o seguinte comando para criar uma nova versão da política gerenciada:

*aws iam create-policy-version*

Para atualizar uma política em linha, execute o seguinte comando:

*aws iam put-role-policy*

## Modificar a descrição de uma função (AWS CLI)

Para alterar a descrição da função, modifique o texto da descrição.

Para alterar a descrição de uma função (AWS CLI)

1. (Opcional) Para visualizar a descrição atual de uma função, execute o comando a seguir:

*aws iam get-role*

Para atualizar a descrição de uma função, execute o seguinte comando com o parâmetro de descrição:

*aws iam update-role*

## Modificar a duração máxima da sessão de uma função (AWS CLI)

Para especificar a configuração de duração máxima da sessão para funções que são assumidas usando a AWS CLI ou a API, modifique o valor da configuração da duração máxima da sessão. Essa configuração pode ter um valor de 1 hora a 12 horas. Se você não especificar um valor, o padrão máximo de 1 hora será aplicado. Essa configuração não limita sessões assumidas por serviços da AWS.

...
nota
Qualquer pessoa que assuma uma função a partir da AWS CLI ou da API pode usar o parâmetro da ILC duration-seconds ou o parâmetro da API DurationSeconds para solicitar uma sessão mais longa. A configuração MaxSessionDuration determina a duração máxima da sessão da função que pode ser solicitada usando o parâmetro DurationSeconds. Se os usuários não especificarem um valor para o parâmetro DurationSeconds, suas credenciais de segurança serão válidas por uma hora.
...

Para alterar a configuração de duração máxima da sessão para funções que são assumidas usando a AWS CLI (AWS CLI)

1. (Opcional) Para visualizar a configuração de duração máxima da sessão atual para uma função, execute o seguinte comando:

*aws iam get-role*

2. Para atualizar uma configuração de duração máxima da sessão da função, execute o seguinte comando com o parâmetro da CLI max-session-duration ou o parâmetro da API MaxSessionDuration:

*aws iam update-role*

Suas alterações não terão efeito até que alguém assuma essa função. Para saber como revogar as sessões existentes para a função, consulte Revogar credenciais de segurança temporárias de função do IAM.

## Modificar o limite de permissões de uma função (AWS CLI)

Para alterar o número máximo de permissões permitidas para uma função, modifique o limite de permissões da função.

###Para alterar a política gerenciada usada para definir o limite de permissões para uma função (AWS CLI)

1. (Opcional) Para visualizar a política de permissões atual de uma função, execute o seguinte comando:

*aws iam get-role*

2. Para usar uma política gerenciada diferente para atualizar o limite de permissões de uma função, execute o seguinte comando:

*aws iam put-role-permissions-boundary*

Uma função pode ter apenas um conjunto de políticas gerenciadas como um limite de permissões. Se você alterar o limite de permissões, você altera o número máximo de permissões permitidas para uma função.

[Artigo original](https://docs.aws.amazon.com/pt_br/IAM/latest/UserGuide/roles-managingrole-editing-cli.html)