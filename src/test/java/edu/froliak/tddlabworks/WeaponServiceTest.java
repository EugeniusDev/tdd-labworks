package edu.froliak.tddlabworks;

/*
  @author eugen
  @project tdd-labworks
  @class ItemServiceTest
  @version 1.0.0
  @since 3/27/2026 - 11.02
*/

import edu.froliak.tddlabworks.model.Weapon;
import edu.froliak.tddlabworks.response.ApiResponse;
import edu.froliak.tddlabworks.response.BaseMetaData;
import edu.froliak.tddlabworks.service.WeaponService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeaponServiceTest {


    @Autowired
    private WeaponService underTest;

    List<Weapon> weapons = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
    }
    @AfterEach
    void tearsDown(){
    }


    @Test
    void whenGetAllItemsListThenSizeIs30() {
        int size = underTest.getAll().size();
        assertEquals(30, size);
    }

    @Test
    void whenItemIsPresentThenReturnAsOkApiResponse() {
        // given
        String id = "69aeefcbe5c3dbd26376b0a8";
        // when
        Weapon weapon = underTest.getById(id);
        ApiResponse<BaseMetaData, Weapon> response = underTest.getByIdAsApiResponse(id);
        //then
        assertNotNull(response);
        assertFalse(response.getData().isEmpty());
        assertNotNull(response.getData().get(0));
        assertTrue(response.getMeta().isSuccess());
        assertEquals(200, response.getMeta().getCode());
        assertNull(response.getMeta().getErrorMessage());
        assertEquals(weapon, response.getData().get(0));
    }

    @Test
    void whenItemIsNotPresentThenReturn400ApiResponseCode_404() {
        // given
        String id = "69aeefcbe5c3d";
        // when
        Weapon weapon = underTest.getById(id);
        ApiResponse<BaseMetaData, Weapon> response = underTest.getByIdAsApiResponse(id);
        //then
        assertNotNull(response);
        assertTrue(response.getData().isEmpty());
        assertFalse(response.getMeta().isSuccess());
        assertEquals(404, response.getMeta().getCode());
        assertNotNull(response.getMeta().getErrorMessage());
        assertEquals("Not found", response.getMeta().getErrorMessage());
    }
}