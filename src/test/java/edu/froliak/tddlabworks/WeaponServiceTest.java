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

    // 1
    @Test
    void whenWeaponIsPresentThenReturnOkApiResponse() {
        Weapon existing = weaponRepository.findAll().get(0);
        ApiResponse<BaseMetaData, Weapon> response = underTest.getByIdAsApiResponse(existing.getId());

        assertNotNull(response);
        assertEquals(200, response.getMeta().getCode());
        assertEquals(existing.getName(), response.getData().get(0).getName());
    }

    // 2
    @Test
    void whenWeaponIsNotPresentThenReturn404() {
        ApiResponse<BaseMetaData, Weapon> response = underTest.getByIdAsApiResponse("non_existent_id");

        assertEquals(404, response.getMeta().getCode());
        assertFalse(response.getMeta().isSuccess());
        assertEquals("Not found", response.getMeta().getErrorMessage());
    }

    // 3
    @Test
    void whenGetAllAsApiResponseThenReturnOk() {
        ApiResponse<BaseMetaData, Weapon> response = underTest.getAllAsApiResponse();

        assertTrue(response.getMeta().isSuccess());
        assertEquals(30, response.getData().size());
    }

    // 4
    @Test
    void whenUpdateExistingWeaponThenReturnOk() {
        Weapon weapon = weaponRepository.findAll().get(0);
        weapon.setName("Spear");

        ApiResponse<BaseMetaData, Weapon> response = underTest.updateAsApiResponse(weapon);

        assertEquals(200, response.getMeta().getCode());
        assertEquals("Spear", response.getData().get(0).getName());
    }

    // 5
    @Test
    void whenUpdateNonExistentThenReturn404() {
        Weapon ghost = new Weapon("Megatron", "666", "Cool stuff");
        ghost.setId("chynazes");

        ApiResponse<BaseMetaData, Weapon> response = underTest.updateAsApiResponse(ghost);

        assertEquals(404, response.getMeta().getCode());
        assertFalse(response.getMeta().isSuccess());
    }

    // 6
    @Test
    void whenSuccessThenErrorMessageIsNull() {
        ApiResponse<BaseMetaData, Weapon> response = underTest.getAllAsApiResponse();
        assertNull(response.getMeta().getErrorMessage());
    }

    // 7
    @Test
    void whenNotFoundDataListIsNotNull() {
        ApiResponse<BaseMetaData, Weapon> response = underTest.getByIdAsApiResponse("wrong");
        assertNotNull(response.getData());
        // В ApiResponse конструктор для помилок повинен ініціалізувати список
    }

    // 8
    @Test
    void checkMetaDataConsistency() {
        BaseMetaData meta = new BaseMetaData(200, true);
        assertEquals(200, meta.getCode());
        assertTrue(meta.isSuccess());
    }

    // 9
    @Test
    void whenDeleteThenGetByIdReturns404() {
        Weapon weapon = weaponRepository.findAll().get(0);
        underTest.delById(weapon.getId());

        ApiResponse<BaseMetaData, Weapon> response = underTest.getByIdAsApiResponse(weapon.getId());

        assertEquals(404, response.getMeta().getCode());
    }
}