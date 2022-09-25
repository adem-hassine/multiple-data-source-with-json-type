package com.multidatasource.demo.postgres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class PostgresInitializer {
    private final EntityRepository entityRepository;
    // works properly in separated datasource , interested more with mongoDB
    @PostConstruct
    public void init(){
        entityRepository.save(PostgresEntity.builder().dbIdentifier("PostgreSQL").build());
    }
}
