package com.api.contracts.entities;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "contracts",
        uniqueConstraints = {@UniqueConstraint(name = "contract_unique",
                columnNames = {"number", "client_id"})})
public class ContractEntity {

    @Id
    @SequenceGenerator(name = "contract_seq", sequenceName = "contract_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_seq")
    @Column(name = "contract_id", nullable = false)
    private Long id;

    @Column(name = "number", length = 14, nullable = false)
    private String number;

    @Column(name = "begin_date", nullable = false)
    private ZonedDateTime beginDate;

    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    public ContractEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ZonedDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(ZonedDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
}
