# Java Lambda using Dynamo DB

Something like a database connection does need to be done during every invocation of a lambda function. Instead use the static initilization of a class
to only connect once and keep the connection for other invokations of the lambda.

`java-11-db-not-static` is an example of establishing a connection to dynamoDB every time the lambda function is invoked.

`java-11-db-static` is an example of establishing the connection to dynamoDB only once.

Example of using static initilizer for a database connection:

```
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static AmazonDynamoDB dynamoDB;
    private static final Regions REGION = Regions.US_EAST_2;
    private static final String DYNAMODB_TABLE_NAME = "table_name";

    static {
        dynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
    }

...

}
```