package com.api.contracts.models;

import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

public class ContractModel {

    @Size(max = 14)
    @NotBlank(message = "{number.mandatory}")
    @ApiParam(value = "Numero do contrato", required = true)
    private String number;

    @NotBlank(message = "{date.mandatory}")
    @ApiParam(value = "Inicio vigencia", required = true, defaultValue = "25/07/2020")
    private String beginDate;

    @NotBlank(message = "{date.mandatory}")
    @ApiParam(value = "Fim vigencia", required = true, defaultValue = "25/07/2022")
    private String endDate;

    @ApiParam(value = "Nome do servico", required = true)
    private ServiceType service;

    @Size(max = 14)
    @NotBlank(message = "{cnpj.mandatory}")
    @ApiParam(value = "CNPJ cliente", required = true)
    private String cnpj;

    public ContractModel() {
    }

    public ContractModel(String number, ZonedDateTime beginDate, ZonedDateTime endDate, String service, String cnpj) {
        this.number = number;
        this.beginDate = beginDate.toString();
        this.endDate = endDate.toString();
        this.service = ServiceType.valueOf(service);
        this.cnpj = cnpj;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ServiceType getService() {
        return service;
    }

    public void setService(ServiceType service) {
        this.service = service;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}