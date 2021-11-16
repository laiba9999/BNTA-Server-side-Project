package com.teamname.workplaces;

import com.teamname.allotments.AllotmentService;
import com.teamname.buildings.Building;
import com.teamname.buildings.BuildingService;
import com.teamname.buildings.workplaces.Workplace;
import com.teamname.buildings.workplaces.WorkplaceDAO;
import com.teamname.buildings.workplaces.WorkplaceService;
import com.teamname.citizens.Citizen;
import com.teamname.exceptions.ResourcesNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void shouldReturnListOfWorkplaces(){
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

    @Test //C
    //Should insert Workplace via DAO
    void shouldInsertWorkplaceToDAO(){

        //No need to make the mock data
        //Given
        when(workplaceDAOMock
                .createWorkplace(new Workplace(3, "JP Office", 3, 4)))
                .thenReturn(1);

        workplaceServiceTest.createWorkplace(new Workplace(3, "JP Office", 3, 4));

        //When
        //As it has arguments we cannot use the same structure as lines 46-47
        //We must use the argumentCaptor
        ArgumentCaptor<Workplace> workplaceArgumentCaptor = ArgumentCaptor.forClass(Workplace.class);
        verify(workplaceDAOMock).createWorkplace(workplaceArgumentCaptor.capture());
        Workplace insertedWorkplace = workplaceArgumentCaptor.getValue();

        //Assert statement
        assertThat(insertedWorkplace).isEqualTo(new Workplace(3, "JP Office", 3, 4));

        verifyNoMoreInteractions(workplaceDAOMock);
    }

    @Test
    void shouldPassDownCitizenIDIfIDExists(){
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


    @Test
    void shouldThrowErrorIfIDDoesNotExist(){
//        Given
        when(workplaceDAOMock.selectWorkplaceById(2)).thenReturn((Optional.empty()));
        assertThatThrownBy(() -> workplaceServiceTest.deleteWorkplace(2))
                .hasMessage("Workplace with id: 2 is not found")
                .isInstanceOf(ResourcesNotFoundException.class);

        verify(workplaceDAOMock).selectWorkplaceById(2);

        verifyNoMoreInteractions(workplaceDAOMock);
    }






}
