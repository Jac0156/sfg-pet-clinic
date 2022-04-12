package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    OwnerController controller;

    MockMvc mockMvc;

    Set<Owner> owners;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2l).build());

        MockitoAnnotations.openMocks(this);
        controller = new OwnerController(ownerService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

//    @Test
//    void listOwners() throws Exception {
//
//        //given
//        when(ownerService.findAll()).thenReturn(owners);
//
//        //When
//        mockMvc.perform(get("/owners"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("owners/index"))
//                .andExpect(model().attribute("owners", hasSize(2)));
//        verify(ownerService, times(1)).findAll();
//    }
//
//    @Test
//    void listOwnersByIndex() throws Exception {
//
//        //given
//        when(ownerService.findAll()).thenReturn(owners);
//
//        //When
//        mockMvc.perform(get("/owners/index"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("owners/index"))
//                .andExpect(model().attribute("owners", hasSize(2)));
//        verify(ownerService, times(1)).findAll();
//    }

    @Test
    void findOwners() throws Exception {

        //when
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        //then
        verifyNoInteractions(ownerService);
    }

    @Test
    void showOwner() throws Exception {

        //given
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        //when
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerdetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1l))));

        //then
        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString()))
                .thenReturn(Arrays.asList(Owner.builder().id(1L).build(),
                        Owner.builder().id(2L).build()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));

    }

    @Test
    void processFindFormReturnOne() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString()))
                .thenReturn(Arrays.asList(Owner.builder().id(1L).build()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

    }
}