package com.app.misgastos.web;

import com.app.misgastos.model.AnnotacionDto;
import com.app.misgastos.services.AnnotationService;
import com.app.misgastos.services.impl.AnnotationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Optional;

@Controller
@RestController("/annotation")
public class AnnotationController {
    Logger log = LoggerFactory.getLogger(AnnotationController.class);

    @Autowired
    private AnnotationServiceImpl annotationService;

    @PutMapping
    public ResponseEntity<AnnotacionDto> create(@RequestBody AnnotacionDto annotacionDto){
        log.info("Creating object with id: " + annotacionDto.getId());
        annotationService.createAnotation(annotacionDto);
        return ResponseEntity.ok(annotacionDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnotacionDto> getById(@PathParam("id") Long id){
        log.info("Searching for object with id: " + id);
        Optional<AnnotacionDto> annotacion = annotationService.getById(id);

        if (annotacion.isPresent()) {
            return ResponseEntity.ok(annotacion.get());
        } else {
            log.error("Object not found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
