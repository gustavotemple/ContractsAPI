package com.api.contracts.services;

import com.api.contracts.entities.ServiceEntity;
import com.api.contracts.exceptions.BadRequestException;
import com.api.contracts.exceptions.DuplicateException;
import com.api.contracts.exceptions.NotFoundException;
import com.api.contracts.models.ServiceModel;
import com.api.contracts.repositories.ContractRepository;
import com.api.contracts.repositories.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicesService {

    private final ModelMapper mapper;

    private final ServiceRepository serviceRepository;

    private final ContractRepository contractRepository;

    @Autowired
    public ServicesService(ModelMapper mapper, ServiceRepository repository, ContractRepository contractRepository) {
        this.mapper = mapper;
        this.serviceRepository = repository;
        this.contractRepository = contractRepository;
    }

    @Transactional(readOnly = true)
    public Page<ServiceModel> getAll(Pageable pageable) {
        return serviceRepository.getAll(pageable);
    }

    @Transactional(readOnly = true)
    public ServiceEntity getByName(String name) {
        return serviceRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Servico nao cadastrado. Nome: " + name));
    }

    @Transactional
    public void removeById(Long id) {
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Servico nao cadastrado. ID: " + id));

        if (!contractRepository.findByService(serviceEntity).isEmpty()) {
            throw new BadRequestException("Existem contratos associados ao ID: " + id);
        }

        serviceRepository.deleteById(id);
    }

    @Transactional
    public ServiceModel save(ServiceModel request) {

        if (serviceRepository.findByName(request.getName().name()).isPresent()) {
            throw new DuplicateException("Servico ja cadastrado. Nome: " + request.getName());
        }

        return mapper.map(serviceRepository.save(mapper.map(request, ServiceEntity.class)), ServiceModel.class);
    }

    @Transactional
    public ServiceModel edit(Long id, ServiceModel serviceModel) {

        ServiceEntity clientEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Servico nao cadastrado. ID: " + id));

        if (serviceRepository.findByName(serviceModel.getName().name()).isPresent()) {
            throw new DuplicateException("Cliente ja cadastrado. Nome: " + serviceModel.getName().name());
        }

        clientEntity.setName(serviceModel.getName().name());

        return serviceModel;
    }

}
