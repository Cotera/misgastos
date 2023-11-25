package com.app.misgastos.repository;

import com.app.misgastos.model.entities.AnnotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationRepository extends JpaRepository<AnnotationEntity, Long> {
}
