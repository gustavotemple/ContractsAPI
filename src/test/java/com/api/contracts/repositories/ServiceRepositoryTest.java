package com.api.contracts.repositories;

import com.api.contracts.entities.ServiceEntity;
import com.api.contracts.models.ServiceType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceRepositoryTest {

    @Autowired
    private ServiceRepository serviceRepository;

    private ServiceEntity serviceEntity;

    @Before
    public void setUp() {
        serviceEntity = new ServiceEntity();
        serviceEntity.setName(ServiceType.BACKEND.name());
        serviceEntity.setId(3L);
    }

    @Test
    public void findByNameTest() {
        Optional<ServiceEntity> serviceEntity = serviceRepository.findByName(this.serviceEntity.getName());

        assertTrue(serviceEntity.isPresent());
        assertEquals(Long.valueOf(1L), serviceEntity.get().getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveDataIntegrityViolationException() {
        serviceRepository.save(serviceEntity);
    }
}
