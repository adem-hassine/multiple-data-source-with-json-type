package com.multidatasource.demo.postgres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class PostgresEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String dbIdentifier;
}
