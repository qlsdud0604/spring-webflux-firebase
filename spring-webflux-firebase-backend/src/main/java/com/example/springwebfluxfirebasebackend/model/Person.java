package com.example.springwebfluxfirebasebackend.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collectionName = "people")
public class Person {

    @DocumentId
    private String id;

    private String name;

    private String hobby;

    private Integer age;
}
