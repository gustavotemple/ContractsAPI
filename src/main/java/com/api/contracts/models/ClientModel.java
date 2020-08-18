package com.api.contracts.models;

import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClientModel {

    @Size(max = 14)
    @NotBlank(message = "{cnpj.mandatory}")
    @ApiParam(value = "CNPJ cliente", required = true)
    private String cnpj;

    public ClientModel() {
    }

    public ClientModel(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}