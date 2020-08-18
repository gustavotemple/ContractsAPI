package com.api.contracts.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "services",
        uniqueConstraints = {@UniqueConstraint(name = "service_unique",
                columnNames = {"name"})})
public class ServiceEntity {

    @Id
    @SequenceGenerator(name = "service_seq", sequenceName = "service_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_seq")
    @Column(name = "service_id", nullable = false)
    private Long id;

    @Column(name = "name", length = 14, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "service")
    private List<ContractEntity> contracts;

    public ServiceEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContractEntity> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractEntity> contracts) {
        this.contracts = contracts;
    }
}
