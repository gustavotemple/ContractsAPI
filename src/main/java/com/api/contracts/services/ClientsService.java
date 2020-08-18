package com.api.contracts.services;

import com.api.contracts.entities.ClientEntity;
import com.api.contracts.exceptions.BadRequestException;
import com.api.contracts.exceptions.DuplicateException;
import com.api.contracts.exceptions.NotFoundException;
import com.api.contracts.models.ClientModel;
import com.api.contracts.repositories.ClientRepository;
import com.api.contracts.repositories.ContractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientsService {

    private final ModelMapper mapper;

    private final ClientRepository clientRepository;

    private final ContractRepository contractRepository;

    @Autowired
    public ClientsService(ModelMapper mapper, ClientRepository clientRepository, ContractRepository contractRepository) {
        this.mapper = mapper;
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;
    }

    @Transactional(readOnly = true)
    public Page<ClientModel> getAll(Pageable pageable) {
        return clientRepository.getAll(pageable);
    }

    @Transactional
    public void removeById(Long id) {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente nao cadastrado. ID: " + id));

        if (!contractRepository.findByClient(clientEntity).isEmpty()) {
            throw new BadRequestException("Existem contratos associados ao ID: " + id);
        }

        clientRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ClientEntity getByCnpj(String cnpj) {
        return clientRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new NotFoundException("Cliente nao cadastrado. CNPJ: " + cnpj));
    }

    @Transactional
    public ClientModel save(ClientModel request) {

        if (clientRepository.findByCnpj(request.getCnpj()).isPresent()) {
            throw new DuplicateException("Cliente ja cadastrado. CNPJ: " + request.getCnpj());
        }

        return mapper.map(clientRepository.save(mapper.map(request, ClientEntity.class)), ClientModel.class);
    }

    @Transactional
    public ClientModel edit(Long id, ClientModel contractModel) {

        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente nao cadastrado. ID: " + id));

        if (clientRepository.findByCnpj(contractModel.getCnpj()).isPresent()) {
            throw new DuplicateException("Cliente ja cadastrado. CNPJ: " + contractModel.getCnpj());
        }

        clientEntity.setCnpj(contractModel.getCnpj());

        return contractModel;
    }

}
