package com.teamname.buildings.houses;

import com.teamname.allotments.Allotment;
import com.teamname.allotments.AllotmentService;
import com.teamname.buildings.Building;
import com.teamname.buildings.BuildingService;
import com.teamname.exceptions.NotModifiedException;
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

        verify(houseDAOMock).selectAllHouses();
        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void getHouseByIdShouldThrowErrorIfHouseDoesNotExist(){
        when(houseDAOMock.selectHouseById(1)).thenReturn((Optional.empty()));
        assertThatThrownBy(() -> houseServiceTest.getHouseById(1))
                .hasMessage("House with id 1 doesn't exist!").isInstanceOf(ResourcesNotFoundException.class);

        verify(houseDAOMock).selectHouseById(1);

        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void getHouseByIdPassDownIDIfIDExists(){
        // given
        when(houseDAOMock.selectHouseById(1))
                .thenReturn(Optional.of(new House(1, "HouseOne", 5, 1)));

        // when
        Optional<House> actual = houseServiceTest.getHouseById(1);

        // then
        assertThat(actual).isEqualTo(Optional.of(new House(1, "HouseOne", 5, 1)));

        verify(houseDAOMock, times(2)).selectHouseById(1);
        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void createHouseShouldThrowErrorIfAllotmentIsTakenByFirstHouseInDatabase(){
        // given
        List<Building> fakeBuildings = List.of(
                new House(1,"HouseOne",5,1),
                new House(2,"HouseTwo",6,2)
        );
        when(buildingServiceMock.getAllBuildings())
                .thenReturn(fakeBuildings);

        // when
        // Nothing to add

        // then
        assertThatThrownBy(() -> houseServiceTest
                .createHouse(new House(3,"HouseThree",5,1)))
                .hasMessage("Allotment 1 already has a building on it");


        verify(buildingServiceMock).getAllBuildings();
        verifyNoMoreInteractions(buildingServiceMock);

        verifyNoInteractions(houseDAOMock);
    }

    @Test
    void createHouseShouldThrowErrorIfAllotmentIsTakenBySecondHouseInDatabase(){
        // given
        List<Building> fakeBuildings = List.of(
                new House(1,"HouseOne",5,1),
                new House(2,"HouseTwo",6,2)
        );
        when(buildingServiceMock.getAllBuildings())
                .thenReturn(fakeBuildings);

        // when
        // Nothing to add

        // then
        assertThatThrownBy(() -> houseServiceTest
                .createHouse(new House(3,"HouseThree",6,2)))
                .hasMessage("Allotment 2 already has a building on it");

        verify(buildingServiceMock).getAllBuildings();
        verifyNoMoreInteractions(buildingServiceMock);

        verifyNoInteractions(houseDAOMock);
    }

    @Test
    void createHouseShouldPassDownHouseIfAllotmentIsFree(){
        // given
        List<Building> fakeBuildings = List.of(
                new House(1,"HouseOne",5,1),
                new House(2,"HouseTwo",6,2)
        );
        when(buildingServiceMock.getAllBuildings())
                .thenReturn(fakeBuildings);
        when(houseDAOMock.createHouse(new House(3,"HouseThree",5,3)))
                .thenReturn(1);

        // when
        houseServiceTest.createHouse(new House(3,"HouseOne",5,3));

        // then
        ArgumentCaptor<House> idArgumentCaptor = ArgumentCaptor.forClass(House.class);
        verify(houseDAOMock).createHouse(idArgumentCaptor.capture());
        House createdHouse = idArgumentCaptor.getValue();

        assertThat(createdHouse).isEqualTo(new House(3,"HouseOne",5,3));

        verifyNoMoreInteractions(houseDAOMock);

        verify(buildingServiceMock).getAllBuildings();
        verifyNoMoreInteractions(buildingServiceMock);
    }

    @Test
    void deleteHouseShouldThrowErrorIfIDDoesNotExist(){
        // given
        when(houseDAOMock.selectHouseById(1)).thenReturn(Optional.empty());

        // when
        // nothing to add

        // then
        assertThatThrownBy(()-> houseServiceTest.deleteHouse(1))
                .hasMessage("House with id 1 doesn't exist!")
                .isInstanceOf(ResourcesNotFoundException.class);

        verify(houseDAOMock).selectHouseById(1);
        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void deleteHouseShouldPassDownIDIfIDDoesExist(){
        // given
        when(houseDAOMock.deleteHouse(1)).thenReturn(1);
        when(houseDAOMock.selectHouseById(1))
                .thenReturn(Optional.of(new House(1, "HouseOne", 5, 3)));

        // when
        houseServiceTest.deleteHouse(1);

        // then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(houseDAOMock).deleteHouse(idArgumentCaptor.capture());
        Integer idOfDeletedHouse = idArgumentCaptor.getValue();

        assertThat(idOfDeletedHouse).isEqualTo(1);

        verify(houseDAOMock).selectHouseById(1);
        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void updateHouseShouldThrowErrorIfHouseDoesNotExist() {
        // given
        House updatedHouse = new House(null, "UpdatedHouse", 2, null);
        when(houseDAOMock.selectHouseById(1)).thenReturn(Optional.empty());

        // when
        // nothing to add

        // then
        assertThatThrownBy(() -> houseServiceTest
                .updateHouse(1, updatedHouse))
                .hasMessage("House with id 1 doesn't exist!")
                .isInstanceOf(ResourcesNotFoundException.class);

        verify(houseDAOMock).selectHouseById(1);
        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void updateHouseShouldThrowErrorIfNoUpdateWouldBeMadeToHouse() {
        // given
        House houseToBeUpdated = new House(1, "OldHouse", 2, 3);
        House updatedHouse = new House(null, "OldHouse", 2, null);
        // new house id and allotment_id will be null as id is not taken in

        when(houseDAOMock.selectHouseById(1)).thenReturn(Optional.of(houseToBeUpdated));

        // when
        // nothing to add

        // then
        assertThatThrownBy(() -> houseServiceTest.updateHouse(1, updatedHouse))
                .hasMessage("No modifications made to house with id 1")
                .isInstanceOf(NotModifiedException.class);

        verify(houseDAOMock, times(2)).selectHouseById(1);
        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test  // Not implemented yet
    void updateHouseShouldThrowErrorIfThereIsNoContent() {
        // given
        House houseToBeUpdated = new House(1, "OldHouse", 2, 3);
        House updatedHouse = new House(null, null, null, null);
        // new house id and allotment_id will be null as id is not taken in

        when(houseDAOMock.selectHouseById(1)).thenReturn(Optional.of(houseToBeUpdated));

        // when
        // nothing to add

        // then
        assertThatThrownBy(() -> houseServiceTest.updateHouse(1, updatedHouse))
                .hasMessage("No content")
                .isInstanceOf(IllegalStateException.class);

        verify(houseDAOMock).selectHouseById(1);
        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void updateHouseShouldUpdateNameIfOnlyNameIsPassedIn() {
        // when
        House houseToBeUpdated = new House(1, "OldHouse", 2, 3);
        House updatedHouse = new House(null, "UpdatedHouse", null, null);

        when(houseDAOMock.selectHouseById(1)).thenReturn(Optional.of(houseToBeUpdated));
        when(houseDAOMock.updateHouse(1, updatedHouse)).thenReturn(1);

        // when
        houseServiceTest.updateHouse(1, updatedHouse);

        // then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<House> houseArgumentCaptor = ArgumentCaptor.forClass(House.class);

        verify(houseDAOMock).updateHouse(idArgumentCaptor.capture(), houseArgumentCaptor.capture());

        Integer capturedId = idArgumentCaptor.getValue();
        House capturedHouse = houseArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(1);
        assertThat(capturedHouse)
                .isEqualTo(new House(1, "UpdatedHouse", 2, 3));

        verify(houseDAOMock, times(2)).selectHouseById(1);
        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void updateHouseShouldUpdateCapacityIfOnlyCapacityIsPassedIn() {
        // when
        House houseToBeUpdated = new House(1, "OldHouse", 2, 3);
        House updatedHouse = new House(null, null, 4, null);

        when(houseDAOMock.selectHouseById(1)).thenReturn(Optional.of(houseToBeUpdated));
        when(houseDAOMock.updateHouse(1, updatedHouse)).thenReturn(1);

        // when
        houseServiceTest.updateHouse(1, updatedHouse);

        // then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<House> houseArgumentCaptor = ArgumentCaptor.forClass(House.class);

        verify(houseDAOMock).updateHouse(idArgumentCaptor.capture(), houseArgumentCaptor.capture());

        Integer capturedId = idArgumentCaptor.getValue();
        House capturedHouse = houseArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(1);
        assertThat(capturedHouse)
                .isEqualTo(new House(1, "OldHouse", 4, 3));

        verify(houseDAOMock, times(2)).selectHouseById(1);
        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test
    void updateHouseShouldUpdateNameAndCapacityIfBothArePassedIn() {
        // when
        House houseToBeUpdated = new House(1, "OldHouse", 2, 3);
        House updatedHouse = new House(null, "UpdatedHouse", 4, null);

        when(houseDAOMock.selectHouseById(1)).thenReturn(Optional.of(houseToBeUpdated));
        when(houseDAOMock.updateHouse(1, updatedHouse)).thenReturn(1);

        // when
        houseServiceTest.updateHouse(1, updatedHouse);

        // then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<House> houseArgumentCaptor = ArgumentCaptor.forClass(House.class);

        verify(houseDAOMock).updateHouse(idArgumentCaptor.capture(), houseArgumentCaptor.capture());

        Integer capturedId = idArgumentCaptor.getValue();
        House capturedHouse = houseArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(1);
        assertThat(capturedHouse)
                .isEqualTo(new House(1, "UpdatedHouse", 4, 3));

        verify(houseDAOMock, times(2)).selectHouseById(1);
        verifyNoMoreInteractions(houseDAOMock);
    }

    @Test // Not implemented yet
    void updateHouseShouldThrowErrorEmptyNameIsPassedIn() {
        // given
        House houseToBeUpdated = new House(1, "OldHouse", 2, 3);
        House updatedHouse = new House(null, "", null, null);
        // new house id and allotment_id will be null as id is not taken in

        when(houseDAOMock.selectHouseById(1)).thenReturn(Optional.of(houseToBeUpdated));

        // when
        // nothing to add

        // then
        assertThatThrownBy(() -> houseServiceTest.updateHouse(1, updatedHouse))
                .hasMessage("No content")
                .isInstanceOf(IllegalStateException.class);

        verify(houseDAOMock).selectHouseById(1);
        verifyNoMoreInteractions(houseDAOMock);
    }
}