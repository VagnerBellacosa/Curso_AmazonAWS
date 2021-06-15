# Usar o AWS Lambda com o Amazon DynamoDB

� poss�vel usar uma fun��o do AWS Lambda para processar registros em um fluxo do Amazon DynamoDB. Com o DynamoDB Streams, voc� pode acionar uma fun��o do Lambda para executar o trabalho adicional cada vez que uma tabela do DynamoDB � atualizada.

O Lambda l� registros do fluxo e invoca sua fun��o de forma s�ncrona com um evento que cont�m registros de fluxo. O Lambda l� registros em lotes e invoca sua fun��o para processar registros do lote.

**exemplo Evento de registro do DynamoDB Streams**

...
{
  "Records": [
    {
      "eventID": "1",
      "eventVersion": "1.0",
      "dynamodb": {
        "Keys": {
          "Id": {
            "N": "101"
          }
        },
        "NewImage": {
          "Message": {
            "S": "New item!"
          },
          "Id": {
            "N": "101"
          }
        },
        "StreamViewType": "NEW_AND_OLD_IMAGES",
        "SequenceNumber": "111",
        "SizeBytes": 26
      },
      "awsRegion": "us-west-2",
      "eventName": "INSERT",
      "eventSourceARN": eventsourcearn,
      "eventSource": "aws:dynamodb"
    },
    {
      "eventID": "2",
      "eventVersion": "1.0",
      "dynamodb": {
        "OldImage": {
          "Message": {
            "S": "New item!"
          },
          "Id": {
            "N": "101"
          }
        },
        "SequenceNumber": "222",
        "Keys": {
          "Id": {
            "N": "101"
          }
        },
        "SizeBytes": 59,
        "NewImage": {
          "Message": {
            "S": "This item has changed"
          },
          "Id": {
            "N": "101"
          }
        },
        "StreamViewType": "NEW_AND_OLD_IMAGES"
      },
      "awsRegion": "us-west-2",
      "eventName": "MODIFY",
      "eventSourceARN": sourcearn,
      "eventSource": "aws:dynamodb"
    }
...

O Lambda sonda os estilha�os em seu fluxo do DynamoDB para registros a uma taxa b�sica de 4 vezes por segundo. Quando os registros est�o dispon�veis, o Lambda invoca a fun��o e aguarda o resultado. Se o processamento for bem-sucedido, o Lambda continua a sondagem at� que ela receba mais registros.

Por padr�o, o Lambda invoca sua fun��o assim que os registros est�o dispon�veis no fluxo. Se o lote que o Lambda l� do stream tiver somente um registro, o Lambda enviar� apenas um registro � fun��o. Para evitar invocar a fun��o com um n�mero pequeno de registros, voc� pode instruir � origem dos eventos para fazer o buffer dos registros por at� cinco minutos, configurando uma janela de lote. Antes de invocar a fun��o, o Lambda continua lendo os registros do fluxo at� reunir um lote completo ou at� a janela do lote expirar.

Se a sua fun��o retornar um erro, o Lambda tentar� executar novamente o lote at� que o processamento seja bem-sucedido ou os dados expirem. Para evitar estilha�os paralisados, voc� pode configurar o mapeamento de origem de evento para tentar ser executado novamente com um tamanho de lote menor, limitar o n�mero de novas tentativas ou descartar os registros muito antigos. Para manter os eventos descartados, � poss�vel configurar o mapeamento de origem de evento para enviar detalhes sobre lotes com falha para uma fila do SQS ou um t�pico do SNS.

Voc� tamb�m pode aumentar a simultaneidade processando v�rios lotes de cada estilha�o em paralelo. O Lambda pode processar at� 10 lotes em cada estilha�o simultaneamente. Se voc� aumentar o n�mero de lotes simult�neos por estilha�o, o Lambda ainda garantir� o processamento na ordem no n�vel de chave de parti��o.

Defina a configura��o ParallelizationFactor para processar um fragmento de um stream de dados Kinesis ou DynamoDB ou com mais de uma invoca��o do Lambda simultaneamente. � poss�vel especificar o n�mero de lotes simult�neos em que o Lambda realiza a pesquisa de um fragmento por meio de um fator de paraleliza��o de 1 (padr�o) a 10. Por exemplo, quando ParallelizationFactor � definido como 2, voc� pode ter 200 invoca��es simult�neas do Lambda no m�ximo para processar 100 fragmentos de dados do Kinesis. Isso ajuda a aumentar a taxa de transfer�ncia de processamento quando o volume de dados � vol�til e o IteratorAge � alto. Para obter mais informa��es, consulte Novos controles de escalabilidade do AWS Lambda para fontes de eventos do Kinesis e do DynamoDB.

### Se��es

- Permiss�es da fun��o de execu��o
- Configurar um stream como fonte de eventos
- APIs de mapeamento da fonte de eventos
- Tratamento de erros
- M�tricas do Amazon CloudWatch
- Janelas de tempo
- Gerar relat�rios de falhas de itens de lote
- Tutorial: usar o AWS Lambda com streams do Amazon DynamoDB
- C�digo de exemplo da fun��o
- Modelo do AWS SAM para um aplicativo do DynamoDB

## Permiss�es da fun��o de execu��o

O Lambda precisa das permiss�es a seguir para gerenciar recursos relacionados ao seu fluxo do DynamoDB. Adicione-as � fun��o de execu��o da sua fun��o.

**dynamodb:DescribeStream**

**dynamodb:GetRecords**

**dynamodb:GetShardIterator**

**dynamodb:ListStreams**

**dynamodb:ListShards**

A pol�tica gerenciada AWSLambdaDynamoDBExecutionRole inclui essas permiss�es. Para obter mais informa��es, consulte Fun��o de execu��o do AWS Lambda.

Para enviar registros de lotes com falha para uma fila ou um t�pico, sua fun��o precisa de permiss�es adicionais. Cada servi�o de destino requer uma permiss�o diferente, como se segue:

**Amazon SQS �** *sqs:SendMessage*

**Amazon SNS �** *sns:Publish*

## Configurar um stream como fonte de eventos

Crie um mapeamento de fonte do evento para orientar o Lambda a enviar registros de seu fluxo para uma fun��o do Lambda. � poss�vel criar v�rios mapeamentos de origem de evento para processar os mesmos dados com v�rias fun��es do Lambda ou processar itens de v�rios fluxos com uma �nica fun��o.

Para configurar sua fun��o para leitura no DynamoDB Streams no console do Lambda, crie um **gatilho DynamoDB**.

### Para criar um gatilho

1. Abra a p�gina Fun��es do console do Lambda.
2. Escolha uma fun��o.
3. Em Function overview (Vis�o geral da fun��o), escolha Add trigger (Adicionar gatilho).
4. Escolha um tipo de gatinho.
5. Configure as op��es necess�rias e, em seguida, escolha Add (Adicionar).

O Lambda oferece suporte �s seguintes op��es das fontes de evento do DynamoDB.

### Op��es de fonte do evento

- Tabela DynamoDB � A tabela DynamoDB da qual os registros ser�o lidos.

- Batch size (Tamanho do lote) � o n�mero de registros a serem enviados para a fun��o em cada lote, at� 10.000. O Lambda transmite todos os registros no lote para a fun��o em uma �nica chamada, desde que o tamanho total dos eventos n�o exceda o limite de carga para a invoca��o s�ncrona (6 MB).

- Batch window (Janela de lote) � especifique o m�ximo de tempo para reunir registros antes de invocar a fun��o, em segundos.

- Starting position (Posi��o inicial) � Processe apenas registros novos ou todos os registros existentes.

	- Latest (Mais recente) � Processe novos registros adicionados ao fluxo.

	- Trim horizon (Redu��o horizontal) � Processe todos os registros no fluxo.

Depois de processar todos os registros existentes, a fun��o � capturada e continua a processar novos registros.

- On-failure destination (Destino em caso de falha) � uma fila do SQS ou um t�pico do SNS para registros que n�o possam ser processados. Quando o Lambda descarta um lote de registros porque � muito antigo ou esgotou todas as novas tentativas, ele envia detalhes sobre o lote para a fila ou o t�pico.

Retry attempts (Novas tentativas) � o n�mero m�ximo de vezes que o Lambda faz novas tentativas quando a fun��o retorna um erro. Isso n�o se aplica a erros ou limita��es do servi�o em que o lote n�o atingiu a fun��o.

Maximum age of record (Idade m�xima do registro) � a idade m�xima de um registro que o Lambda envia para sua fun��o.

Split batch on error (Dividir o lote em caso de erro) � quando a fun��o retorna um erro, divide o lote em dois antes de tentar novamente.

Concurrent batches per shard (Lotes simult�neos por estilha�o) � processa v�rios lotes do mesmo estilha�o simultaneamente.

Habilitado � Defina como verdadeiro para habilitar o mapeamento da origem do evento. Defina como falso para interromper o processamento de registros. O Lambda monitora o �ltimo registro processado e retoma o processamento a partir desse ponto quando o mapeamento � habilitado novamente.

nota

Voc� n�o � cobrado por chamadas da API GetRecords invocadas pelo Lambda como parte de triggers do DynamoDB.

Para gerenciar a configura��o da fonte do evento posteriormente, escolha o gatilho no designer.

### APIs de mapeamento da fonte de eventos

Para gerenciar uma origem de evento com a AWS CLI ou o AWS SDK, � poss�vel usar as seguintes opera��es de API:

- CreateEventSourceMapping
- ListEventSourceMappings
- GetEventSourceMapping
- UpdateEventSourceMapping
- DeleteEventSourceMapping

O exemplo a seguir usa a AWS CLI para mapear uma fun��o denominada my-function para um fluxo do DynamoDB especificado pelo respectivo nome de recurso da Amazon (ARN), com um tamanho de lote de 500.

aws lambda create-event-source-mapping --function-name my-function --batch-size 500 --starting-position LATEST \
--event-source-arn arn:aws:dynamodb:us-east-2:123456789012:table/my-table/stream/2019-06-10T19:26:16.525
Voc� deve ver a sa�da a seguir:

...
{
    "UUID": "14e0db71-5d35-4eb5-b481-8945cf9d10c2",
    "BatchSize": 500,
    "MaximumBatchingWindowInSeconds": 0,
    "ParallelizationFactor": 1,
    "EventSourceArn": "arn:aws:dynamodb:us-east-2:123456789012:table/my-table/stream/2019-06-10T19:26:16.525",
    "FunctionArn": "arn:aws:lambda:us-east-2:123456789012:function:my-function",
    "LastModified": 1560209851.963,
    "LastProcessingResult": "No records processed",
    "State": "Creating",
    "StateTransitionReason": "User action",
    "DestinationConfig": {},
    "MaximumRecordAgeInSeconds": 604800,
    "BisectBatchOnFunctionError": false,
    "MaximumRetryAttempts": 10000
}
...

Configure op��es adicionais para personalizar como os lotes s�o processados e para especificar quando descartar os registros que n�o podem ser processados. O exemplo a seguir atualiza um mapeamento de origem de evento para enviar um registro de falha para uma fila do SQS depois de duas novas tentativas ou se os registros tiverem mais uma hora.

aws lambda update-event-source-mapping --uuid f89f8514-cdd9-4602-9e1f-01a5b77d449b \
--maximum-retry-attempts 2  --maximum-record-age-in-seconds 3600
--destination-config '{"OnFailure": {"Destination": "arn:aws:sqs:us-east-2:123456789012:dlq"}}'

Voc� deve ver esta sa�da:

...
{
    "UUID": "f89f8514-cdd9-4602-9e1f-01a5b77d449b",
    "BatchSize": 100,
    "MaximumBatchingWindowInSeconds": 0,
    "ParallelizationFactor": 1,
    "EventSourceArn": "arn:aws:dynamodb:us-east-2:123456789012:table/my-table/stream/2019-06-10T19:26:16.525",
    "FunctionArn": "arn:aws:lambda:us-east-2:123456789012:function:my-function",
    "LastModified": 1573243620.0,
    "LastProcessingResult": "PROBLEM: Function call failed",
    "State": "Updating",
    "StateTransitionReason": "User action",
    "DestinationConfig": {},
    "MaximumRecordAgeInSeconds": 604800,
    "BisectBatchOnFunctionError": false,
    "MaximumRetryAttempts": 10000
}
...

As configura��es atualizadas s�o aplicadas de forma ass�ncrona e n�o s�o refletidas na sa�da at� que o processo seja conclu�do. Use o comando get-event-source-mapping para ver o status atual.

aws lambda get-event-source-mapping --uuid f89f8514-cdd9-4602-9e1f-01a5b77d449b
Voc� deve ver esta sa�da:

...
{
    "UUID": "f89f8514-cdd9-4602-9e1f-01a5b77d449b",
    "BatchSize": 100,
    "MaximumBatchingWindowInSeconds": 0,
    "ParallelizationFactor": 1,
    "EventSourceArn": "arn:aws:dynamodb:us-east-2:123456789012:table/my-table/stream/2019-06-10T19:26:16.525",
    "FunctionArn": "arn:aws:lambda:us-east-2:123456789012:function:my-function",
    "LastModified": 1573244760.0,
    "LastProcessingResult": "PROBLEM: Function call failed",
    "State": "Enabled",
    "StateTransitionReason": "User action",
    "DestinationConfig": {
        "OnFailure": {
            "Destination": "arn:aws:sqs:us-east-2:123456789012:dlq"
        }
    },
    "MaximumRecordAgeInSeconds": 3600,
    "BisectBatchOnFunctionError": false,
    "MaximumRetryAttempts": 2
}
...

Para processar v�rios lotes simultaneamente, use a op��o --parallelization-factor.

aws lambda update-event-source-mapping --uuid 2b733gdc-8ac3-cdf5-af3a-1827b3b11284 \
--parallelization-factor 5
Tratamento de erros
O mapeamento de origem de evento que l� registros do fluxo do DynamoDB invoca sua fun��o de forma s�ncrona e tenta novamente em caso de erros. Se a fun��o for limitada ou o servi�o Lambda retornar um erro sem invocar a fun��o, o Lambda tentar� novamente at� os registros expirarem ou excederem a idade m�xima que voc� configurar no mapeamento de origem de evento.

Se a fun��o receber os registros, mas retornar um erro, o Lambda tentar� novamente at� os registros do lote expirarem, excederem a idade m�xima ou atingirem a cota de novas tentativas configurada. Para erros de fun��o, tamb�m � poss�vel configurar o mapeamento de origem de evento para dividir um lote com falha em dois lotes. A nova tentativa com lotes menores isola os registros inv�lidos e contorna problemas de tempo limite. A divis�o de um lote n�o � levada em considera��o na cota de novas tentativas.

Se as medidas de tratamento de erros falharem, o Lambda descartar� os registros e continuar� a processar os lotes do fluxo. Com as configura��es padr�o, isso significa que um registro inv�lido pode bloquear o processamento no estilha�o afetado por at� one day. Para evitar isso, configure o mapeamento de origem de evento da sua fun��o com um n�mero razo�vel de novas tentativas e uma idade m�xima de registro adequada ao seu caso de uso.

Para manter um registro de lotes descartados, configure um destino de evento com falha. O Lambda envia um documento para a fila ou o t�pico de destino com detalhes sobre o lote.

Como configurar um destino para registros de eventos com falha

Abra a p�gina Fun��es do console do Lambda.

Escolha uma fun��o.

Em Function overview (Vis�o geral da fun��o), escolha Add destination (Adicionar destino).

Em Source (Origem), escolha Stream invocation (Chamada de fluxo).

Para Stream (Fluxo), escolha um fluxo mapeado para a fun��o.

Em Destination type (Tipo de destino), escolha o tipo de recurso que recebe o registro da invoca��o.

Em Destination (Destino), escolha um recurso.

Escolha Save (Salvar).

O exemplo a seguir mostra um registro de invoca��o de um stream do DynamoDB.

#### exemplo Registro de invoca��o

...
{
    "requestContext": {
        "requestId": "316aa6d0-8154-xmpl-9af7-85d5f4a6bc81",
        "functionArn": "arn:aws:lambda:us-east-2:123456789012:function:myfunction",
        "condition": "RetryAttemptsExhausted",
        "approximateInvokeCount": 1
    },
    "responseContext": {
        "statusCode": 200,
        "executedVersion": "$LATEST",
        "functionError": "Unhandled"
    },
    "version": "1.0",
    "timestamp": "2019-11-14T00:13:49.717Z",
    "DDBStreamBatchInfo": {
        "shardId": "shardId-00000001573689847184-864758bb",
        "startSequenceNumber": "800000000003126276362",
        "endSequenceNumber": "800000000003126276362",
        "approximateArrivalOfFirstRecord": "2019-11-14T00:13:19Z",
        "approximateArrivalOfLastRecord": "2019-11-14T00:13:19Z",
        "batchSize": 1,
        "streamArn": "arn:aws:dynamodb:us-east-2:123456789012:table/mytable/stream/2019-11-14T00:04:06.388"
    }
}
...

Voc� pode usar essas informa��es para recuperar os registros afetados do fluxo para solu��o de problemas. Os registros reais n�o s�o inclu�dos, portanto, voc� deve processar esse registro e recuper�-los do fluxo antes que eles expirem ou sejam perdidos.

### M�tricas do Amazon CloudWatch

Lambda emite a m�trica IteratorAge quando a sua fun��o termina de processar um lote de registros. A m�trica indica a idade do �ltimo registro no lote quando o processamento foi conclu�do. Se a sua fun��o estiver processando novos eventos, voc� poder� usar a idade do iterador para estimar a lat�ncia entre quando um registro � adicionado e quando a fun��o o processa.

Uma tend�ncia crescente na idade do iterador pode indicar problemas com sua fun��o. Para obter mais informa��es, consulte Trabalhar com m�tricas de fun��o do AWS Lambda.

### Janelas de tempo

As fun��es do Lambda podem executar aplica��es de processamento cont�nuo de streams. Um stream representa dados n�o vinculados que fluem continuamente por meio de sua aplica��o. Para analisar as informa��es dessa entrada de atualiza��o cont�nua, voc� pode vincular os registros inclu�dos usando uma janela definida em termos de tempo.

As invoca��es do Lambda s�o sem estado. N�o � poss�vel us�-las para processar dados ao longo de v�rias invoca��es cont�nuas sem um banco de dados externo. No entanto, com o Windows habilitado, voc� pode manter seu estado em todas as invoca��es. Esse estado cont�m o resultado agregado das mensagens previamente processadas para a janela atual. Seu estado pode ter no m�ximo 1 MB por fragmento. Se exceder esse tamanho, o Lambda encerra a janela antes.

### Janelas em cascata

As fun��es do Lambda podem agregar dados usando janelas em cascata: janelas de tempo distintas que abrem e fecham em intervalos regulares. As janelas em cascata permitem que voc� processe fontes de dados de streaming por meio de janelas de tempo cont�guas e n�o sobrepostas.

Cada registro de um stream pertence a uma janela espec�fica. Um registro � processado apenas uma vez, quando o Lambda processa a janela � qual o registro pertence. Em cada janela, voc� pode executar c�lculos, como uma soma ou m�dia, no n�vel da chave de parti��o dentro de um fragmento.

### Agrega��o e processamento

Sua fun��o gerenciada pelo usu�rio � chamada tanto para agrega��o quanto para processamento dos resultados finais dessa agrega��o. O Lambda agrega todos os registros recebidos na janela. Voc� pode receber esses registros em v�rios lotes, cada um como uma invoca��o separada. Cada invoca��o recebe um estado. Voc� tamb�m pode processar registros e retornar um novo estado, que � passado na pr�xima chamada. O Lambda retorna um TimeWindowEventResponse em JSON no seguinte formato:

#### exemplo Valores de TimeWindowEventReponse

...
{
    "state": {
        "1": 282,
        "2": 715
    },
    "batchItemFailures": []
}
...

#### nota

Para fun��es Java, recomendamos o uso de Mapa<String, String> para representar o estado.

No final da janela, a sinaliza��o isFinalInvokeForWindow � definida como true para indicar que esse � o estado final e que est� pronto para processamento. Ap�s o processamento, a janela � conclu�da e sua invoca��o final � conclu�da e, em seguida, o estado � descartado.

No final da janela, o Lambda usa o processamento final para a��es sobre os resultados da agrega��o. Seu processamento final � invocado de forma s�ncrona. Ap�s a invoca��o bem-sucedida, sua fun��o define os pontos de verifica��o no n�mero da sequ�ncia e o processamento de streams continua. Se a invoca��o n�o for bem-sucedida, sua fun��o do Lambda suspender� o processamento adicional at� uma chamada bem-sucedida.


#### exemplo DynamodbTimeWindowEvent

...
{
   "Records":[
      {
         "eventID":"1",
         "eventName":"INSERT",
         "eventVersion":"1.0",
         "eventSource":"aws:dynamodb",
         "awsRegion":"us-east-1",
         "dynamodb":{
            "Keys":{
               "Id":{
                  "N":"101"
               }
            },
            "NewImage":{
               "Message":{
                  "S":"New item!"
               },
               "Id":{
                  "N":"101"
               }
            },
            "SequenceNumber":"111",
            "SizeBytes":26,
            "StreamViewType":"NEW_AND_OLD_IMAGES"
         },
         "eventSourceARN":"stream-ARN"
      },
      {
         "eventID":"2",
         "eventName":"MODIFY",
         "eventVersion":"1.0",
         "eventSource":"aws:dynamodb",
         "awsRegion":"us-east-1",
         "dynamodb":{
            "Keys":{
               "Id":{
                  "N":"101"
               }
            },
            "NewImage":{
               "Message":{
                  "S":"This item has changed"
               },
               "Id":{
                  "N":"101"
               }
            },
            "OldImage":{
               "Message":{
                  "S":"New item!"
               },
               "Id":{
                  "N":"101"
               }
            },
            "SequenceNumber":"222",
            "SizeBytes":59,
            "StreamViewType":"NEW_AND_OLD_IMAGES"
         },
         "eventSourceARN":"stream-ARN"
      },
      {
         "eventID":"3",
         "eventName":"REMOVE",
         "eventVersion":"1.0",
         "eventSource":"aws:dynamodb",
         "awsRegion":"us-east-1",
         "dynamodb":{
            "Keys":{
               "Id":{
                  "N":"101"
               }
            },
            "OldImage":{
               "Message":{
                  "S":"This item has changed"
               },
               "Id":{
                  "N":"101"
               }
            },
            "SequenceNumber":"333",
            "SizeBytes":38,
            "StreamViewType":"NEW_AND_OLD_IMAGES"
         },
         "eventSourceARN":"stream-ARN"
      }
   ],
    "window": {
        "start": "2020-07-30T17:00:00Z",
        "end": "2020-07-30T17:05:00Z"
    },
    "state": {
        "1": "state1"
    },
    "shardId": "shard123456789",
    "eventSourceARN": "stream-ARN",
    "isFinalInvokeForWindow": false,
    "isWindowTerminatedEarly": false
}
...

### Configura��o
Voc� pode configurar janelas em cascata ao criar ou atualizar um mapeamento de fonte de eventos. Para configurar uma janela em cascata, especifique a janela em segundos. O comando de exemplo da AWS Command Line Interface (AWS CLI) a seguir cria um mapeamento de fonte de eventos em streaming com uma janela em cascata de 120 segundos. A fun��o do Lambda definida para agrega��o e processamento � chamada de tumbling-window-example-function.

aws lambda create-event-source-mapping --event-source-arn arn:aws:dynamodb:us-east-1:123456789012:stream/lambda-stream --function-name "arn:aws:lambda:us-east-1:123456789018:function:tumbling-window-example-function" --region us-east-1 --starting-position TRIM_HORIZON --tumbling-window-in-seconds 120
O Lambda determina os limites da janela em cascata com base no hor�rio em que os registros foram inseridos no stream. Todos os registros t�m um carimbo de data/hora aproximado dispon�vel que o Lambda usa para determinar os limites.

As agrega��es de janelas em cascata n�o s�o compat�veis com refragmenta��o. Quando o fragmento termina, o Lambda considera a janela como fechada e os fragmentos filhos iniciam suas pr�prias janelas em um novo estado.

As janelas em cascata s�o totalmente compat�veis com as pol�ticas maxRetryAttempts e maxRecordAge.

#### exemplo Handler.py � agrega��o e processamento

A fun��o do Python a seguir demonstra como agregar e, em seguida, processar seu estado final:
...
def lambda_handler(event, context):
    print('Incoming event: ', event)
    print('Incoming state: ', event['state'])

#Check if this is the end of the window to either aggregate or process.
    if event['isFinalInvokeForWindow']:
        # logic to handle final state of the window
        print('Destination invoke')
    else:
        print('Aggregate invoke')

#Check for early terminations
    if event['isWindowTerminatedEarly']:
        print('Window terminated early')

    #Aggregation logic
    state = event['state']
    for record in event['Records']:
        state[record['dynamodb']['NewImage']['Id']] = state.get(record['dynamodb']['NewImage']['Id'], 0) + 1

    print('Returning state: ', state)
    return {'state': state}
...

## Gerar relat�rios de falhas de itens de lote

Ao consumir e processar dados de streaming de uma fonte de eventos, o Lambda definir� checkpoints por padr�o no n�mero mais elevado na sequ�ncia de um lote somente quando o lote for um sucesso total. O Lambda trata todos os outros resultados como falha absoluta e tenta processar novamente o lote at� o limite de novas tentativas. Para permitir sucessos parciais durante o processamento de lotes de um stream, ative ReportBatchItemFailures. Permitir sucessos parciais pode ajudar a reduzir o n�mero de novas tentativas em um registro, embora n�o impe�a totalmente a possibilidade de novas tentativas em um registro bem-sucedido.

Para ativar ReportBatchItemFailures, inclua o valor de enum ReportBatchItemFailures na lista FunctionResponseTypes. Essa lista indica quais tipos de resposta est�o habilitados para sua fun��o. Voc� pode configurar essa lista ao criar ou atualizar um mapeamento de fonte de eventos.

### Sintaxe do relat�rio

Ao configurar relat�rios sobre falhas de itens de lote, a classe *StreamsEventResponse* � retornada com uma lista de falhas de itens de lote. � poss�vel usar um objeto StreamsEventResponse para retornar o n�mero sequencial do primeiro registro com falha no lote. Voc� tamb�m pode criar sua pr�pria classe personalizada usando a sintaxe de resposta correta. A seguinte estrutura JSON mostra a sintaxe de resposta necess�ria:

...
{ 
  "batchItemFailures": [ 
        {
            "itemIdentifier": "<id>"
        }
    ]
}
...

### Condi��es de sucesso e falha

O Lambda trata um lote como um sucesso completo se voc� retornar qualquer um dos seguintes:

- Uma lista de batchItemFailure vazia
- Uma lista de batchItemFailure nula
- Uma EventResponse vazia
- Uma EventResponse nula

O Lambda trata um lote como uma falha absoluta se voc� retornar qualquer um dos seguintes:

- Uma string *itemIdentifier* vazia

- Um *itemIdentifier* nulo

- Um *itemIdentifier* com um nome de chave inv�lido

O Lambda faz novas tentativas ap�s falhas com base na sua estrat�gia de repeti��o.

### Dividir um lote

Se a invoca��o falhar e *BisectBatchOnFunctionError* estiver ativado, o lote ser� dividido independentemente da configura��o de *ReportBatchItemFailures*.

Quando uma resposta de sucesso parcial do lote � recebida e tanto *BisectBatchOnFunctionError* quanto *ReportBatchItemFailures* est�o ativados, o lote � dividido no n�mero de sequ�ncia retornado e o Lambda tenta novamente apenas os registros restantes.



[Artigo original](https://docs.aws.amazon.com/pt_br/lambda/latest/dg/with-ddb.html)