# exemplo Handler.py – return batchItemFailures[]

def handler(event, context):
    records = event.get("Records")
    curRecordSequenceNumber = "";
    
    for record in records:
        try:
            # Process your record
            curRecordSequenceNumber = record["dynamodb"]["sequenceNumber"]
        except Exception as e:
            # Return failed record's sequence number
            return {"batchItemFailures":[{"itemIdentifier": curRecordSequenceNumber}]}

    return {"batchItemFailures":[]}