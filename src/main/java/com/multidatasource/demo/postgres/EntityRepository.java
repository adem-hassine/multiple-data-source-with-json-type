package com.multidatasource.demo.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<PostgresEntity,Long>, QuerydslPredicateExecutor<PostgresEntity> {
}
