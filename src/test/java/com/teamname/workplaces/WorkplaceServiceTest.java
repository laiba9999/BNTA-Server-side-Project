package com.teamname.workplaces;

import com.teamname.allotments.AllotmentService;
import com.teamname.buildings.Building;
import com.teamname.buildings.BuildingService;
import com.teamname.buildings.houses.House;
import com.teamname.buildings.workplaces.Workplace;
import com.teamname.buildings.workplaces.WorkplaceDAO;
import com.teamname.buildings.workplaces.WorkplaceService;
import com.teamname.citizens.Citizen;
import com.teamname.exceptions.NotModifiedException;
import com.teamname.exceptions.ResourcesNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class WorkplaceServiceTest {
    private WorkplaceDAO workplaceDAOMock;
    private BuildingService buildingService;
    private AllotmentService allotmentService;
    private WorkplaceService workplaceServiceTest;

    @BeforeEach
    void setUp(){
        workplaceDAOMock = mock(WorkplaceDAO.class);
        buildingService = mock(BuildingService.class);
        allotmentService = mock(AllotmentService.class);
        workplaceServiceTest = new WorkplaceService(workplaceDAOMock, buildingService, allotmentService);
    }

    @Test //r
    //getting all workplaces as a List of Workplaces
    void listWorkplaceShouldReturnListOfWorkplaces(){
        //Given
        //List of fake Workplaces are created
        List<Workplace> fakeWorkplaces = List.of(
                new Workplace(1,"Tesco Headquarters", 100, 2),
                new Workplace(2, "Maxwell Office", 78, 4));
        //When we call on workplaceDOAMock selectAllWorkplaces then return the fake ones we created
        when(workplaceDAOMock.selectAllWorkplace()).thenReturn(fakeWorkplaces);

        //When
        //We want to check if our getAllWorkplace in this WorkplaceServiceTest
        //is the same as the fakeWorkplaces we created
        List<Workplace> actual = workplaceServiceTest.getAllWorkplace();
        List<Workplace> expected = fakeWorkplaces;

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void selectShouldThrowErrorIfWorkplaceDoesNotExist(){
        when(workplaceDAOMock.selectWorkplaceById(1)).thenReturn((Optional.empty()));
        assertThatThrownBy(() -> workplaceServiceTest.getWorkplaceById(1))
                .hasMessage("Workplace with id: 1 is not found").isInstanceOf(ResourcesNotFoundException.class);

        verify(workplaceDAOMock).selectWorkplaceById(1);

        verifyNoMoreInteractions(workplaceDAOMock);
    }

    @Test
    void getWorkplaceByIdShouldPassDownIDIfIDExists(){
//        given
        when(workplaceDAOMock.selectWorkplaceById(1)).thenReturn(java.util.Optional.of(new Workplace(1, "HouseOne", 5, 1)));

        workplaceServiceTest.getWorkplaceById(1);

        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(workplaceDAOMock,times(2)).selectWorkplaceById(idArgumentCaptor.capture());
        Integer idOfSelectedWorkplace = idArgumentCaptor.getValue();

        assertThat(idOfSelectedWorkplace).isEqualTo(1);
    }

    @Test //C
    //Should insert Workplace via DAO
    void createWorkplaceShouldCreateWorkplaceInDAO(){

        //No need to make the mock data
        //Given
        when(workplaceDAOMock
                .createWorkplace(new Workplace(3, "JP Office", 3, 4)))
                .thenReturn(1);

        //CALL THE METHOD YOU TETS (run the test)
        workplaceServiceTest.createWorkplace(new Workplace(3, "JP Office", 3, 4));

        //When
        //As it has arguments we cannot use the same structure as lines 46-47
        //We must use the argumentCaptor
        ArgumentCaptor<Workplace> workplaceArgumentCaptor = ArgumentCaptor.forClass(Workplace.class);

        //checking if the workplace we put in correct
        verify(workplaceDAOMock).createWorkplace(workplaceArgumentCaptor.capture());
        Workplace insertedWorkplace = workplaceArgumentCaptor.getValue();

        //Assert statement
        assertThat(insertedWorkplace).isEqualTo(new Workplace(3, "JP Office", 3, 4));

        verifyNoMoreInteractions(workplaceDAOMock);
    }

    @Test //delete
    void deleteWorkplaceShouldPassDownCitizenIDIfIDExists(){
//        Given
//        Pretend id1 exists and id2 doesn't exist
        when(workplaceDAOMock.deleteWorkplace(1)).thenReturn(1);
        when(workplaceDAOMock.selectWorkplaceById(1))
                .thenReturn(java.util.Optional.of(new Workplace(3, "JP Office", 3, 4)));
//        When
        workplaceServiceTest.deleteWorkplace(1);

        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(workplaceDAOMock).deleteWorkplace(idArgumentCaptor.capture());
        Integer idOfDeletedWorkplace = idArgumentCaptor.getValue();

        assertThat(idOfDeletedWorkplace).isEqualTo(1);
    }


    @Test // delete: is the exception thrown
    void deleteWorkplaceShouldThrowErrorIfIDDoesNotExist(){
//        Given
        when(workplaceDAOMock.selectWorkplaceById(2)).thenReturn((Optional.empty()));
        assertThatThrownBy(() -> workplaceServiceTest.deleteWorkplace(2))
                .hasMessage("Workplace with id: 2 is not found")
                .isInstanceOf(ResourcesNotFoundException.class);

        verify(workplaceDAOMock).selectWorkplaceById(2);

        verifyNoMoreInteractions(workplaceDAOMock);
    }


    @Test //Create workplace: throw an error if there is already a building in the allotment id given
    void createWorkplaceShouldThrowAnException(){
        List<Building> fakeWorkplaces = List.of(
                new Workplace(1,"Tesco Headquarters", 100, 2),
                new Workplace(2, "Maxwell Office", 78, 4));

       when(buildingService.getAllBuildings()).thenReturn(fakeWorkplaces);
       when(workplaceDAOMock.createWorkplace(new Workplace(3, "JP Office", 3, 2))).thenReturn(0);

       assertThatThrownBy(() -> workplaceServiceTest.createWorkplace(new Workplace(3, "JP Office", 3, 4)))
               .hasMessage("Allotment 4 already has a building on it");


       verify(buildingService).getAllBuildings();

       verifyNoMoreInteractions(buildingService);

    }


    @Test //Update workplace: if the id exists then it is passed through Workplace service to workplaceDAO
    void updateWorkplaceShouldThrowErrorIfWorkplaceDoesNotExist() {
        // given
        Workplace updatedWorkplace = new Workplace(null, "UpdatedWorkplace", 2, null);
        when(workplaceDAOMock.selectWorkplaceById(1)).thenReturn(Optional.empty());

        // when
        // nothing to add

        // then
        assertThatThrownBy(() -> workplaceServiceTest
                .updateWorkplace(1, updatedWorkplace))
                .hasMessage("Workplace with id 1 doesn't exist!")
                .isInstanceOf(ResourcesNotFoundException.class);

        verify(workplaceDAOMock).selectWorkplaceById(1);
        verifyNoMoreInteractions(workplaceDAOMock);
    }

    @Test
    void updateWorkplaceShouldThrowErrorIfNoUpdateWouldBeMadeToWorkplace() {
        Workplace workplaceToBeUpdated = new Workplace(1, "Tesco Headquarters", 2, 3);
        Workplace updatedWorkplace = new Workplace(null, "Tesco Headquarters", 2, null);

        when(workplaceDAOMock.selectWorkplaceById(1)).thenReturn(Optional.of(workplaceToBeUpdated));

        assertThatThrownBy(() -> workplaceServiceTest.updateWorkplace(1, updatedWorkplace))
                .hasMessage("No modifications made to workplace with id 1")
                .isInstanceOf(NotModifiedException.class);

        verify(workplaceDAOMock, times(2)).selectWorkplaceById(1);
        verifyNoMoreInteractions(workplaceDAOMock);
    }

    @Test  // Not implemented yet
    void updateWorkplaceShouldThrowErrorIfThereIsNoContent() {
        // given
        Workplace workplaceToBeUpdated = new Workplace(1, "JP Office", 2, 3);
        Workplace updatedWorkplace = new Workplace(null, null, null, null);
        // new house id and allotment_id will be null as id is not taken in

        when(workplaceDAOMock.selectWorkplaceById(1)).thenReturn(Optional.of(workplaceToBeUpdated));

        // when
        // nothing to add

        // then
        assertThatThrownBy(() -> workplaceServiceTest.updateWorkplace(1, updatedWorkplace))
                .hasMessage("No content")
                .isInstanceOf(IllegalStateException.class);

        verify(workplaceDAOMock).selectWorkplaceById(1);
        verifyNoMoreInteractions(workplaceDAOMock);
    }

    @Test
    void updateHouseShouldUpdateNameIfOnlyNameIsPassedIn() {
        // when
        Workplace workplaceToBeUpdated = new Workplace(1, "OldWorkplace", 2, 3);
        Workplace updatedWorkplace = new Workplace(null, "UpdatedWorkplace", null, null);

        when(workplaceDAOMock.selectWorkplaceById(1)).thenReturn(Optional.of(workplaceToBeUpdated));
        when(workplaceDAOMock.updateWorkplace(1, updatedWorkplace)).thenReturn(1);

        // when
        workplaceServiceTest.updateWorkplace(1, updatedWorkplace);

        // then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Workplace> workplaceArgumentCaptor = ArgumentCaptor.forClass(Workplace.class);

        verify(workplaceDAOMock).updateWorkplace(idArgumentCaptor.capture(), workplaceArgumentCaptor.capture());

        Integer capturedId = idArgumentCaptor.getValue();
        Workplace capturedWorkplace = workplaceArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(1);
        assertThat(capturedWorkplace)
                .isEqualTo(new Workplace(1, "UpdatedWorkplace", 2, 3));

        verify(workplaceDAOMock, times(2)).selectWorkplaceById(1);
        verifyNoMoreInteractions(workplaceDAOMock);
    }

    @Test
    void updateWorkplaceShouldUpdateCapacityIfOnlyCapacityIsPassedIn() {
        // when
        Workplace workplaceToBeUpdated = new Workplace(1, "OldWorkplace", 2, 3);
        Workplace updatedWorkplace = new Workplace(null, null, 4, null);

        when(workplaceDAOMock.selectWorkplaceById(1)).thenReturn(Optional.of(workplaceToBeUpdated));
        when(workplaceDAOMock.updateWorkplace(1, updatedWorkplace)).thenReturn(1);

        // when
        workplaceServiceTest.updateWorkplace(1, updatedWorkplace);

        // then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Workplace> workplaceArgumentCaptor = ArgumentCaptor.forClass(Workplace.class);

        verify(workplaceDAOMock).updateWorkplace(idArgumentCaptor.capture(), workplaceArgumentCaptor.capture());

        Integer capturedId = idArgumentCaptor.getValue();
        Workplace capturedWorkplace = workplaceArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(1);
        assertThat(capturedWorkplace)
                .isEqualTo(new Workplace(1, "OldWorkplace", 4, 3));

        verify(workplaceDAOMock, times(2)).selectWorkplaceById(1);
        verifyNoMoreInteractions(workplaceDAOMock);
    }

    @Test
    void updateWorkplaceShouldUpdateNameAndCapacityIfBothArePassedIn() {
        // when
        Workplace workplaceToBeUpdated = new Workplace(1, "OldWorkplace", 2, 3);
        Workplace updatedWorkplace = new Workplace(null, "UpdatedWorkplace", 4, null);

        when(workplaceDAOMock.selectWorkplaceById(1)).thenReturn(Optional.of(workplaceToBeUpdated));
        when(workplaceDAOMock.updateWorkplace(1, updatedWorkplace)).thenReturn(1);

        // when
        workplaceServiceTest.updateWorkplace(1, updatedWorkplace);

        // then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Workplace> workplaceArgumentCaptor = ArgumentCaptor.forClass(Workplace.class);

        verify(workplaceDAOMock).updateWorkplace(idArgumentCaptor.capture(), workplaceArgumentCaptor.capture());

        Integer capturedId = idArgumentCaptor.getValue();
        Workplace capturedWorkplace = workplaceArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(1);
        assertThat(capturedWorkplace)
                .isEqualTo(new Workplace(1, "UpdatedWorkplace", 4, 3));

        verify(workplaceDAOMock, times(2)).selectWorkplaceById(1);
        verifyNoMoreInteractions(workplaceDAOMock);
    }

    @Test // Not implemented yet
    void updateWorkplaceShouldThrowErrorEmptyNameIsPassedIn() {
        // given
        Workplace workplaceToBeUpdated = new Workplace(1, "OldWorkplace", 2, 3);
        Workplace updatedWorkplace = new Workplace(null, "", null, null);
        // new house id and allotment_id will be null as id is not taken in

        when(workplaceDAOMock.selectWorkplaceById(1)).thenReturn(Optional.of(workplaceToBeUpdated));

        // when
        // nothing to add

        // then
        assertThatThrownBy(() -> workplaceServiceTest.updateWorkplace(1, updatedWorkplace))
                .hasMessage("No content")
                .isInstanceOf(IllegalStateException.class);

        verify(workplaceDAOMock).selectWorkplaceById(1);
        verifyNoMoreInteractions(workplaceDAOMock);
    }


}
