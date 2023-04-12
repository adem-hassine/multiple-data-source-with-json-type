package com.multidatasource.demo.sqlserver;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sql_server_entity")
public class SQLServerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dbIdentifier;
    @Column(columnDefinition = "nvarchar(MAX)")
    @Convert(converter = ObjectMapperJsonConverter.class)
    private Object unknownObject;
}
