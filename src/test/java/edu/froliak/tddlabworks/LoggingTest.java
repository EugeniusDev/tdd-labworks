package edu.froliak.tddlabworks;

import edu.froliak.tddlabworks.model.Weapon;
import edu.froliak.tddlabworks.repository.WeaponRepository;
import edu.froliak.tddlabworks.request.WeaponPageRequest;
import edu.froliak.tddlabworks.response.ApiResponse;
import edu.froliak.tddlabworks.response.PaginationMetaData;
import edu.froliak.tddlabworks.service.WeaponService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoggingTest {

    @Autowired
    private WeaponService underTest;

    @Autowired
    private WeaponRepository weaponRepository;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        List<Weapon> testWeapons = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            testWeapons.add(new Weapon("Weapon " + i, "CODE_" + i, "Desc " + i));
        }
        underTest.createAll(testWeapons);
    }

    @Test
    void testLoggingOutputBeforeMethodGetById(CapturedOutput output) {
        // given
        String dynamicId = weaponRepository.findAll().get(0).getId();

        // when
        Weapon weapon = underTest.getById(dynamicId);

        // then
        assertNotNull(weapon);
        String logs = output.toString();
        assertTrue(logs.contains("Entering method:"));
        assertTrue(logs.contains("WeaponService.getById"));
        assertTrue(logs.contains(dynamicId));
    }

    @Test
    void testLoggingOutputAfterMethodGetById(CapturedOutput output) {
        // given
        Weapon expectedWeapon = weaponRepository.findAll().get(0);
        String dynamicId = expectedWeapon.getId();

        // when
        Weapon weapon = underTest.getById(dynamicId);

        // then
        assertNotNull(weapon);
        String logs = output.toString();
        assertTrue(logs.contains("WeaponService.getById"));
        assertTrue(logs.contains("completed successfully"));
        assertTrue(logs.contains(expectedWeapon.getName()));
    }

    @Test
    void testLoggingOutputBeforeMethodGetItemsPage(CapturedOutput output) {
        // given
        WeaponPageRequest request = new WeaponPageRequest(0, 5);

        // when
        ApiResponse<PaginationMetaData, Weapon> page = underTest.getWeaponsPage(request);

        // then
        assertNotNull(page);
        String logs = output.toString();
        assertTrue(logs.contains("WeaponService.getWeaponsPage"));
        assertTrue(logs.contains("0"));
        assertTrue(logs.contains("5"));
    }

    @Test
    void testLoggingWhenResultIsNull(CapturedOutput output) {
        // given
        String fakeId = "non_existing_id";

        // when
        Weapon weapon = underTest.getById(fakeId);

        // then
        assertNull(weapon);
        String logs = output.toString();
        assertTrue(logs.contains("getById"));
        assertTrue(logs.contains("completed successfully"));
        assertTrue(logs.contains("null"));
    }

    @Test
    void testLoggingArgumentsInBeforeAdvice(CapturedOutput output) {
        // given
        String dynamicId = weaponRepository.findAll().get(0).getId();

        // when
        underTest.getById(dynamicId);

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Entering method: WeaponService.getById"));
        assertTrue(logs.contains("with arguments:"));
        assertTrue(logs.contains(dynamicId));
    }

    @Test
    void testLoggingOnCreateWeapon(CapturedOutput output) {
        // given
        Weapon newWeapon = new Weapon("New Katana", "SWD-KTN2", "Sharp blade");

        // when
        Weapon savedWeapon = underTest.create(newWeapon);

        // then
        assertNotNull(savedWeapon.getId());
        String logs = output.toString();
        assertTrue(logs.contains("Entering method: WeaponService.create"));
        assertTrue(logs.contains("New Katana"));
        assertTrue(logs.contains("completed successfully"));
    }

    @Test
    void testLoggingOnDeleteWeapon(CapturedOutput output) {
        // given
        String dynamicId = weaponRepository.findAll().get(0).getId();

        // when
        underTest.delById(dynamicId);

        // then
        String logs = output.toString();
        assertTrue(logs.contains("Entering method: WeaponService.delById"));
        assertTrue(logs.contains(dynamicId));
        assertTrue(logs.contains("completed successfully"));
    }

    @Test
    void testLoggingOnGetAll(CapturedOutput output) {
        // when
        List<Weapon> allWeapons = underTest.getAll();

        // then
        assertFalse(allWeapons.isEmpty());
        String logs = output.toString();
        assertTrue(logs.contains("Entering method: WeaponService.getAll"));
        assertTrue(logs.contains("completed successfully"));
    }
}