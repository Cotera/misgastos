package com.app.misgastos.services;

import com.app.misgastos.model.AnnotacionDto;

import java.util.List;
import java.util.Optional;

public interface AnnotationService {

    void createAnotation(AnnotacionDto annotacionDto);

    Optional<AnnotacionDto> getById(Long id);

    List<AnnotacionDto> getAll();

    Long deleteById(Long id);

    /**
     * Uptade of an Annotation into Database
     * @param id id of element to update
     * @param annotacionDto new data to update
     * @return updated element
     */
    AnnotacionDto update(Long id, AnnotacionDto annotacionDto);

}
