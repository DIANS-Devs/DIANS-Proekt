package wineverse.com.mk.Wineverse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wineverse.com.mk.Wineverse.Model.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findFirstByType(String type);
}
