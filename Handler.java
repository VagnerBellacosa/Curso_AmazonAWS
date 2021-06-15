// exemplo Handler.java – return new StreamsEventResponse()

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProcessDynamodbRecords implements RequestHandler<DynamodbEvent, Serializable> {

    @Override
    public Serializable handleRequest(DynamodbEvent input, Context context) {

        List<StreamsEventResponse.BatchItemFailure> batchItemFailures = new ArrayList<*>();
        String curRecordSequenceNumber = "";

        for (DynamodbEvent.DynamodbEventRecord dynamodbEventRecord : input.getRecords()) {
            try {
                //Process your record
                DynamodbEvent.Record dynamodbRecord = dynamodbEventRecord.getDynamodb();
                curRecordSequenceNumber = dynamodbRecord.getSequenceNumber();

            } catch (Exception e) {
                //Return failed record's sequence number
                batchItemFailures.add(new StreamsEventResponse.BatchItemFailure(curRecordSequenceNumber));
                    return new StreamsEventResponse(batchItemFailures);
            }
        }
       
       return new StreamsEventResponse(batchItemFailures);   
    }
}
