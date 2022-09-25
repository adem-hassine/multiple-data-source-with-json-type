package com.multidatasource.demo.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// another defined object type example for MongoDocument model
public class DifferentKnownObjectExample {
    private Long id;
    private String anotherProperty;
}
