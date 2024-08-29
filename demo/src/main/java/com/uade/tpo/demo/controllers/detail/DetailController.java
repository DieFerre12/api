package com.uade.tpo.demo.controllers.detail;

import com.uade.tpo.demo.entity.Detail;
import com.uade.tpo.demo.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/details")
public class DetailController {

    @Autowired
    private DetailService detailService;

    // Obtener un detalle por ID
    @GetMapping("/{detailId}")
    public ResponseEntity<Detail> getDetailById(@PathVariable Long detailId) {
        Optional<Detail> detail = detailService.getDetailById(detailId);
        return detail.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // Crear un nuevo detalle
    @PostMapping
    public ResponseEntity<Detail> createDetail(@RequestBody Detail detail) {
        Detail newDetail = detailService.createDetail(detail);
        return ResponseEntity.created(URI.create("/details/" + newDetail.getId())).body(newDetail);
    }

    // Actualizar un detalle existente
    @PutMapping("/{id}")
    public ResponseEntity<Detail> updateDetail(@PathVariable Long detailId, @RequestBody Detail detail) {
        Optional<Detail> updatedDetail = detailService.updateDetail(detailId, detail);
        return updatedDetail.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // Eliminar un detalle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetail(@PathVariable Long detailId) {
        detailService.deleteDetail(detailId);
        return ResponseEntity.noContent().build();
    }
}
