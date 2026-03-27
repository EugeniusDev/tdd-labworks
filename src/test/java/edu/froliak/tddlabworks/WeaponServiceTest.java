package edu.froliak.tddlabworks;

/*
  @author eugen
  @project tdd-labworks
  @class WeaponServiceTest
  @version 1.0.0
  @since 3/27/2026 - 11.02
*/

import edu.froliak.tddlabworks.model.Weapon;
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
}