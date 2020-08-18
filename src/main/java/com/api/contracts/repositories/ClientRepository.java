package com.api.contracts.repositories;

import com.api.contracts.entities.ClientEntity;
import com.api.contracts.models.ClientModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    public Optional<ClientEntity> findByCnpj(String cnpj);

    @Query(" SELECT new com.api.contracts.models.ClientModel(" +
            "c.cnpj) " +
            "FROM ClientEntity c")
    public Page<ClientModel> getAll(Pageable pageable);
}
