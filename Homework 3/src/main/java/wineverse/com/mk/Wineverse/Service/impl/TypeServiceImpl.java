package wineverse.com.mk.Wineverse.Service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Model.Type;
import wineverse.com.mk.Wineverse.Repository.TypeRepository;
import wineverse.com.mk.Wineverse.Service.TypeService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public Optional<Type> findById(Long id) {
        return typeRepository.findById(id);
    }
}