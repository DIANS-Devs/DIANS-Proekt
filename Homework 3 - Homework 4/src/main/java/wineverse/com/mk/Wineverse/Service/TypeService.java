package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.Model.Type;

import java.util.List;
import java.util.Optional;

public interface TypeService {
    List<Type> findAll();

    Optional<Type> findById(Long id);
}
