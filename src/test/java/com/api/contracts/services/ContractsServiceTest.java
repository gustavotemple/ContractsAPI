package com.api.contracts.services;

import com.api.contracts.entities.ClientEntity;
import com.api.contracts.entities.ContractEntity;
import com.api.contracts.entities.ServiceEntity;
import com.api.contracts.exceptions.BadRequestException;
import com.api.contracts.exceptions.DuplicateException;
import com.api.contracts.exceptions.NotFoundException;
import com.api.contracts.models.ContractModel;
import com.api.contracts.models.ServiceType;
import com.api.contracts.repositories.ContractRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class ContractsServiceTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ServicesService servicesService;

    @Mock
    private ClientsService clientsService;

    @InjectMocks
    private ContractsService contractsService;

    private ClientEntity clientEntity;

    private ServiceEntity serviceEntity;

    private ContractModel contractModel;

    private ContractEntity contractEntity;

    private Optional<ContractEntity> contractEntityOptional;

    @Before
    public void setUp() {
        contractModel = new ContractModel();
        contractModel.setCnpj("123");
        contractModel.setService(ServiceType.BACKEND);
        contractModel.setNumber("123");
        contractModel.setBeginDate("25/07/2020");
        contractModel.setEndDate("25/07/2022");

        serviceEntity = new ServiceEntity();
        serviceEntity.setName(ServiceType.BACKEND.name());
        serviceEntity.setId(1L);

        clientEntity = new ClientEntity();
        clientEntity.setCnpj("123");
        clientEntity.setId(1L);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate beginDate = LocalDate.parse(contractModel.getBeginDate(), formatter);
        LocalDate endDate = LocalDate.parse(contractModel.getEndDate(), formatter);

        contractEntity = new ContractEntity();
        contractEntity.setNumber("123");
        contractEntity.setClient(clientEntity);
        contractEntity.setService(serviceEntity);
        contractEntity.setBeginDate(beginDate.atStartOfDay(ZoneOffset.systemDefault()));
        contractEntity.setEndDate(endDate.atStartOfDay(ZoneOffset.systemDefault()));

        contractEntityOptional = Optional.of(contractEntity);
    }

    @Test(expected = NotFoundException.class)
    public void removeNotFoundException() {
        contractsService.removeById(1L);
    }

    @Test
    public void removeSuccessful() {
        Mockito.when(contractRepository.existsById(1L)).thenReturn(true);

        contractsService.removeById(1L);
    }


    @Test(expected = BadRequestException.class)
    public void createBadRequestException() {
        Mockito.when(clientsService.getByCnpj("123")).thenReturn(clientEntity);
        Mockito.when(servicesService.getByName(ServiceType.BACKEND.name())).thenReturn(serviceEntity);

        contractModel.setBeginDate("25/07/2022");
        contractModel.setEndDate("25/07/2020");
        Mockito.when(mapper.map(contractModel, ContractEntity.class)).thenReturn(contractEntity);

        contractsService.create(contractModel);
    }

    @Test
    public void createSuccessful() {
        Mockito.when(clientsService.getByCnpj("123")).thenReturn(clientEntity);
        Mockito.when(servicesService.getByName(ServiceType.BACKEND.name())).thenReturn(serviceEntity);
        Mockito.when(contractRepository.findByNumberAndClient(anyString(), any())).thenReturn(Optional.empty());
        Mockito.when(mapper.map(contractModel, ContractEntity.class)).thenReturn(contractEntity);

        ContractModel contractModel = contractsService.create(this.contractModel);

        assertEquals(this.contractModel, contractModel);
        assertEquals(contractEntity.getNumber(), contractModel.getNumber());
    }

    @Test(expected = DuplicateException.class)
    public void createDuplicateException() {
        Mockito.when(clientsService.getByCnpj("123")).thenReturn(clientEntity);
        Mockito.when(contractRepository.findByNumberAndClient("123", clientEntity)).thenReturn(contractEntityOptional);

        contractsService.create(contractModel);
    }

    @Test(expected = NotFoundException.class)
    public void editNotFoundException() {
        contractsService.edit(1L, contractModel);
    }

    @Test
    public void editSuccessful() {
        Mockito.when(contractRepository.findById(1L)).thenReturn(contractEntityOptional);
        Mockito.when(clientsService.getByCnpj("123")).thenReturn(clientEntity);
        Mockito.when(servicesService.getByName(ServiceType.BACKEND.name())).thenReturn(serviceEntity);

        ContractModel contractModel = contractsService.edit(1L, this.contractModel);

        assertEquals(this.contractModel, contractModel);
        assertEquals(contractEntityOptional.get().getNumber(), contractModel.getNumber());
    }

}
