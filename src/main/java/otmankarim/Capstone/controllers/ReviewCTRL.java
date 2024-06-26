package otmankarim.Capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otmankarim.Capstone.entities.Review;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.payloads.ReviewDTO;
import otmankarim.Capstone.services.ReviewSRV;

import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewCTRL {
    @Autowired
    private ReviewSRV reviewSRV;

    @GetMapping
    public Page<Review> getReviews(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String orderBy) {
        return this.reviewSRV.getReviews(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable UUID id) {
        return this.reviewSRV.getReviewById(id);
    }

    @GetMapping("/freelancer/me")
    @PreAuthorize("hasAuthority('FREELANCER')")
    public Page<Review> getReviewsByFreelancer(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "id") String orderBy,
                                               @AuthenticationPrincipal User freelancer) {
        return this.reviewSRV.getReviewsByFreelancer(freelancer, page, size, orderBy);
    }

    @GetMapping("/client/me")
    @PreAuthorize("hasAuthority('CLIENT')")
    public Page<Review> getReviewsByClient(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String orderBy,
                                           @AuthenticationPrincipal User client) {
        return this.reviewSRV.getReviewsByClient(client, page, size, orderBy);
    }

    @GetMapping("/{id}/check")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'FREELANCER')")
    public boolean checkIfExistsReview(@AuthenticationPrincipal User client, @PathVariable UUID id) {
        return this.reviewSRV.checkIfExistsReview(id, client);
    }

    @GetMapping("/{id}/get")
    @PreAuthorize("hasAuthority('CLIENT')")
    public Review getReviewByPerformanceAndClient(@AuthenticationPrincipal User client, @PathVariable UUID id) {
        return this.reviewSRV.getReviewByPerformanceAndClient(id, client);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    @ResponseStatus(HttpStatus.CREATED)
    public Review saveReview(@AuthenticationPrincipal User client, @RequestBody @Validated ReviewDTO newReview, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.reviewSRV.save(newReview, client);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Review updateReviewById(@RequestBody @Validated ReviewDTO updatedReview, @PathVariable UUID id, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.reviewSRV.updateReviewById(updatedReview, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable UUID id) {
        this.reviewSRV.delete(id);
    }


}
