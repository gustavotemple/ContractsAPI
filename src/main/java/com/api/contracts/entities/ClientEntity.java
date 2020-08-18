package com.api.contracts.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients",
        uniqueConstraints = {@UniqueConstraint(name = "client_unique",
                columnNames = {"cnpj"})})
public class ClientEntity {

    @Id
    @SequenceGenerator(name = "client_seq", sequenceName = "client_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @Column(name = "client_id", nullable = false)
    private Long id;

    @Column(name = "cnpj", length = 14, nullable = false, unique = true)
    private String cnpj;

    @OneToMany(mappedBy = "client")
    private List<ContractEntity> contracts;

    public ClientEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<ContractEntity> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractEntity> contracts) {
        this.contracts = contracts;
    }
}
