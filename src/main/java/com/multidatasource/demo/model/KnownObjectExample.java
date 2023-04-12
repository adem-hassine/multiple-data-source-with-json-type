package com.multidatasource.demo.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// defined object type example for MongoDocument model
public class KnownObjectExample{
    private Long id;
    private String property;
}