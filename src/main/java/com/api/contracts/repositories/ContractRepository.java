package com.api.contracts.repositories;

import com.api.contracts.entities.ClientEntity;
import com.api.contracts.entities.ContractEntity;
import com.api.contracts.entities.ServiceEntity;
import com.api.contracts.models.ContractModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Long> {

    public List<ContractEntity> findByClient(ClientEntity clientEntity);

    public List<ContractEntity> findByService(ServiceEntity serviceEntity);

    public Optional<ContractEntity> findByNumberAndClient(String number, ClientEntity clientEntity);

    @Query("SELECT new com.api.contracts.models.ContractModel(" +
            "c.number, " +
            "c.beginDate, " +
            "c.endDate, " +
            "c.service.name, " +
            "c.client.cnpj) " +
            "FROM ContractEntity c")
    public Page<ContractModel> getAll(Pageable pageable);
}
