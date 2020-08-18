package com.api.contracts.models;

import io.swagger.annotations.ApiParam;

public class ServiceModel {

    @ApiParam(value = "Nome do servico", required = true)
    private ServiceType name;

    public ServiceModel() {
    }

    public ServiceModel(String name) {
        this.name = ServiceType.valueOf(name);
    }

    public ServiceType getName() {
        return name;
    }

    public void setName(ServiceType name) {
        this.name = name;
    }
}