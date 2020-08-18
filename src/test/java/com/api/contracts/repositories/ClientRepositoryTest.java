package com.api.contracts.repositories;

import com.api.contracts.entities.ClientEntity;
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
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private ClientEntity clientEntity;

    @Before
    public void setUp() {
        clientEntity = new ClientEntity();
        clientEntity.setCnpj("01234");
        clientEntity.setId(3L);
    }

    @Test
    public void findByCnpjTest() {
        Optional<ClientEntity> clientEntity = clientRepository.findByCnpj(this.clientEntity.getCnpj());

        assertTrue(clientEntity.isPresent());
        assertEquals(Long.valueOf(1L), clientEntity.get().getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveDataIntegrityViolationException() {
        clientRepository.save(clientEntity);
    }
}
