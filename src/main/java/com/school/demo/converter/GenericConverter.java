package com.school.demo.converter;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Date: 2/5/2021 Time: 11:19 AM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */

@Service
@AllArgsConstructor
public class GenericConverter {

    private final ModelMapper mapper;

    public <T, S> S convert(T param, Class<S> type) {
        return mapper.map(param, type);
    }

    public <T, S> List<S> convertList(Collection<T> param, Class<S> type) {
        return param.stream()
                .map(var -> this.convert(var, type))
                .collect(Collectors.toList());
    }

    public <T, S> Set<S> convertSet(Collection<T> param, Class<S> type) {
        return param.stream()
                .map(var -> this.convert(var, type))
                .collect(Collectors.toSet());
    }


}
