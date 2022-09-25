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
    // the reason made me interested in using mongoDB because this entity may hold different objects types when the application where purely SQL
    private Object unknownObject;
}
