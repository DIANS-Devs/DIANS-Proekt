package wineverse.com.mk.Wineverse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wineverse.com.mk.Wineverse.Model.Review;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.List;

@Repository
public interface WineryRepository extends JpaRepository<Winery, Long> {
}
