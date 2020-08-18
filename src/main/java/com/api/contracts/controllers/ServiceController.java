package com.api.contracts.controllers;

import com.api.contracts.annotations.ApiPageable;
import com.api.contracts.models.ServiceModel;
import com.api.contracts.services.ServicesService;
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
@Api(tags = "Services")
@RequestMapping(path = "/services")
public class ServiceController {

    private final ServicesService service;

    @Autowired
    public ServiceController(ServicesService service) {
        this.service = service;
    }

    @GetMapping
    @ApiPageable
    @ApiOperation(value = "Busca servicos com paginacao")
    public Page<ServiceModel> getServices(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PostMapping
    @ApiOperation(value = "Cria servico")
    public ServiceModel createService(@Valid ServiceModel contractModel) {
        return service.save(contractModel);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edita servico")
    public ServiceModel editService(@PathVariable(value = "id") Long id, @Valid ServiceModel serviceModel) {
        return service.edit(id, serviceModel);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove servico")
    public void removeService(@PathVariable(value = "id") Long id) {
        service.removeById(id);
    }

}
