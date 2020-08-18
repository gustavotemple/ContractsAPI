package com.api.contracts.repositories;

import com.api.contracts.entities.ClientEntity;
import com.api.contracts.entities.ContractEntity;
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
public class ContractRepositoryTest {

    @Autowired
    private ContractRepository contractRepository;

    private ClientEntity clientEntity;

    private ContractEntity contractEntity;

    @Before
    public void setUp() {
        clientEntity = new ClientEntity();
        clientEntity.setCnpj("01234");
        clientEntity.setId(1L);

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName(ServiceType.BACKEND.name());
        serviceEntity.setId(1L);

        contractEntity = new ContractEntity();
        contractEntity.setClient(clientEntity);
        contractEntity.setService(serviceEntity);
        contractEntity.setNumber("123");
        contractEntity.setId(3L);
    }

    @Test
    public void findByNumberAndClientTest() {
        Optional<ContractEntity> serviceEntity = contractRepository.findByNumberAndClient("123", clientEntity);

        assertTrue(serviceEntity.isPresent());
        assertEquals(Long.valueOf(1L), serviceEntity.get().getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveDataIntegrityViolationException() {
        contractRepository.save(contractEntity);
    }
}
