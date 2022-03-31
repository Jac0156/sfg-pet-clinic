package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerServiceSDJpaTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerServiceSDJpa serviceSDJpa;

    String ownerName = "Smith";
    Long ownerId = 1L;
    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(ownerId).lastName(ownerName).build();
    }

    @Test
    void findAll() {

        //given
        Set<Owner> returnOwnerSet = new HashSet<>();
        returnOwnerSet.add(Owner.builder().id(1L).build());
        returnOwnerSet.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(returnOwnerSet);

        //when
        Set<Owner> owners = serviceSDJpa.findAll();

        //then
        assertNotNull(owners);
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {

        //given
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));

        //when
        Owner owner = serviceSDJpa.findById(ownerId);

        //then
        assertNotNull(owner);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void findByIdNotFound() {

        //given
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Owner owner = serviceSDJpa.findById(ownerId);

        //then
        assertNull(owner);
    }

    @Test
    void save() {

        //given
        when(ownerRepository.save(any())).thenReturn(returnOwner);

        //when
        Owner owner = serviceSDJpa.save(returnOwner);

        //then
        assertNotNull(owner);
        verify(ownerRepository).save(any());

    }

    @Test
    void delete() {

        //given

        //when
        serviceSDJpa.delete(returnOwner);

        //then
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {

        // given

        //when
        serviceSDJpa.deleteById(ownerId);

        //then
        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        //given
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        //when
        Owner ownerFound = serviceSDJpa.findByLastName(ownerName);

        //then
        assertNotNull(ownerFound);
        assertEquals(ownerName, ownerFound.getLastName());
        verify(ownerRepository).findByLastName(any());
}

    @Test
    void findByLastNameNotFound() {

        //given

        //when
        Owner ownerFound = serviceSDJpa.findByLastName("foo");

        //then
        assertNull(ownerFound);
    }
}