# Modificar uma fun��o (AWS CLI)

Voc� pode usar a AWS Command Line Interface para modificar uma fun��o. Para alterar o conjunto de tags em uma fun��o, consulte .

### T�picos

- Modificar uma pol�tica de confian�a de fun��o (AWS CLI)
- Modificar uma pol�tica de permiss�es de fun��o (AWS CLI)
- Modificar a descri��o de uma fun��o (AWS CLI)
- Modificar a dura��o m�xima da sess�o de uma fun��o (AWS CLI)
- Modificar o limite de permiss�es de uma fun��o (AWS CLI)

##Modificar uma pol�tica de confian�a de fun��o (AWS CLI)

Para alterar quem pode assumir uma fun��o, voc� deve modificar a pol�tica de confian�a da fun��o. Voc� n�o pode modificar a pol�tica de confian�a para uma *fun��o vinculada a servi�o*.

...
nota
Se um usu�rio for listado como principal em uma pol�tica de confian�a da fun��o, mas n�o puder assumir a fun��o, verifique o limite de permiss�es do usu�rio. Se um limite de permiss�es for definido para o usu�rio, ele dever� permitir a a��o sts:AssumeRole.
...

## Como modificar uma pol�tica de confian�a da fun��o (AWS CLI)

1. (Opcional) Se voc� n�o souber o nome da fun��o que deseja modificar, execute o seguinte comando para listar as fun��es em sua conta:

aws iam list-roles

2. (Opcional) Para visualizar a pol�tica de confian�a atual de uma fun��o, execute o seguinte comando:

aws iam get-role

3. Para modificar as entidades principais confi�veis que podem acessar a fun��o, crie um arquivo de texto com a pol�tica de confian�a atualizada. � poss�vel usar qualquer editor de texto para construir a pol�tica.

Por exemplo, a seguinte pol�tica de confian�a mostra como fazer refer�ncia a duas contas da AWS no elemento Principal. Isso permite que os usu�rios de duas contas da AWS separadas assumam essa fun��o.

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

Se voc� especificar um principal em outra conta, adicionar uma conta � pol�tica de confian�a de uma fun��o � apenas metade da tarefa de estabelecer o relacionamento de confian�a entre contas. Por padr�o, nenhum usu�rio nas contas confi�veis pode assumir a fun��o. O administrador da conta confi�vel rec�m-criada deve conceder aos usu�rios a permiss�o para assumir a fun��o. Para fazer isso, o administrador deve criar ou editar uma pol�tica que est� anexada ao usu�rio para permitir acesso ao usu�rio � a��o sts:AssumeRole. Para obter mais informa��es, consulte o procedimento a seguir ou Conceder permiss�es a um usu�rio para alternar fun��es.

4. Para usar o arquivo que voc� acabou de criar para atualizar a pol�tica de confian�a, execute o seguinte comando:

aws iam update-assume-role-policy


## Para permitir que os usu�rios em uma conta externa confi�vel usem a fun��o (AWS CLI)

Para obter mais informa��es e detalhes sobre esse procedimento, consulte Conceder permiss�es a um usu�rio para alternar fun��es.

1. Crie um arquivo JSON que contenha uma pol�tica de permiss�es que concede permiss�es para assumir a fun��o. Por exemplo, a seguinte pol�tica cont�m as permiss�es necess�rias m�nimas:

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

Substitua o ARN na instru��o pelo ARN da fun��o que o usu�rio pode assumir.

2. Execute o comando a seguir para fazer upload do arquivo JSON que cont�m a pol�tica de confian�a para o IAM:

*aws iam create-policy*

O resultado desse comando inclui o ARN da pol�tica. Anote esse ARN, pois voc� precisar� dele em uma etapa posterior.

3. Decida qual usu�rio ou grupo ao qual anexar a pol�tica. Se voc� n�o souber o nome do usu�rio ou do grupo pretendido, use um dos seguintes comandos para listar os usu�rios ou os grupos em sua conta:

*aws iam list-users*

*aws iam list-groups*

4. Use um dos seguintes comandos para anexar a pol�tica criada na etapa anterior ao usu�rio ou ao grupo:

*aws iam attach-user-policy*

*aws iam attach-group-policy*

## Modificar uma pol�tica de permiss�es de fun��o (AWS CLI)

### Para alterar as permiss�es permitidas pela fun��o, modifique a pol�tica de permiss�es da fun��o (ou pol�ticas). Voc� n�o pode modificar a pol�tica de permiss�es para uma fun��o vinculada a servi�o no IAM. Pode ser poss�vel modificar a pol�tica de permiss�es no servi�o que depende da fun��o. Para verificar se um servi�o oferece suporte a este recurso, consulte Servi�os da AWS compat�veis com o IAM e procure os servi�os que t�m Sim na coluna Fun��es vinculadas ao servi�o. Escolha um Sim com um link para exibir a documenta��o da fun��o vinculada a servi�o desse servi�o.

Para alterar as permiss�es permitidas por uma fun��o (AWS CLI)

1. (Opcional) Para visualizar as permiss�es atuais associadas a uma fun��o, execute um dos comandos a seguir:

*aws iam list-role-policies* para listar as pol�ticas em linha

*aws iam list-attached-role-policies* para listar as pol�ticas gerenciadas

2. O comando para atualizar as permiss�es para a fun��o ser� diferente se voc� estiver atualizando uma pol�tica gerenciada ou uma pol�tica em linha.

Para atualizar uma pol�tica gerenciada, execute o seguinte comando para criar uma nova vers�o da pol�tica gerenciada:

*aws iam create-policy-version*

Para atualizar uma pol�tica em linha, execute o seguinte comando:

*aws iam put-role-policy*

## Modificar a descri��o de uma fun��o (AWS CLI)

Para alterar a descri��o da fun��o, modifique o texto da descri��o.

Para alterar a descri��o de uma fun��o (AWS CLI)

1. (Opcional) Para visualizar a descri��o atual de uma fun��o, execute o comando a seguir:

*aws iam get-role*

Para atualizar a descri��o de uma fun��o, execute o seguinte comando com o par�metro de descri��o:

*aws iam update-role*

## Modificar a dura��o m�xima da sess�o de uma fun��o (AWS CLI)

Para especificar a configura��o de dura��o m�xima da sess�o para fun��es que s�o assumidas usando a AWS CLI ou a API, modifique o valor da configura��o da dura��o m�xima da sess�o. Essa configura��o pode ter um valor de 1 hora a 12 horas. Se voc� n�o especificar um valor, o padr�o m�ximo de 1 hora ser� aplicado. Essa configura��o n�o limita sess�es assumidas por servi�os da AWS.

...
nota
Qualquer pessoa que assuma uma fun��o a partir da AWS CLI ou da API pode usar o par�metro da ILC duration-seconds ou o par�metro da API DurationSeconds para solicitar uma sess�o mais longa. A configura��o MaxSessionDuration determina a dura��o m�xima da sess�o da fun��o que pode ser solicitada usando o par�metro DurationSeconds. Se os usu�rios n�o especificarem um valor para o par�metro DurationSeconds, suas credenciais de seguran�a ser�o v�lidas por uma hora.
...

Para alterar a configura��o de dura��o m�xima da sess�o para fun��es que s�o assumidas usando a AWS CLI (AWS CLI)

1. (Opcional) Para visualizar a configura��o de dura��o m�xima da sess�o atual para uma fun��o, execute o seguinte comando:

*aws iam get-role*

2. Para atualizar uma configura��o de dura��o m�xima da sess�o da fun��o, execute o seguinte comando com o par�metro da CLI max-session-duration ou o par�metro da API MaxSessionDuration:

*aws iam update-role*

Suas altera��es n�o ter�o efeito at� que algu�m assuma essa fun��o. Para saber como revogar as sess�es existentes para a fun��o, consulte Revogar credenciais de seguran�a tempor�rias de fun��o do IAM.

## Modificar o limite de permiss�es de uma fun��o (AWS CLI)

Para alterar o n�mero m�ximo de permiss�es permitidas para uma fun��o, modifique o limite de permiss�es da fun��o.

###Para alterar a pol�tica gerenciada usada para definir o limite de permiss�es para uma fun��o (AWS CLI)

1. (Opcional) Para visualizar a pol�tica de permiss�es atual de uma fun��o, execute o seguinte comando:

*aws iam get-role*

2. Para usar uma pol�tica gerenciada diferente para atualizar o limite de permiss�es de uma fun��o, execute o seguinte comando:

*aws iam put-role-permissions-boundary*

Uma fun��o pode ter apenas um conjunto de pol�ticas gerenciadas como um limite de permiss�es. Se voc� alterar o limite de permiss�es, voc� altera o n�mero m�ximo de permiss�es permitidas para uma fun��o.

[Artigo original](https://docs.aws.amazon.com/pt_br/IAM/latest/UserGuide/roles-managingrole-editing-cli.html)