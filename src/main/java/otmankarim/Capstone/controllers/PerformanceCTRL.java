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

    @GetMapping("/filter")
    public Page<Performance> getPerformancesFiltered(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "id") String orderBy,
                                                     @RequestParam(required = false) String category,
                                                     @RequestParam(required = false) String direction) {
        return this.performanceSRV.getPerformancesFiltered(page, size, orderBy, category, direction);
    }


    @GetMapping("/me")
    @PreAuthorize("hasAuthority('FREELANCER')")
    public Page<Performance> getMyPerformances(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "id") String orderBy,
                                               @AuthenticationPrincipal User user) {
        return this.performanceSRV.getMyPerformances(page, size, orderBy, user);
    }

    @GetMapping("/{id}")
    public Performance getPerformanceById(@PathVariable UUID id) {
        return this.performanceSRV.getPerformanceById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FREELANCER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Performance savePerformance(@AuthenticationPrincipal User freelancer, @RequestBody @Validated PerformanceDTO newPerformance, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.performanceSRV.save(freelancer, newPerformance);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('FREELANCER', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Performance updatePerformanceId(@RequestBody @Validated PerformanceDTO updatedPerformance, @PathVariable UUID id, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.performanceSRV.updatePerformanceById(updatedPerformance, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('FREELANCER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerformance(@PathVariable UUID id) {
        this.performanceSRV.delete(id);
    }

}
