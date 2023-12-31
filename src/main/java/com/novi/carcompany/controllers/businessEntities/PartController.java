package com.novi.carcompany.controllers.businessEntities;

import com.novi.carcompany.dtos.businessEntities.NumberInputDto;
import com.novi.carcompany.dtos.businessEntities.PartChangeInputDto;
import com.novi.carcompany.dtos.businessEntities.PartDto;
import com.novi.carcompany.dtos.businessEntities.PartInputDto;
import com.novi.carcompany.helpers.BindingResults;
import com.novi.carcompany.services.businessEntities.PartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

        return ResponseEntity.ok(partService.getAll());
    }

    @GetMapping("/{partNumber}")
    public ResponseEntity<PartDto> getPart(@PathVariable String partNumber) {
        return ResponseEntity.ok(partService.getOne(partNumber));
    }

    @GetMapping("/findName")
    public ResponseEntity<List<PartDto>> getPartsByName(@RequestParam String name) {
        return ResponseEntity.ok(partService.getPartsByName(name));
    }

    @GetMapping("/onStock")
    public ResponseEntity<List<PartDto>> getPartsOnStock() {
        return ResponseEntity.ok(partService.getPartsOnStock());
    }

    @PostMapping
    public ResponseEntity<Object> createPart(@Valid @RequestBody PartInputDto part, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return  BindingResults.showBindingResult(bindingResult);
        } else {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + part.partNumber.toUpperCase()).toUriString());

            return ResponseEntity.created(uri).body(partService.createNew(part));
        }
    }

    @PutMapping("/{partNumber}")
    public ResponseEntity<Object> changePart(@PathVariable String partNumber, @Valid @RequestBody PartChangeInputDto part, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return BindingResults.showBindingResult(bindingResult);
        } else {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

            return ResponseEntity.created(uri).body(partService.changePart(partNumber, part));
        }
    }

    @DeleteMapping("/{partNumber}")
    public ResponseEntity<String> deletePart(@PathVariable String partNumber) {

        return ResponseEntity.ok(partService.deleteOne(partNumber));
    }

    @PutMapping("/{partNumber}/picknote")
    public ResponseEntity<Object> assignPicknoteToPart(@PathVariable String partNumber,  @Valid @RequestBody NumberInputDto picknoteNumber, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return BindingResults.showBindingResult(bindingResult);
        } else {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

            return ResponseEntity.created(uri).body(partService.assignPicknoteToPart(partNumber, picknoteNumber));
        }
    }
}
