package edu.froliak.tddlabworks;

/*
  @author eugen
  @project tdd-labworks
  @class WeaponServiceTest
  @version 1.0.0
  @since 3/27/2026 - 11.02
*/

import edu.froliak.tddlabworks.model.Weapon;
import edu.froliak.tddlabworks.repository.WeaponRepository;
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
    @Autowired
    private WeaponRepository weaponRepository;

    List<Weapon> weapons = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        List<Weapon> testWeapons = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            testWeapons.add(new Weapon(
                    "Weapon Name " + i,
                    "CODE_" + i,
                    "Description for weapon " + i
            ));
        }

        underTest.createAll(testWeapons);
    }

    @AfterEach
    void tearsDown(){
    }

    @Test
    void whenGetAllWeaponsListThenSizeIs30() {
        int size = underTest.getAll().size();
        assertEquals(30, size);
    }

    @Test
    void whenWeaponIsPresentThenReturnOkApiResponse() {
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
    void whenWeaponIsNotPresentThenReturnApiResponseCode_404() {
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

    @Test
    void whenWeaponNotExistsThenReturn404Response() {

        String id = "wrong_id";

        ApiResponse<BaseMetaData, Weapon> response =
                underTest.getByIdAsApiResponse(id);

        assertNotNull(response);
        assertFalse(response.getMeta().isSuccess());
        assertEquals(404, response.getMeta().getCode());
        assertEquals("Not found", response.getMeta().getErrorMessage());

        assertTrue(response.getData().isEmpty());
    }

    @Test
    void whenGetAllAsApiResponseThenReturnOk() {
        ApiResponse<BaseMetaData, Weapon> response = underTest.getAllAsApiResponse();

        assertNotNull(response);
        assertTrue(response.getMeta().isSuccess());
        assertEquals(200, response.getMeta().getCode());
        assertNotNull(response.getData());
    }

    void whenUpdateExistingWeaponThenReturnOk() {
        Weapon doctor = weaponRepository.findAll().get(0);
        doctor.setName("Updated");

        ApiResponse<BaseMetaData, Weapon> response = underTest.updateAsApiResponse(doctor);

        assertTrue(response.getMeta().isSuccess());
        assertEquals(200, response.getMeta().getCode());
        assertEquals("Updated", response.getData().get(0).getName());
    }

    @Test
    void whenWeaponExistsThenErrorMessageIsNull() {

        String id = "69b8508538302af2aea5d3d6";

        ApiResponse<BaseMetaData, Weapon> response =
                underTest.getByIdAsApiResponse(id);

        assertNull(response.getMeta().getErrorMessage());
    }

    @Test
    void whenUpdateWeaponThenReturnUpdatedWeapon() {

        Weapon doctor = weaponRepository.findAll().get(0);
        doctor.setName("UpdatedName");

        ApiResponse<BaseMetaData, Weapon> response =
                underTest.updateAsApiResponse(doctor);

        assertEquals("UpdatedName",
                response.getData().get(0).getName());
    }

    @Test
    void whenUpdateWeaponNotExistsThenReturn404() {

        Weapon doctor = new Weapon("999", "Ghost", "000", "none");

        ApiResponse<BaseMetaData, Weapon> response =
                underTest.updateAsApiResponse(doctor);

        assertFalse(response.getMeta().isSuccess());
        assertEquals(404, response.getMeta().getCode());
    }
}