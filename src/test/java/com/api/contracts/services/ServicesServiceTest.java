package com.api.contracts.services;

import com.api.contracts.entities.ClientEntity;
import com.api.contracts.entities.ContractEntity;
import com.api.contracts.entities.ServiceEntity;
import com.api.contracts.exceptions.BadRequestException;
import com.api.contracts.exceptions.DuplicateException;
import com.api.contracts.exceptions.NotFoundException;
import com.api.contracts.models.ServiceModel;
import com.api.contracts.models.ServiceType;
import com.api.contracts.repositories.ContractRepository;
import com.api.contracts.repositories.ServiceRepository;
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
public class ServicesServiceTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private ServicesService servicesService;

    private ServiceModel serviceModel;

    private ServiceEntity serviceEntity;

    private Optional<ServiceEntity> serviceEntityOptional;

    private List<ContractEntity> contracts;

    @Before
    public void setUp() {
        serviceModel = new ServiceModel();
        serviceModel.setName(ServiceType.BACKEND);

        serviceEntity = new ServiceEntity();
        serviceEntity.setName(ServiceType.BACKEND.name());
        serviceEntity.setId(1L);

        serviceEntityOptional = Optional.of(serviceEntity);

        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setService(serviceEntity);

        contracts = new ArrayList<>();
        contracts.add(contractEntity);
    }

    @Test(expected = NotFoundException.class)
    public void getByNameNotFoundException() {
        servicesService.getByName("notfound");
    }

    @Test(expected = NotFoundException.class)
    public void editNotFoundException() {
        servicesService.edit(1L, serviceModel);
    }

    @Test(expected = DuplicateException.class)
    public void editDuplicateException() {
        Mockito.when(serviceRepository.findById(1L)).thenReturn(serviceEntityOptional);
        Mockito.when(serviceRepository.findByName(ServiceType.BACKEND.name())).thenReturn(serviceEntityOptional);

        servicesService.edit(1L, serviceModel);
    }

    @Test(expected = DuplicateException.class)
    public void saveDuplicateException() {
        Mockito.when(serviceRepository.findByName(ServiceType.BACKEND.name())).thenReturn(serviceEntityOptional);

        servicesService.save(serviceModel);
    }

    @Test(expected = NotFoundException.class)
    public void removeNotFoundException() {
        servicesService.removeById(1L);
    }

    @Test
    public void removeSuccessful() {
        Mockito.when(serviceRepository.findById(1L)).thenReturn(serviceEntityOptional);

        servicesService.removeById(1L);
    }


    @Test(expected = BadRequestException.class)
    public void removeBadRequestException() {
        Mockito.when(serviceRepository.findById(1L)).thenReturn(serviceEntityOptional);
        Mockito.when(contractRepository.findByService(serviceEntity)).thenReturn(contracts);

        servicesService.removeById(1L);
    }

    @Test
    public void editSuccessful() {
        Mockito.when(serviceRepository.findById(anyLong())).thenReturn(serviceEntityOptional);
        Mockito.when(serviceRepository.findByName(anyString())).thenReturn(Optional.empty());

        ServiceModel serviceModel = servicesService.edit(1L, this.serviceModel);

        assertEquals(this.serviceModel, serviceModel);
        assertTrue(serviceEntityOptional.isPresent());
        assertEquals(serviceEntityOptional.get().getName(), serviceModel.getName().name());
    }

    @Test
    public void saveSuccessful() {
        Mockito.when(serviceRepository.findByName(anyString())).thenReturn(Optional.empty());
        Mockito.when(mapper.map(serviceModel, ServiceEntity.class)).thenReturn(serviceEntity);
        Mockito.when(mapper.map(serviceEntity, ServiceModel.class)).thenReturn(serviceModel);
        Mockito.when(serviceRepository.save(serviceEntity)).thenReturn(serviceEntity);

        ServiceModel serviceModel = servicesService.save(this.serviceModel);

        assertEquals(this.serviceModel, serviceModel);
    }

    @Test
    public void getByNameSuccessful() {
        Mockito.when(serviceRepository.findByName(ServiceType.BACKEND.name())).thenReturn(serviceEntityOptional);

        ServiceEntity serviceEntity = servicesService.getByName(ServiceType.BACKEND.name());

        assertTrue(serviceEntityOptional.isPresent());
        assertEquals(serviceEntityOptional.get(), serviceEntity);
        assertEquals(ServiceType.BACKEND, ServiceType.valueOf(serviceEntity.getName()));
    }

}
