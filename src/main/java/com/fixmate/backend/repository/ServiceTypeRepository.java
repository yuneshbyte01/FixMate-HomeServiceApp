package com.fixmate.backend.repository;

import com.fixmate.backend.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    Optional<ServiceType> findById(Long id);
    Optional<ServiceType> findByName(String name);
}
