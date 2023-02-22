package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static AmazonDynamoDB dynamoDB;
    private static final Regions REGION = Regions.US_EAST_2;
    private static final String DYNAMODB_TABLE_NAME = "java-serverless-example";

    static {
        dynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();
    }

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {

        PersonRequest request = new PersonRequest();
        request.setFirstName("Bob");
        request.setLastName("Jones");
        persistData(request);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

            return response
                    .withStatusCode(200)
                    .withBody("{}");

    }

    private void persistData(PersonRequest personRequest) throws ConditionalCheckFailedException {

        Map<String, AttributeValue> attributesMap = new HashMap<>();
        attributesMap.put("id", new AttributeValue(UUID.randomUUID().toString()));
        attributesMap.put("firstName", new AttributeValue(personRequest.getFirstName()));
        attributesMap.put("lastName", new AttributeValue(personRequest.getLastName()));

        dynamoDB.putItem(DYNAMODB_TABLE_NAME, attributesMap);
    }
}
