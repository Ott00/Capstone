package otmankarim.Capstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import otmankarim.Capstone.entities.Performance;
import otmankarim.Capstone.entities.Review;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.exceptions.NotFoundException;
import otmankarim.Capstone.payloads.ReviewDTO;
import otmankarim.Capstone.repositories.ReviewDAO;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ReviewSRV {
    @Autowired
    private ReviewDAO reviewDAO;

    @Autowired
    private PerformanceSRV performanceSRV;

    public Page<Review> getReviews(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return reviewDAO.findAll(pageable);
    }

    public Review save(ReviewDTO newReview, User client) {
        Performance performance = performanceSRV.getPerformanceById(newReview.performance_id());
        if (reviewDAO.existsReviewForSamePerformance(performance, client)) {
            throw new BadRequestException(client.getEmail() + " already made a review for this performance: " + performance.getTitle());
        }
        Review review = new Review(
                newReview.evaluation(),
                newReview.title(),
                newReview.comment(),
                performance,
                client
        );
        return reviewDAO.save(review);
    }

    public boolean checkIfExistsReview(UUID performanceId, User client) {
        Performance performance = performanceSRV.getPerformanceById(performanceId);
        return reviewDAO.existsReviewForSamePerformance(performance, client);
    }

    public Review getReviewByPerformanceAndClient(UUID performanceId, User client) {
        Performance performance = performanceSRV.getPerformanceById(performanceId);
        return reviewDAO.findReviewByPerformanceAndClient(performance, client);
    }

    public Review getReviewById(UUID id) {
        return reviewDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Review updateReviewById(ReviewDTO updatedReview, UUID id) {
        Review found = getReviewById(id);
        found.setTitle(updatedReview.title());
        found.setComment(updatedReview.comment());
        found.setEvaluation(updatedReview.evaluation());
        found.setLastEditDate(LocalDate.now());
        found.setEdited(true);
        return reviewDAO.save(found);
    }

    public void delete(UUID id) {
        Review found = getReviewById(id);
        reviewDAO.delete(found);
    }
}
