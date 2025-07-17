package com.fixmate.backend.service;

import com.fixmate.backend.model.ServiceType;
import com.fixmate.backend.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceType addServiceType(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    public ServiceType updateServiceType(Long id, ServiceType serviceType) {
        ServiceType existingServiceType = serviceTypeRepository.findById(id).orElseThrow();
        existingServiceType.setName(serviceType.getName());
        existingServiceType.setDescription(serviceType.getDescription());
        return serviceTypeRepository.save(existingServiceType);
    }

    public void deleteServiceType(Long id) {
        serviceTypeRepository.deleteById(id);
    }
}
