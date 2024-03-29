package com.multidatasource.demo.sqlserver;

import com.multidatasource.demo.model.DifferentKnownObjectExample;
import com.multidatasource.demo.model.KnownObjectExample;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/sqlserver")
public class SQLServerInitializer {

    private final SQLServerEntityRepository sqlEntityRepository;
    private final EntityManager entityManager;

    public SQLServerInitializer(SQLServerEntityRepository sqlEntityRepository, EntityManager entityManager) {
        this.sqlEntityRepository = sqlEntityRepository;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void init() {
        this.sqlEntityRepository.deleteAll();
        sqlEntityRepository.save(SQLServerEntity.builder().dbIdentifier("SQLServer").unknownObject(new KnownObjectExample(1L,"No property provided")).build());
        sqlEntityRepository.save(SQLServerEntity.builder().dbIdentifier("SQLServer").unknownObject(new DifferentKnownObjectExample(1L,"provided property")).build());

    }
    @GetMapping
    public List<SQLServerEntity> getAll(@RequestParam Map<String,String> searchCriteria){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SQLServerEntity> criteriaQuery=  criteriaBuilder.createQuery(SQLServerEntity.class);
        Root<SQLServerEntity> root = criteriaQuery.from(SQLServerEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String,String> criteria : searchCriteria.entrySet()){
            predicates.add(criteriaBuilder.like(retrieveJsonAttribute(criteriaBuilder,root,"unknownObject",criteria.getKey()),"%"+criteria.getValue()+"%"));
        }
        return entityManager.createQuery(criteriaQuery.where(predicates.toArray(Predicate[]::new))).getResultList();
    }
    public Expression<String> retrieveJsonAttribute(CriteriaBuilder cb, Root<?> root, String columnName, String path) {
        return cb.function("JSON_VALUE", String.class,root.get(columnName),  cb.literal("$."+ path));
    }
}

