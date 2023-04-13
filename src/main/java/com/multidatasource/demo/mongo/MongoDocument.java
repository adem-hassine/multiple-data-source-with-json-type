package com.multidatasource.demo.mongo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class MongoDocument {
    @Id
    @GeneratedValue
    private String id;
    private String dbIdentifier;
    private Object unknownObject;
}
