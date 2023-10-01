package com.novi.carcompany.controllers;

import com.novi.carcompany.dtos.PartDto;
import com.novi.carcompany.services.PartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parts")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }


    @GetMapping
    public ResponseEntity<List<PartDto>> getCars() {

        return ResponseEntity.ok(partService.getParts());
    }

    @GetMapping("/{partNumber}")
    public ResponseEntity<PartDto> getPart(@PathVariable String partNumber) {
        return ResponseEntity.ok(partService.getPart(partNumber));
    }

    @GetMapping("/findName")
    public ResponseEntity<List<PartDto>> getPartsByName(@RequestParam String name) {
        return ResponseEntity.ok(partService.getPartsByName(name));
    }



}
