package edu.froliak.tddlabworks;

/*
  @author eugen
  @project tdd-labworks
  @class WeaponServicePagingTest
  @version 1.0.0
  @since 4/16/2026 - 09.02
*/

import edu.froliak.tddlabworks.model.Weapon;
import edu.froliak.tddlabworks.repository.WeaponRepository;
import edu.froliak.tddlabworks.request.WeaponPageRequest;
import edu.froliak.tddlabworks.response.ApiResponse;
import edu.froliak.tddlabworks.response.PaginationMetaData;
import edu.froliak.tddlabworks.service.WeaponService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeaponServicePagingTest {


    @Autowired
    private WeaponService underTest;
    @Autowired
    private WeaponRepository weaponRepository;

    List<Weapon> items = new ArrayList<>();

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
    void whenHappyPathThenOk() {
        // given
        WeaponPageRequest request = new WeaponPageRequest(0, 5);
        List<Weapon> allSorted = weaponRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        String expectedFirstId = allSorted.get(0).getId();

        // when
        ApiResponse<PaginationMetaData, Weapon> response = underTest.getWeaponsPage(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(200, response.getMeta().getCode());
        assertTrue(response.getMeta().isSuccess());
        assertNull(response.getMeta().getErrorMessage());

        assertEquals(0, response.getMeta().getNumber());
        assertEquals(5, response.getMeta().getSize());
        assertEquals(30, response.getMeta().getTotalElements());
        assertEquals(6, response.getMeta().getTotalPages());
        assertTrue(response.getMeta().isFirst());
        assertFalse(response.getMeta().isLast());

        assertNotNull(response.getData());
        assertFalse(response.getData().isEmpty());
        assertEquals(5, response.getData().size());
        assertEquals(expectedFirstId, response.getData().get(0).getId());
    }

    @Test
    void whenSizeIs_7_AndPageIs_4_ThenIsLast_TrueAndSizeEquals_2() {
        // given
        WeaponPageRequest request = new WeaponPageRequest(4, 7);

        // when
        ApiResponse<PaginationMetaData, Weapon> response = underTest.getWeaponsPage(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(200, response.getMeta().getCode());
        assertTrue(response.getMeta().isSuccess());
        assertNull(response.getMeta().getErrorMessage());

        assertEquals(4, response.getMeta().getNumber());
        assertEquals(7, response.getMeta().getSize());
        assertEquals(30, response.getMeta().getTotalElements());
        assertEquals(5, response.getMeta().getTotalPages());
        assertFalse(response.getMeta().isFirst());
        assertTrue(response.getMeta().isLast());

        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
    }

    @Test
    void whenPageValueIsOutOfRangeThenErrorMessageHasTheWarning() {
        // given
        WeaponPageRequest request = new WeaponPageRequest(100, 5);

        // when
        ApiResponse<PaginationMetaData, Weapon> response = underTest.getWeaponsPage(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(200, response.getMeta().getCode());
        assertTrue(response.getMeta().isSuccess());
        assertNotNull(response.getMeta().getErrorMessage());
        assertEquals("Requested page is not in range", response.getMeta().getErrorMessage());

        assertTrue(response.getData().isEmpty());
    }

    @Test
    void whenPageIsMiddleThenIsFirstFalseAndIsLastFalse() {
        // given
        WeaponPageRequest request = new WeaponPageRequest(2, 5);

        // when
        ApiResponse<PaginationMetaData, Weapon> response = underTest.getWeaponsPage(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(200, response.getMeta().getCode());
        assertTrue(response.getMeta().isSuccess());
        assertNull(response.getMeta().getErrorMessage());

        assertEquals(2, response.getMeta().getNumber());
        assertEquals(5, response.getMeta().getSize());
        assertEquals(30, response.getMeta().getTotalElements());
        assertEquals(6, response.getMeta().getTotalPages());

        assertFalse(response.getMeta().isFirst());
        assertFalse(response.getMeta().isLast());

        assertNotNull(response.getData());
        assertEquals(5, response.getData().size());
    }

    @Test
    void whenSortedByIdDescThenFirstElementHasGreatestId() {
        // given
        WeaponPageRequest request = new WeaponPageRequest(0, 5);

        // when
        ApiResponse<PaginationMetaData, Weapon> response = underTest.getWeaponsPage(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getData());

        List<Weapon> weapons = response.getData();

        assertFalse(weapons.isEmpty());
        assertEquals(5, weapons.size());


        for (int i = 0; i < weapons.size() - 1; i++) {
            String currentId = weapons.get(i).getId();
            String nextId = weapons.get(i + 1).getId();

            assertTrue(currentId.compareTo(nextId) >= 0);
        }
    }


    @Test
    void whenDatabaseIsEmptyThenReturnWarningMessage() {
        // given
        underTest.deleteAll();
        WeaponPageRequest request = new WeaponPageRequest(0, 5);

        // when
        ApiResponse<PaginationMetaData, Weapon> response = underTest.getWeaponsPage(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getMeta());
        assertEquals(200, response.getMeta().getCode());
        assertTrue(response.getMeta().isSuccess());

        assertEquals("No weapons found", response.getMeta().getErrorMessage());
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void whenPageSizeIsLargerThanTotalElementsThenReturnAllInOnePage() {
        // given
        WeaponPageRequest request = new WeaponPageRequest(0, 50);

        // when
        ApiResponse<PaginationMetaData, Weapon> response = underTest.getWeaponsPage(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(0, response.getMeta().getNumber());
        assertEquals(50, response.getMeta().getSize());
        assertEquals(30, response.getMeta().getTotalElements());
        assertEquals(1, response.getMeta().getTotalPages());

        // Since there's only 1 page, it is both the first and the last page
        assertTrue(response.getMeta().isFirst());
        assertTrue(response.getMeta().isLast());

        assertEquals(30, response.getData().size());
    }

    @Test
    void whenRequestingExactlyTheLastElementThenReturnPageWithOneItem() {
        // given
        WeaponPageRequest request = new WeaponPageRequest(1, 29);

        // when
        ApiResponse<PaginationMetaData, Weapon> response = underTest.getWeaponsPage(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(1, response.getMeta().getNumber());
        assertEquals(29, response.getMeta().getSize());
        assertEquals(2, response.getMeta().getTotalPages());

        assertFalse(response.getMeta().isFirst());
        assertTrue(response.getMeta().isLast());

        assertEquals(1, response.getData().size());
    }
}