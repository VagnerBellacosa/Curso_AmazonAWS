# Usar o AWS Lambda com o Amazon DynamoDB

É possível usar uma função do AWS Lambda para processar registros em um fluxo do Amazon DynamoDB. Com o DynamoDB Streams, você pode acionar uma função do Lambda para executar o trabalho adicional cada vez que uma tabela do DynamoDB é atualizada.

O Lambda lê registros do fluxo e invoca sua função de forma síncrona com um evento que contém registros de fluxo. O Lambda lê registros em lotes e invoca sua função para processar registros do lote.

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

O Lambda sonda os estilhaços em seu fluxo do DynamoDB para registros a uma taxa básica de 4 vezes por segundo. Quando os registros estão disponíveis, o Lambda invoca a função e aguarda o resultado. Se o processamento for bem-sucedido, o Lambda continua a sondagem até que ela receba mais registros.

Por padrão, o Lambda invoca sua função assim que os registros estão disponíveis no fluxo. Se o lote que o Lambda lê do stream tiver somente um registro, o Lambda enviará apenas um registro à função. Para evitar invocar a função com um número pequeno de registros, você pode instruir à origem dos eventos para fazer o buffer dos registros por até cinco minutos, configurando uma janela de lote. Antes de invocar a função, o Lambda continua lendo os registros do fluxo até reunir um lote completo ou até a janela do lote expirar.

Se a sua função retornar um erro, o Lambda tentará executar novamente o lote até que o processamento seja bem-sucedido ou os dados expirem. Para evitar estilhaços paralisados, você pode configurar o mapeamento de origem de evento para tentar ser executado novamente com um tamanho de lote menor, limitar o número de novas tentativas ou descartar os registros muito antigos. Para manter os eventos descartados, é possível configurar o mapeamento de origem de evento para enviar detalhes sobre lotes com falha para uma fila do SQS ou um tópico do SNS.

Você também pode aumentar a simultaneidade processando vários lotes de cada estilhaço em paralelo. O Lambda pode processar até 10 lotes em cada estilhaço simultaneamente. Se você aumentar o número de lotes simultâneos por estilhaço, o Lambda ainda garantirá o processamento na ordem no nível de chave de partição.

Defina a configuração ParallelizationFactor para processar um fragmento de um stream de dados Kinesis ou DynamoDB ou com mais de uma invocação do Lambda simultaneamente. É possível especificar o número de lotes simultâneos em que o Lambda realiza a pesquisa de um fragmento por meio de um fator de paralelização de 1 (padrão) a 10. Por exemplo, quando ParallelizationFactor é definido como 2, você pode ter 200 invocações simultâneas do Lambda no máximo para processar 100 fragmentos de dados do Kinesis. Isso ajuda a aumentar a taxa de transferência de processamento quando o volume de dados é volátil e o IteratorAge é alto. Para obter mais informações, consulte Novos controles de escalabilidade do AWS Lambda para fontes de eventos do Kinesis e do DynamoDB.

### Seções

- Permissões da função de execução
- Configurar um stream como fonte de eventos
- APIs de mapeamento da fonte de eventos
- Tratamento de erros
- Métricas do Amazon CloudWatch
- Janelas de tempo
- Gerar relatórios de falhas de itens de lote
- Tutorial: usar o AWS Lambda com streams do Amazon DynamoDB
- Código de exemplo da função
- Modelo do AWS SAM para um aplicativo do DynamoDB

## Permissões da função de execução

O Lambda precisa das permissões a seguir para gerenciar recursos relacionados ao seu fluxo do DynamoDB. Adicione-as à função de execução da sua função.

**dynamodb:DescribeStream**

**dynamodb:GetRecords**

**dynamodb:GetShardIterator**

**dynamodb:ListStreams**

**dynamodb:ListShards**

A política gerenciada AWSLambdaDynamoDBExecutionRole inclui essas permissões. Para obter mais informações, consulte Função de execução do AWS Lambda.

Para enviar registros de lotes com falha para uma fila ou um tópico, sua função precisa de permissões adicionais. Cada serviço de destino requer uma permissão diferente, como se segue:

**Amazon SQS –** *sqs:SendMessage*

**Amazon SNS –** *sns:Publish*

## Configurar um stream como fonte de eventos

Crie um mapeamento de fonte do evento para orientar o Lambda a enviar registros de seu fluxo para uma função do Lambda. É possível criar vários mapeamentos de origem de evento para processar os mesmos dados com várias funções do Lambda ou processar itens de vários fluxos com uma única função.

Para configurar sua função para leitura no DynamoDB Streams no console do Lambda, crie um **gatilho DynamoDB**.

### Para criar um gatilho

1. Abra a página Funções do console do Lambda.
2. Escolha uma função.
3. Em Function overview (Visão geral da função), escolha Add trigger (Adicionar gatilho).
4. Escolha um tipo de gatinho.
5. Configure as opções necessárias e, em seguida, escolha Add (Adicionar).

O Lambda oferece suporte às seguintes opções das fontes de evento do DynamoDB.

### Opções de fonte do evento

- Tabela DynamoDB – A tabela DynamoDB da qual os registros serão lidos.

- Batch size (Tamanho do lote) – o número de registros a serem enviados para a função em cada lote, até 10.000. O Lambda transmite todos os registros no lote para a função em uma única chamada, desde que o tamanho total dos eventos não exceda o limite de carga para a invocação síncrona (6 MB).

- Batch window (Janela de lote) – especifique o máximo de tempo para reunir registros antes de invocar a função, em segundos.

- Starting position (Posição inicial) – Processe apenas registros novos ou todos os registros existentes.

	- Latest (Mais recente) – Processe novos registros adicionados ao fluxo.

	- Trim horizon (Redução horizontal) – Processe todos os registros no fluxo.

Depois de processar todos os registros existentes, a função é capturada e continua a processar novos registros.

- On-failure destination (Destino em caso de falha) – uma fila do SQS ou um tópico do SNS para registros que não possam ser processados. Quando o Lambda descarta um lote de registros porque é muito antigo ou esgotou todas as novas tentativas, ele envia detalhes sobre o lote para a fila ou o tópico.

Retry attempts (Novas tentativas) – o número máximo de vezes que o Lambda faz novas tentativas quando a função retorna um erro. Isso não se aplica a erros ou limitações do serviço em que o lote não atingiu a função.

Maximum age of record (Idade máxima do registro) – a idade máxima de um registro que o Lambda envia para sua função.

Split batch on error (Dividir o lote em caso de erro) – quando a função retorna um erro, divide o lote em dois antes de tentar novamente.

Concurrent batches per shard (Lotes simultâneos por estilhaço) – processa vários lotes do mesmo estilhaço simultaneamente.

Habilitado – Defina como verdadeiro para habilitar o mapeamento da origem do evento. Defina como falso para interromper o processamento de registros. O Lambda monitora o último registro processado e retoma o processamento a partir desse ponto quando o mapeamento é habilitado novamente.

nota

Você não é cobrado por chamadas da API GetRecords invocadas pelo Lambda como parte de triggers do DynamoDB.

Para gerenciar a configuração da fonte do evento posteriormente, escolha o gatilho no designer.

### APIs de mapeamento da fonte de eventos

Para gerenciar uma origem de evento com a AWS CLI ou o AWS SDK, é possível usar as seguintes operações de API:

- CreateEventSourceMapping
- ListEventSourceMappings
- GetEventSourceMapping
- UpdateEventSourceMapping
- DeleteEventSourceMapping

O exemplo a seguir usa a AWS CLI para mapear uma função denominada my-function para um fluxo do DynamoDB especificado pelo respectivo nome de recurso da Amazon (ARN), com um tamanho de lote de 500.

aws lambda create-event-source-mapping --function-name my-function --batch-size 500 --starting-position LATEST \
--event-source-arn arn:aws:dynamodb:us-east-2:123456789012:table/my-table/stream/2019-06-10T19:26:16.525
Você deve ver a saída a seguir:

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

Configure opções adicionais para personalizar como os lotes são processados e para especificar quando descartar os registros que não podem ser processados. O exemplo a seguir atualiza um mapeamento de origem de evento para enviar um registro de falha para uma fila do SQS depois de duas novas tentativas ou se os registros tiverem mais uma hora.

aws lambda update-event-source-mapping --uuid f89f8514-cdd9-4602-9e1f-01a5b77d449b \
--maximum-retry-attempts 2  --maximum-record-age-in-seconds 3600
--destination-config '{"OnFailure": {"Destination": "arn:aws:sqs:us-east-2:123456789012:dlq"}}'

Você deve ver esta saída:

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

As configurações atualizadas são aplicadas de forma assíncrona e não são refletidas na saída até que o processo seja concluído. Use o comando get-event-source-mapping para ver o status atual.

aws lambda get-event-source-mapping --uuid f89f8514-cdd9-4602-9e1f-01a5b77d449b
Você deve ver esta saída:

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

Para processar vários lotes simultaneamente, use a opção --parallelization-factor.

aws lambda update-event-source-mapping --uuid 2b733gdc-8ac3-cdf5-af3a-1827b3b11284 \
--parallelization-factor 5
Tratamento de erros
O mapeamento de origem de evento que lê registros do fluxo do DynamoDB invoca sua função de forma síncrona e tenta novamente em caso de erros. Se a função for limitada ou o serviço Lambda retornar um erro sem invocar a função, o Lambda tentará novamente até os registros expirarem ou excederem a idade máxima que você configurar no mapeamento de origem de evento.

Se a função receber os registros, mas retornar um erro, o Lambda tentará novamente até os registros do lote expirarem, excederem a idade máxima ou atingirem a cota de novas tentativas configurada. Para erros de função, também é possível configurar o mapeamento de origem de evento para dividir um lote com falha em dois lotes. A nova tentativa com lotes menores isola os registros inválidos e contorna problemas de tempo limite. A divisão de um lote não é levada em consideração na cota de novas tentativas.

Se as medidas de tratamento de erros falharem, o Lambda descartará os registros e continuará a processar os lotes do fluxo. Com as configurações padrão, isso significa que um registro inválido pode bloquear o processamento no estilhaço afetado por até one day. Para evitar isso, configure o mapeamento de origem de evento da sua função com um número razoável de novas tentativas e uma idade máxima de registro adequada ao seu caso de uso.

Para manter um registro de lotes descartados, configure um destino de evento com falha. O Lambda envia um documento para a fila ou o tópico de destino com detalhes sobre o lote.

Como configurar um destino para registros de eventos com falha

Abra a página Funções do console do Lambda.

Escolha uma função.

Em Function overview (Visão geral da função), escolha Add destination (Adicionar destino).

Em Source (Origem), escolha Stream invocation (Chamada de fluxo).

Para Stream (Fluxo), escolha um fluxo mapeado para a função.

Em Destination type (Tipo de destino), escolha o tipo de recurso que recebe o registro da invocação.

Em Destination (Destino), escolha um recurso.

Escolha Save (Salvar).

O exemplo a seguir mostra um registro de invocação de um stream do DynamoDB.

#### exemplo Registro de invocação

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

Você pode usar essas informações para recuperar os registros afetados do fluxo para solução de problemas. Os registros reais não são incluídos, portanto, você deve processar esse registro e recuperá-los do fluxo antes que eles expirem ou sejam perdidos.

### Métricas do Amazon CloudWatch

Lambda emite a métrica IteratorAge quando a sua função termina de processar um lote de registros. A métrica indica a idade do último registro no lote quando o processamento foi concluído. Se a sua função estiver processando novos eventos, você poderá usar a idade do iterador para estimar a latência entre quando um registro é adicionado e quando a função o processa.

Uma tendência crescente na idade do iterador pode indicar problemas com sua função. Para obter mais informações, consulte Trabalhar com métricas de função do AWS Lambda.

### Janelas de tempo

As funções do Lambda podem executar aplicações de processamento contínuo de streams. Um stream representa dados não vinculados que fluem continuamente por meio de sua aplicação. Para analisar as informações dessa entrada de atualização contínua, você pode vincular os registros incluídos usando uma janela definida em termos de tempo.

As invocações do Lambda são sem estado. Não é possível usá-las para processar dados ao longo de várias invocações contínuas sem um banco de dados externo. No entanto, com o Windows habilitado, você pode manter seu estado em todas as invocações. Esse estado contém o resultado agregado das mensagens previamente processadas para a janela atual. Seu estado pode ter no máximo 1 MB por fragmento. Se exceder esse tamanho, o Lambda encerra a janela antes.

### Janelas em cascata

As funções do Lambda podem agregar dados usando janelas em cascata: janelas de tempo distintas que abrem e fecham em intervalos regulares. As janelas em cascata permitem que você processe fontes de dados de streaming por meio de janelas de tempo contíguas e não sobrepostas.

Cada registro de um stream pertence a uma janela específica. Um registro é processado apenas uma vez, quando o Lambda processa a janela à qual o registro pertence. Em cada janela, você pode executar cálculos, como uma soma ou média, no nível da chave de partição dentro de um fragmento.

### Agregação e processamento

Sua função gerenciada pelo usuário é chamada tanto para agregação quanto para processamento dos resultados finais dessa agregação. O Lambda agrega todos os registros recebidos na janela. Você pode receber esses registros em vários lotes, cada um como uma invocação separada. Cada invocação recebe um estado. Você também pode processar registros e retornar um novo estado, que é passado na próxima chamada. O Lambda retorna um TimeWindowEventResponse em JSON no seguinte formato:

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

Para funções Java, recomendamos o uso de Mapa<String, String> para representar o estado.

No final da janela, a sinalização isFinalInvokeForWindow é definida como true para indicar que esse é o estado final e que está pronto para processamento. Após o processamento, a janela é concluída e sua invocação final é concluída e, em seguida, o estado é descartado.

No final da janela, o Lambda usa o processamento final para ações sobre os resultados da agregação. Seu processamento final é invocado de forma síncrona. Após a invocação bem-sucedida, sua função define os pontos de verificação no número da sequência e o processamento de streams continua. Se a invocação não for bem-sucedida, sua função do Lambda suspenderá o processamento adicional até uma chamada bem-sucedida.


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

### Configuração
Você pode configurar janelas em cascata ao criar ou atualizar um mapeamento de fonte de eventos. Para configurar uma janela em cascata, especifique a janela em segundos. O comando de exemplo da AWS Command Line Interface (AWS CLI) a seguir cria um mapeamento de fonte de eventos em streaming com uma janela em cascata de 120 segundos. A função do Lambda definida para agregação e processamento é chamada de tumbling-window-example-function.

aws lambda create-event-source-mapping --event-source-arn arn:aws:dynamodb:us-east-1:123456789012:stream/lambda-stream --function-name "arn:aws:lambda:us-east-1:123456789018:function:tumbling-window-example-function" --region us-east-1 --starting-position TRIM_HORIZON --tumbling-window-in-seconds 120
O Lambda determina os limites da janela em cascata com base no horário em que os registros foram inseridos no stream. Todos os registros têm um carimbo de data/hora aproximado disponível que o Lambda usa para determinar os limites.

As agregações de janelas em cascata não são compatíveis com refragmentação. Quando o fragmento termina, o Lambda considera a janela como fechada e os fragmentos filhos iniciam suas próprias janelas em um novo estado.

As janelas em cascata são totalmente compatíveis com as políticas maxRetryAttempts e maxRecordAge.

#### exemplo Handler.py – agregação e processamento

A função do Python a seguir demonstra como agregar e, em seguida, processar seu estado final:
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

## Gerar relatórios de falhas de itens de lote

Ao consumir e processar dados de streaming de uma fonte de eventos, o Lambda definirá checkpoints por padrão no número mais elevado na sequência de um lote somente quando o lote for um sucesso total. O Lambda trata todos os outros resultados como falha absoluta e tenta processar novamente o lote até o limite de novas tentativas. Para permitir sucessos parciais durante o processamento de lotes de um stream, ative ReportBatchItemFailures. Permitir sucessos parciais pode ajudar a reduzir o número de novas tentativas em um registro, embora não impeça totalmente a possibilidade de novas tentativas em um registro bem-sucedido.

Para ativar ReportBatchItemFailures, inclua o valor de enum ReportBatchItemFailures na lista FunctionResponseTypes. Essa lista indica quais tipos de resposta estão habilitados para sua função. Você pode configurar essa lista ao criar ou atualizar um mapeamento de fonte de eventos.

### Sintaxe do relatório

Ao configurar relatórios sobre falhas de itens de lote, a classe *StreamsEventResponse* é retornada com uma lista de falhas de itens de lote. É possível usar um objeto StreamsEventResponse para retornar o número sequencial do primeiro registro com falha no lote. Você também pode criar sua própria classe personalizada usando a sintaxe de resposta correta. A seguinte estrutura JSON mostra a sintaxe de resposta necessária:

...
{ 
  "batchItemFailures": [ 
        {
            "itemIdentifier": "<id>"
        }
    ]
}
...

### Condições de sucesso e falha

O Lambda trata um lote como um sucesso completo se você retornar qualquer um dos seguintes:

- Uma lista de batchItemFailure vazia
- Uma lista de batchItemFailure nula
- Uma EventResponse vazia
- Uma EventResponse nula

O Lambda trata um lote como uma falha absoluta se você retornar qualquer um dos seguintes:

- Uma string *itemIdentifier* vazia

- Um *itemIdentifier* nulo

- Um *itemIdentifier* com um nome de chave inválido

O Lambda faz novas tentativas após falhas com base na sua estratégia de repetição.

### Dividir um lote

Se a invocação falhar e *BisectBatchOnFunctionError* estiver ativado, o lote será dividido independentemente da configuração de *ReportBatchItemFailures*.

Quando uma resposta de sucesso parcial do lote é recebida e tanto *BisectBatchOnFunctionError* quanto *ReportBatchItemFailures* estão ativados, o lote é dividido no número de sequência retornado e o Lambda tenta novamente apenas os registros restantes.



[Artigo original](https://docs.aws.amazon.com/pt_br/lambda/latest/dg/with-ddb.html)