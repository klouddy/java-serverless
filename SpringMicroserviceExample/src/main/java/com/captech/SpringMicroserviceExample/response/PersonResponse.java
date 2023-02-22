package com.captech.SpringMicroserviceExample.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonResponse {
    private String firstName;
    private String lastName;
    private String title;
    private String quote;
}
