package com.multidatasource.demo.postgres;

import com.multidatasource.demo.model.DifferentKnownObjectExample;
import com.multidatasource.demo.model.KnownObjectExample;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@RestController
@RequestMapping("/entities")
public class PostgresInitializer {
    private final PostgresEntityRepository entityRepository;

    @PostConstruct
    public void init() {
        entityRepository.save(PostgresEntity.builder().dbIdentifier("PostgreSQL").unknownObject(new KnownObjectExample(1L,"No property provided")).build());
        entityRepository.save(PostgresEntity.builder().dbIdentifier("PostgreSQL").unknownObject(new DifferentKnownObjectExample(1L,"provided property")).build());

    }

    @GetMapping
    public List<PostgresEntity> findAll(@RequestParam Map<String, String> searchCriteria) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        JsonPath unknownJsonPath = new JsonPath(QPostgresEntity.postgresEntity.unknownObject);
        for (Map.Entry<String, String> criteria : searchCriteria.entrySet()) {
           booleanBuilder.and(unknownJsonPath.get(criteria.getKey()).asText().containsIgnoreCase(criteria.getValue()));
        }
        return StreamSupport.stream(entityRepository.findAll(booleanBuilder.getValue()).spliterator(), false)
                .collect(Collectors.toList());

    }
}
