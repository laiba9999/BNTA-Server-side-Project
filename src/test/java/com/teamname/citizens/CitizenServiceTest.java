package com.teamname.citizens;
import static org.assertj.core.api.Assertions.assertThat;

import com.teamname.exceptions.ResourcesNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CitizenServiceTest {
    private CitizenDAO citizenDAOMock;
    private CitizenService citizenServiceTest;

    @BeforeEach
    void setUp() {
        citizenDAOMock = mock(CitizenDAO.class);
        citizenServiceTest = new CitizenService(citizenDAOMock);
        }

    @Test
//    GetAllCitizens returns a list of citizen objects
    void shouldReturnListOfCitizens(){
//        Given
//        List of fake citizens are created
        List<Citizen> fakeCitizens = List.of(
                new Citizen(1,"Yacine Hannane",1,2),
                new Citizen(2,"Vinh Chu",2,1));
        when(citizenDAOMock.selectAllCitizens()).thenReturn(fakeCitizens);
//        When
        List<Citizen> expected = fakeCitizens;
        List<Citizen> actual = citizenServiceTest.getAllCitizens();
//        Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
//    Add Citizen should send a Citizen object down to the DAO
    void insertShouldPassCitizenToDAO() {

        when(citizenDAOMock.insertCitizen(new Citizen(3,"Jonathan Hutchinson",3,4)))
                .thenReturn(1);

        citizenServiceTest.insertCitizen(new Citizen(3,"Jonathan Hutchinson",3,4));

        ArgumentCaptor<Citizen> citizenArgumentCaptor = ArgumentCaptor.forClass(Citizen.class);
        verify(citizenDAOMock).insertCitizen(citizenArgumentCaptor.capture());
        Citizen insertedCitizen = citizenArgumentCaptor.getValue();

        assertThat(insertedCitizen).isEqualTo(new Citizen(3,"Jonathan Hutchinson",3,4));
    }

    @Test
//    Add Citizen should send a Citizen object down to the DAO
    void updateShouldPassCitizenToDAO() {

        when(citizenDAOMock.insertCitizen(new Citizen(3,"Jonathan Hutchinson",3,4)))
                .thenReturn(1);

        citizenServiceTest.insertCitizen(new Citizen(3,"Jonathan Hutchinson",3,4));

        ArgumentCaptor<Citizen> citizenArgumentCaptor = ArgumentCaptor.forClass(Citizen.class);
        verify(citizenDAOMock).insertCitizen(citizenArgumentCaptor.capture());
        Citizen insertedCitizen = citizenArgumentCaptor.getValue();

        assertThat(insertedCitizen).isEqualTo(new Citizen(3,"Jonathan Hutchinson",3,4));
//
        verifyNoMoreInteractions(citizenDAOMock);
    }

    @Test
    void shouldPassDownCitizenIDIfIDExists(){
//        Given
//        Pretend id1 exists and id2 doesn't exist
        when(citizenDAOMock.deleteCitizen(1)).thenReturn(1);
        when(citizenDAOMock.selectCitizenById(1))
                .thenReturn(java.util.Optional.of(new Citizen(3, "Jonathan Hutchinson", 3, 4)));
//        When
        citizenServiceTest.deleteCitizen(1);

        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(citizenDAOMock).deleteCitizen(idArgumentCaptor.capture());
        Integer idOfDeletedCitizen = idArgumentCaptor.getValue();

        assertThat(idOfDeletedCitizen).isEqualTo(1);
    }

    @Test
    void shouldThrowErrorIfIDDoesNotExist(){
//        Given
        when(citizenDAOMock.selectCitizenById(2)).thenReturn((Optional.empty()));
        assertThatThrownBy(() -> citizenServiceTest.deleteCitizen(2))
                .hasMessage("Citizen with id 2 does not exist")
                .isInstanceOf(ResourcesNotFoundException.class);

        verify(citizenDAOMock).selectCitizenById(2);

        verifyNoMoreInteractions(citizenDAOMock);
    }

}