package com.multidatasource.demo.postgres;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "postgres_entity")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class PostgresEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dbIdentifier;
    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private Object unknownObject;
}
