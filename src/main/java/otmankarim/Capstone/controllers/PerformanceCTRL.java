package otmankarim.Capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otmankarim.Capstone.entities.Performance;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.payloads.PerformanceDTO;
import otmankarim.Capstone.services.PerformanceSRV;

import java.util.UUID;

@RestController
@RequestMapping("/performances")
public class PerformanceCTRL {
    @Autowired
    private PerformanceSRV performanceSRV;

    @GetMapping
    public Page<Performance> getPerformances(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String orderBy) {
        return this.performanceSRV.getPerformances(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Performance getPerformanceById(@PathVariable UUID id) {
        return this.performanceSRV.getPerformanceById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FREELANCER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Performance save(@AuthenticationPrincipal User freelancer, @RequestBody @Validated PerformanceDTO newPerformance, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.performanceSRV.save(freelancer, newPerformance);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('FREELANCER', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Performance updateCategoryById(@RequestBody @Validated PerformanceDTO updatedPerformance, @PathVariable UUID id, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.performanceSRV.updateCategoryById(updatedPerformance, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('FREELANCER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        this.performanceSRV.delete(id);
    }

}
