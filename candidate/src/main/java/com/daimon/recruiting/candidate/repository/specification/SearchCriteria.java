package com.daimon.recruiting.candidate.repository.specification;

import com.daimon.recruiting.candidate.enums.FilterOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria implements Serializable {

    private static final long serialVersionUID = -9018966759165876775L;

    private String clazz;
    private String key;
    private FilterOperation operation;
    private Object value;

    public SearchCriteria(String key,
                          FilterOperation operation,
                          Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
