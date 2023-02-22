package com.captech;
import io.micronaut.function.aws.MicronautRequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import jakarta.inject.Inject;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClientBuilder;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final String DYNAMODB_TABLE_NAME = "java-serverless-example";
    private static DynamoDbAsyncClient client;

    static {
        client = DynamoDbAsyncClient.builder()
                .region(Region.US_EAST_2)
                .build();
    }
    @Inject
    ObjectMapper objectMapper;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {

        Map<String, AttributeValue> attributesMap = new HashMap<>();
        attributesMap.put("id", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        attributesMap.put("firstName", AttributeValue.builder().s("bob").build());
        attributesMap.put("lastName", AttributeValue.builder().s("smithy").build());

        PutItemRequest putItemRequest = PutItemRequest.builder().tableName(DYNAMODB_TABLE_NAME).item(attributesMap).build();

        PutItemResponse itemResponse = client.putItem(putItemRequest).join();

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            String json = objectMapper.writeValueAsString(response.getBody());
            response.setStatusCode(200);
            response.setBody(json);
        } catch (JsonProcessingException e) {
            response.setStatusCode(500);
        }
        return response;
    }
}
