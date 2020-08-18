package com.api.contracts.controllers;

import com.api.contracts.annotations.ApiPageable;
import com.api.contracts.models.ClientModel;
import com.api.contracts.services.ClientsService;
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
@Api(tags = "Clients")
@RequestMapping(path = "/clients")
public class ClientController {

    private final ClientsService service;

    @Autowired
    public ClientController(ClientsService service) {
        this.service = service;
    }

    @GetMapping
    @ApiPageable
    @ApiOperation(value = "Busca clientes com paginacao")
    public Page<ClientModel> getClients(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PostMapping
    @ApiOperation(value = "Cria cliente")
    public ClientModel createClient(@Valid ClientModel contractModel) {
        return service.save(contractModel);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edita cliente")
    public ClientModel editClient(@PathVariable(value = "id") Long id, @Valid ClientModel clientModel) {
        return service.edit(id, clientModel);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove cliente")
    public void removeClient(@PathVariable(value = "id") Long id) {
        service.removeById(id);
    }

}
