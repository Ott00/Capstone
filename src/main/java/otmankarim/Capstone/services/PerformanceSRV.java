package otmankarim.Capstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import otmankarim.Capstone.entities.Category;
import otmankarim.Capstone.entities.Performance;
import otmankarim.Capstone.entities.User;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.exceptions.NotFoundException;
import otmankarim.Capstone.payloads.PerformanceDTO;
import otmankarim.Capstone.repositories.PerformanceDAO;

import java.util.UUID;

@Service
public class PerformanceSRV {
    @Autowired
    private PerformanceDAO performanceDAO;
    @Autowired
    private CategorySRV categorySRV;
    @Autowired
    private UserSRV userSRV;

    public Page<Performance> getPerformances(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return performanceDAO.findAll(pageable);
    }

    public Page<Performance> getMyPerformances(int pageNum, int size, String orderBy, User user) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return performanceDAO.findByFreelancer(user, pageable);
    }

    public Page<Performance> getPerformancesFiltered(int pageNum, int size, String orderBy, String category, String direction) {

        if (size > 100) size = 100;

        if (category != null && direction != null) {
            Sort.Direction sortDirection;
            if ("desc".equalsIgnoreCase(direction)) {
                sortDirection = Sort.Direction.DESC;
            } else sortDirection = Sort.Direction.ASC;
            Sort sort = Sort.by(sortDirection, orderBy);
            Pageable pageable = PageRequest.of(pageNum, size, sort);
            Category categoryFound = categorySRV.findByName(category.toLowerCase());
            return performanceDAO.findByCategory(pageable, categoryFound);
        } else if (category != null) {
            return getPerformancesFilteredByCategory(pageNum, size, orderBy, category);
        } else if (direction != null) {
            return getPerformancesOrderedByPrice(pageNum, size, orderBy, direction);
        } else {
            return getPerformances(pageNum, size, orderBy); //Back to base case
        }
    }

    public Page<Performance> getPerformancesFilteredByCategory(int pageNum, int size, String orderBy, String category) {
        Category categoryFound = categorySRV.findByName(category.toLowerCase());
        if (size > 100) size = 100;

        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return performanceDAO.findByCategory(pageable, categoryFound);
    }

    public Page<Performance> getPerformancesOrderedByPrice(int pageNum, int size, String orderBy, String priceDirection) {
        if (size > 100) size = 100;

        Sort.Direction sortDirection;
        if ("desc".equalsIgnoreCase(priceDirection)) {
            sortDirection = Sort.Direction.DESC;
        } else sortDirection = Sort.Direction.ASC;

        Sort sort = Sort.by(sortDirection, orderBy);
        Pageable pageable = PageRequest.of(pageNum, size, sort);
        return performanceDAO.findAll(pageable);
    }

    public Performance save(User freelancer, PerformanceDTO newPerformance) {
        Category category = categorySRV.findByName(newPerformance.category());

        if (performanceDAO.existsByNameAndFreelancer(newPerformance.title(), freelancer)) {
            throw new BadRequestException(freelancer.getEmail() + "already have a performance named " + newPerformance.title());
        }

        Performance performance = new Performance(
                newPerformance.title(),
                newPerformance.description(),
                newPerformance.price(),
                newPerformance.location(),
                freelancer,
                category
        );
        return performanceDAO.save(performance);
    }

    public Performance getPerformanceById(UUID id) {
        return performanceDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Performance updatePerformanceById(PerformanceDTO updatedPerformance, UUID id) {
        Category category = categorySRV.findByName(updatedPerformance.category());
        Performance found = getPerformanceById(id);

        found.setTitle(updatedPerformance.title());
        found.setDescription(updatedPerformance.description());
        found.setPrice(updatedPerformance.price());
        found.setLocation(updatedPerformance.location());
        found.setCategory(category);

        return performanceDAO.save(found);
    }

    public void delete(UUID id) {
        Performance found = getPerformanceById(id);
        performanceDAO.delete(found);
    }
}
