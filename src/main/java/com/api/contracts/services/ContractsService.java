package com.api.contracts.services;

import com.api.contracts.entities.ClientEntity;
import com.api.contracts.entities.ContractEntity;
import com.api.contracts.entities.ServiceEntity;
import com.api.contracts.exceptions.BadRequestException;
import com.api.contracts.exceptions.DuplicateException;
import com.api.contracts.exceptions.NotFoundException;
import com.api.contracts.models.ContractModel;
import com.api.contracts.repositories.ContractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class ContractsService {

    private final ModelMapper mapper;

    private final ContractRepository repository;

    private final ServicesService servicesService;

    private final ClientsService clientsService;

    @Autowired
    public ContractsService(ModelMapper mapper,
                            ContractRepository repository,
                            ServicesService servicesService,
                            ClientsService clientsService) {
        this.mapper = mapper;
        this.repository = repository;
        this.servicesService = servicesService;
        this.clientsService = clientsService;
    }

    @Transactional(readOnly = true)
    public Page<ContractModel> getAll(Pageable pageable) {
        return repository.getAll(pageable);
    }

    @Transactional
    public void removeById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Contrato nao cadastrado. ID: " + id);
        }

        repository.deleteById(id);
    }

    @Transactional
    public ContractModel edit(Long id, ContractModel contractModel) {

        ContractEntity contractEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contrato nao cadastrado. ID: " + id));

        ClientEntity clientEntity = clientsService.getByCnpj(contractModel.getCnpj());
        ServiceEntity serviceEntity = servicesService.getByName(contractModel.getService().name());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate beginDate = LocalDate.parse(contractModel.getBeginDate(), formatter);
        LocalDate endDate = LocalDate.parse(contractModel.getEndDate(), formatter);

        if (beginDate.isAfter(endDate)) {
            throw new BadRequestException("Datas incorretas.");
        }

        contractEntity.setBeginDate(beginDate.atStartOfDay(ZoneOffset.systemDefault()));
        contractEntity.setEndDate(endDate.atStartOfDay(ZoneOffset.systemDefault()));
        contractEntity.setNumber(contractModel.getNumber());
        contractEntity.setClient(clientEntity);
        contractEntity.setService(serviceEntity);

        return contractModel;
    }

    @Transactional
    public ContractModel create(ContractModel contractModel) {
        ClientEntity clientEntity = clientsService.getByCnpj(contractModel.getCnpj());
        ServiceEntity serviceEntity = servicesService.getByName(contractModel.getService().name());

        if (repository.findByNumberAndClient(contractModel.getNumber(), clientEntity).isPresent()) {
            throw new DuplicateException("Contrato ja cadastrado. Numero: " +
                    contractModel.getNumber() + " e CNPJ: " + contractModel.getCnpj());
        }

        ContractEntity contractEntity = mapper.map(contractModel, ContractEntity.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate beginDate = LocalDate.parse(contractModel.getBeginDate(), formatter);
        LocalDate endDate = LocalDate.parse(contractModel.getEndDate(), formatter);

        if (beginDate.isAfter(endDate)) {
            throw new BadRequestException("Datas incorretas.");
        }

        contractEntity.setBeginDate(beginDate.atStartOfDay(ZoneOffset.systemDefault()));
        contractEntity.setEndDate(endDate.atStartOfDay(ZoneOffset.systemDefault()));
        contractEntity.setClient(clientEntity);
        contractEntity.setService(serviceEntity);
        repository.save(contractEntity);

        return contractModel;
    }

}
