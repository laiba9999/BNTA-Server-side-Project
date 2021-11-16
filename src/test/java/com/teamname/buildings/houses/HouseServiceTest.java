package com.teamname.buildings.houses;

import com.teamname.allotments.Allotment;
import com.teamname.allotments.AllotmentService;
import com.teamname.buildings.Building;
import com.teamname.buildings.BuildingService;
import com.teamname.exceptions.ResourcesNotFoundException;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HouseServiceTest {
    private HouseDAO houseDAOMock;
    private BuildingService buildingServiceMock;
    private AllotmentService allotmentServiceMock;
    private HouseService houseServiceTest;

    @BeforeEach
    void setUp(){
        buildingServiceMock = mock(BuildingService.class);
        allotmentServiceMock = mock(AllotmentService.class);
        houseDAOMock = mock(HouseDAO.class);
        houseServiceTest = new HouseService(houseDAOMock,buildingServiceMock,allotmentServiceMock);
    }

    @Test
//    getAllHouses should return a list of House objects
    void shouldReturnListOfHouses(){
//        Given
        List<House> fakeHouses = List.of(
                new House(1,"HouseOne",5,1),
                new House(2,"HouseTwo",6,2)
        );
//        When
        when(houseDAOMock.selectAllHouses()).thenReturn(fakeHouses);
//        Then
        List<House> expected = fakeHouses;
        List<House> actual = houseServiceTest.getAllHouses();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void selectShouldThrowErrorIfHouseDoesNotExist(){
        when(houseDAOMock.selectHouseById(1)).thenReturn((Optional.empty()));
        assertThatThrownBy(() -> houseServiceTest.getHouseById(1))
                .hasMessage("House with id 1 doesn't exist!").isInstanceOf(ResourcesNotFoundException.class);

        verify(houseDAOMock).selectHouseById(1);

        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void getHouseByIdShouldPassDownIDIfIDExists(){
//        given
        when(houseDAOMock.selectHouseById(1)).thenReturn(java.util.Optional.of(new House(1, "HouseOne", 5, 1)));

        houseServiceTest.getHouseById(1);

        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(houseDAOMock,times(2)).selectHouseById(idArgumentCaptor.capture());
        Integer idOfSelectedHouse = idArgumentCaptor.getValue();

        assertThat(idOfSelectedHouse).isEqualTo(1);
    }

    @Test
    void createHouseShouldThrowErrorIfAllotmentIsTaken(){

        List<Building> fakeBuildings = List.of(
                new House(1,"HouseOne",5,1),
                new House(2,"HouseTwo",6,2)
        );
        when(buildingServiceMock.getAllBuildings())
                .thenReturn(fakeBuildings);
        when(houseDAOMock.createHouse(new House(1,"HouseOne",5,1)))
                .thenReturn(0);
        assertThatThrownBy(() -> houseServiceTest.createHouse(new House(1,"HouseOne",5,1)))
                .hasMessage("Allotment 1 already has a building on it");

        verify(houseDAOMock).createHouse(new House(1,"HouseOne",5,1));

        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void createHouseShouldPassDownHouseIfAllotmentIsFree(){
        List<Building> fakeBuildings = List.of(
                new House(1,"HouseOne",5,1),
                new House(2,"HouseTwo",6,2)
        );
        when(buildingServiceMock.getAllBuildings())
                .thenReturn(fakeBuildings);
        when(houseDAOMock.createHouse(new House(3,"HouseOne",5,3)))
                .thenReturn(1);
        when(allotmentServiceMock.getAllotmentById(3)).thenReturn(Optional.of(new Allotment(3, 1, "C")));

        houseServiceTest.createHouse(new House(3,"HouseOne",5,3));

        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(allotmentServiceMock,times(2)).getAllotmentById(idArgumentCaptor.capture());
        Integer allotmentIDOfCreatedHouse = idArgumentCaptor.getValue();

        assertThat(allotmentIDOfCreatedHouse).isEqualTo(3);

    }

    @Test
    void deleteHouseShouldThrowErrorIfIDDoesNotExist(){
        when(houseDAOMock.selectHouseById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> houseServiceTest.deleteHouse(1))
                .hasMessage("House with id 1 doesn't exist!")
                .isInstanceOf(ResourcesNotFoundException.class);

        verify(houseDAOMock).selectHouseById(1);

        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void deleteHouseShouldPassDownIDIfIDDoesExist(){
        when(houseDAOMock.deleteHouse(1)).thenReturn(1);
        when(houseDAOMock.selectHouseById(1))
                .thenReturn(Optional.of(new House(1, "HouseOne", 5, 3)));

        houseServiceTest.deleteHouse(1);

        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(houseDAOMock).deleteHouse(idArgumentCaptor.capture());
        Integer idOfDeletedHouse = idArgumentCaptor.getValue();

        assertThat(idOfDeletedHouse).isEqualTo(1);
    }



}