package com.api.contracts.controllers;

import com.api.contracts.annotations.ApiPageable;
import com.api.contracts.models.ContractModel;
import com.api.contracts.services.ContractsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@Api(tags = "Contracts")
@RequestMapping(path = "/contracts")
public class ContractController {

    private final ContractsService service;

    @Autowired
    public ContractController(ContractsService service) {
        this.service = service;
    }

    @GetMapping
    @ApiPageable
    @ApiOperation(value = "Busca contratos com paginacao")
    public Page<ContractModel> getContracts(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PostMapping
    @ApiOperation(value = "Cria contrato")
    public ContractModel createContract(@Valid ContractModel contractModel) {
        return service.create(contractModel);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edita contrato")
    public ContractModel editContract(@PathVariable(value = "id") Long id, @Valid ContractModel contractModel) {
        return service.edit(id, contractModel);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove contrato")
    public void removeContract(@PathVariable(value = "id") Long id) {
        service.removeById(id);
    }
}
