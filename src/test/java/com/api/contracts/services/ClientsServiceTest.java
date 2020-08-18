package com.api.contracts.services;

import com.api.contracts.entities.ClientEntity;
import com.api.contracts.entities.ContractEntity;
import com.api.contracts.exceptions.BadRequestException;
import com.api.contracts.exceptions.DuplicateException;
import com.api.contracts.exceptions.NotFoundException;
import com.api.contracts.models.ClientModel;
import com.api.contracts.repositories.ClientRepository;
import com.api.contracts.repositories.ContractRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class ClientsServiceTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private ClientsService clientsService;

    private ClientModel clientModel;

    private ClientEntity clientEntity;

    private Optional<ClientEntity> clientEntityOptional;

    private List<ContractEntity> contracts;

    @Before
    public void setUp() {
        clientModel = new ClientModel();
        clientModel.setCnpj("123");

        clientEntity = new ClientEntity();
        clientEntity.setCnpj("123");
        clientEntity.setId(1L);

        clientEntityOptional = Optional.of(clientEntity);

        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setClient(clientEntity);

        contracts = new ArrayList<>();
        contracts.add(contractEntity);
    }

    @Test(expected = NotFoundException.class)
    public void getByCnpjNotFoundException() {
        clientsService.getByCnpj("notfound");
    }

    @Test
    public void getByCnpjSuccessful() {
        Mockito.when(clientRepository.findByCnpj("123")).thenReturn(clientEntityOptional);

        ClientEntity clientEntity = clientsService.getByCnpj("123");

        assertTrue(clientEntityOptional.isPresent());
        assertEquals(clientEntityOptional.get(), clientEntity);
        assertEquals("123", clientEntity.getCnpj());
    }

    @Test(expected = NotFoundException.class)
    public void editNotFoundException() {
        clientsService.edit(1L, clientModel);
    }

    @Test(expected = DuplicateException.class)
    public void editDuplicateException() {
        Mockito.when(clientRepository.findById(1L)).thenReturn(clientEntityOptional);
        Mockito.when(clientRepository.findByCnpj("123")).thenReturn(clientEntityOptional);

        clientsService.edit(1L, clientModel);
    }

    @Test(expected = DuplicateException.class)
    public void saveDuplicateException() {
        Mockito.when(clientRepository.findByCnpj("123")).thenReturn(clientEntityOptional);

        clientsService.save(clientModel);
    }

    @Test(expected = NotFoundException.class)
    public void removeNotFoundException() {
        clientsService.removeById(1L);
    }

    @Test
    public void removeSuccessful() {
        Mockito.when(clientRepository.findById(1L)).thenReturn(clientEntityOptional);

        clientsService.removeById(1L);
    }

    @Test(expected = BadRequestException.class)
    public void removeBadRequestException() {
        Mockito.when(clientRepository.findById(1L)).thenReturn(clientEntityOptional);
        Mockito.when(contractRepository.findByClient(clientEntity)).thenReturn(contracts);

        clientsService.removeById(1L);
    }

    @Test
    public void editSuccessful() {
        Mockito.when(clientRepository.findById(anyLong())).thenReturn(clientEntityOptional);
        Mockito.when(clientRepository.findByCnpj(anyString())).thenReturn(Optional.empty());

        ClientModel clientModel = clientsService.edit(1L, this.clientModel);

        assertEquals(this.clientModel, clientModel);
        assertTrue(clientEntityOptional.isPresent());
        assertEquals(clientEntityOptional.get().getCnpj(), clientModel.getCnpj());
    }

    @Test
    public void saveSuccessful() {
        Mockito.when(clientRepository.findByCnpj(anyString())).thenReturn(Optional.empty());
        Mockito.when(mapper.map(clientModel, ClientEntity.class)).thenReturn(clientEntity);
        Mockito.when(mapper.map(clientEntity, ClientModel.class)).thenReturn(clientModel);
        Mockito.when(clientRepository.save(clientEntity)).thenReturn(clientEntity);

        ClientModel clientModel = clientsService.save(this.clientModel);

        assertEquals(this.clientModel, clientModel);
    }

}
