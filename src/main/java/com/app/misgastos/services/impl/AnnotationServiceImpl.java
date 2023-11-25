package com.app.misgastos.services.impl;

import com.app.misgastos.model.AmountTypeEnum;
import com.app.misgastos.model.AnnotacionDto;
import com.app.misgastos.model.entities.AnnotationEntity;
import com.app.misgastos.repository.AnnotationRepository;
import com.app.misgastos.services.AnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnotationServiceImpl implements AnnotationService {

    @Autowired
    private AnnotationRepository annotationRepository;

    @Override
    public void createAnotation(AnnotacionDto annotacionDto) {
        AnnotationEntity annotationEntity = new AnnotationEntity();
        annotationEntity.setId(annotacionDto.getId());
        annotationEntity.setDescription(annotacionDto.getDescription());
        annotationEntity.setAmount(annotacionDto.getAmount());

        annotationEntity.setType(annotacionDto.getType().getId());

        annotationRepository.save(annotationEntity);
    }

    @Override
    public Optional<AnnotacionDto> getById(Long id) {
        Optional<AnnotationEntity> annotationEntityOpt = annotationRepository.findById(id);

        if (annotationEntityOpt.isPresent()) {
            AnnotacionDto annotacionDto = new AnnotacionDto();
            annotacionDto.setId(annotationEntityOpt.get().getId());
            annotacionDto.setDescription(annotationEntityOpt.get().getDescription());
            annotacionDto.setAmount(annotationEntityOpt.get().getAmount());
            annotacionDto.setType(
                    AmountTypeEnum.getFromId(
                            annotationEntityOpt.get().getType()
                    ));
            return Optional.of(annotacionDto);
        }

        return Optional.empty();
    }

    @Override
    public List<AnnotacionDto> getAll() {
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        return null;
    }

    @Override
    public AnnotacionDto update(Long id, AnnotacionDto annotacionDto) {
        return null;
    }
}
