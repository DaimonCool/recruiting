package com.daimon.recruiting.candidate.repository.specification;

import com.daimon.recruiting.candidate.entity.Candidate;
import com.daimon.recruiting.candidate.entity.City;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class CandidateSpecification implements Specification<Candidate> {

    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Candidate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate;
        switch (criteria.getOperation()) {
            case EQUAL:
                predicate = criteriaBuilder.equal(getFieldPath(root), criteria.getValue());
                break;
            case LIKE:
                predicate = criteriaBuilder.like(
                        criteriaBuilder.lower(getFieldPath(root)),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"
                );
                break;
            case EQUAL_LIST:
                var values = (Set<Object>) criteria.getValue();
                var listPredicate = new ArrayList<>();
                values.forEach(value -> listPredicate.add(getFieldPath(root).in(value)));
                predicate = criteriaBuilder.and(listPredicate.toArray(Predicate[]::new));
                break;
            default:
                predicate = null;
        }
        return predicate;
    }

    private Expression<String> getFieldPath(Root<Candidate> root) {
        if (criteria.getClazz() == null) {
            return root.get(this.criteria.getKey());
        } else {
            Join<Candidate, ?> join = root.join(criteria.getClazz());
            return join.get(criteria.getKey());
        }
    }
}
