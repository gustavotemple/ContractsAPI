package com.api.contracts.repositories;

import com.api.contracts.entities.ServiceEntity;
import com.api.contracts.models.ServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    public Optional<ServiceEntity> findByName(String name);

    @Query(" SELECT new com.api.contracts.models.ServiceModel(" +
            "s.name) " +
            "FROM ServiceEntity s")
    public Page<ServiceModel> getAll(Pageable pageable);
}
