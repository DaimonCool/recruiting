package com.daimon.recruiting.candidate.repository.specification;

import com.daimon.recruiting.candidate.entity.Candidate;
import com.daimon.recruiting.candidate.enums.FilterOperation;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CandidateSpecificationBuilder {

    private final List<SearchCriteria> criterias;

    public CandidateSpecificationBuilder() {
        criterias = new ArrayList<>();
    }

    public CandidateSpecificationBuilder with(String key, FilterOperation operation, Object value) {
        if (value != null) {
            criterias.add(new SearchCriteria(key, operation, value));
        }
        return this;
    }

    public CandidateSpecificationBuilder with(String clazz, String key, FilterOperation operation, Object value) {
        if (value != null) {
            criterias.add(new SearchCriteria(clazz, key, operation, value));
        }
        return this;
    }

    public Optional<Specification<Candidate>> build() {
        if (!criterias.isEmpty()) {
            Specification<Candidate> result;
            var specs = criterias.stream()
                    .map(CandidateSpecification::new)
                    .collect(Collectors.toList());

            result = specs.get(0);
            for (int i = 1; i < criterias.size(); i++) {
                result = Specification.where(result).and(specs.get(i));
            }
            return Optional.of(result);
        }
        return Optional.empty();
    }
}
