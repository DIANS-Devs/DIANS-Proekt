package wineverse.com.mk.Wineverse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wineverse.com.mk.Wineverse.Model.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> { }
