package com.multidatasource.demo.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDocumentRepository extends MongoRepository<MongoDocument,String> , QuerydslPredicateExecutor<MongoDocument> {
}
