package com.fixmate.backend.controller;

import com.fixmate.backend.model.ServiceType;
import com.fixmate.backend.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @GetMapping
    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeService.getAllServiceTypes();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ServiceType addServiceType(@RequestBody ServiceType serviceType) {
        return serviceTypeService.addServiceType(serviceType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ServiceType updateServiceType(@PathVariable Long id, @RequestBody ServiceType serviceType) {
        return serviceTypeService.updateServiceType(id, serviceType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteServiceType(@PathVariable Long id) {
        serviceTypeService.deleteServiceType(id);
    }
}
