package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Rating;
import com.sirioitalia.api.projection.RatingProjection;
import com.sirioitalia.api.repository.RatingRepository;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Iterable<RatingProjection.FromItem> getRatings() {
        return ratingRepository.findBy();
    }

    public Iterable<Rating> getRatingsByItemId(Long itemId) {
        return ratingRepository.findByItemId(itemId);
    }

    @Transactional
    public Rating createRating(Rating ratingDetails) throws ResourceException {
        try {
            return ratingRepository.save(ratingDetails);
        } catch (Exception ex) {
            throw new ResourceException(ex.getMessage());
        }
    }


    @Transactional
    public Rating updateRating(Long ratingId, Rating ratingDetails) throws ResourceException {
        try {
            Rating ratingToUpdate = ratingRepository.findById(ratingId)
                    .orElseThrow(() -> new ResourceException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Rating not found"));

            ratingToUpdate.setRating(ratingDetails.getRating());
            ratingToUpdate.setComment(ratingDetails.getComment());

            return ratingRepository.save(ratingToUpdate);
        } catch (Exception ex) {
            throw new ResourceException(ex.getMessage());
        }
    }

    @Transactional
    public void deleteRating(Long ratingId) throws ResourceException {
        Rating ratingToDelete = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResourceException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Rating not found"));

        ratingRepository.delete(ratingToDelete);
    }

}
