package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;

    Long owerId = 1L;
    String name = "Toto";

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
        ownerServiceMap.save(Owner.builder().id(owerId).lastName(name).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerServiceMap.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(owerId);
        assertEquals(owerId, owner.getId());
    }

    @Test
    void saveIDNone() {
        Owner owner2 = Owner.builder().build();
        Owner ownerSaved = ownerServiceMap.save(owner2);
        assertEquals(2L, ownerSaved.getId());
    }

    @Test
    void saveIDExist() {
        Owner owner2 = Owner.builder().id(owerId).build();
        Owner ownerSaved = ownerServiceMap.save(owner2);
        assertEquals(owerId, ownerSaved.getId());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(owerId));
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(owerId);
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner ownerFound = ownerServiceMap.findByLastName(name);
        assertNotNull(ownerFound);
        assertEquals(owerId,ownerFound.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner ownerFound = ownerServiceMap.findByLastName("foo");
        assertNull(ownerFound);
    }
}