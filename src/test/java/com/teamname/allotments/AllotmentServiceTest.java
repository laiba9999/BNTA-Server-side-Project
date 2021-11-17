package com.teamname.allotments;

import com.teamname.exceptions.ResourcesNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AllotmentServiceTest {
    private AllotmentDAO allotmentDAOMock;
    private AllotmentService underTest;

    @BeforeEach
    void setUp() {
        allotmentDAOMock = mock(AllotmentDAO.class);
        underTest = new AllotmentService(allotmentDAOMock);
    }

//    Get Requests
    @Test
    void itShouldReturnAListOfAllTheAllotmentsWhenTheSelectAllAllotmentsMethodIsCalled(){
        List<Allotment> allotmentList = List.of(
                new Allotment(1, 1, "A"),
                new Allotment(1,1, "B"));
        when(allotmentDAOMock.selectAllAllotments()).thenReturn(allotmentList);

        List<Allotment> actual = underTest.getAllAllotments();

        assertThat(actual).isEqualTo(allotmentList);
    }

    @Test
    void itShouldReturnASingleAllotmentIfTheSelectAllotmentByIdMethodIsCalled() {
        when(allotmentDAOMock.selectAllotmentById(1))
                .thenReturn(Optional.of(new Allotment(1, 1, "A")));

        underTest.getAllotmentById(1);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(allotmentDAOMock, times(2)).selectAllotmentById(argumentCaptor.capture());
        Integer selectedAllotment = argumentCaptor.getValue();

        assertThat(selectedAllotment).isEqualTo(1);
    }

    @Test
    void itShouldReturnErrorWhenSelectAllotmentByIdMethodIsCalledWhenIdDoesNotExist() {
        when(allotmentDAOMock.selectAllotmentById(1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getAllotmentById(1))
                .hasMessage("Allotment with the id 1 not found")
                .isInstanceOf(ResourcesNotFoundException.class);
    }

//    Post Requests
    @Test
    void itShouldPassAnAllotmentToTheDAOWhenTheCreateMethodIsCalled() {

        when(allotmentDAOMock.createAllotment(new Allotment(1, 1, "A")))
                .thenReturn(1);

        underTest.createAllotment(new Allotment(1, 1, "A"));

        ArgumentCaptor<Allotment> argumentCaptor = ArgumentCaptor.forClass(Allotment.class);
        verify(allotmentDAOMock).createAllotment(argumentCaptor.capture());
        Allotment createdAllotment = argumentCaptor.getValue();

        assertThat(createdAllotment).isEqualTo(new Allotment(1, 1, "A"));
    }


}