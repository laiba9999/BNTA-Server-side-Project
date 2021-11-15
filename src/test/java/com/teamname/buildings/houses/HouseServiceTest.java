package com.teamname.buildings.houses;

import com.teamname.allotments.Allotment;
import com.teamname.allotments.AllotmentService;
import com.teamname.buildings.Building;
import com.teamname.buildings.BuildingService;
import com.teamname.exceptions.ResourcesNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void shouldReturnListOfCitizens(){
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
}