package edu.froliak.tddlabworks;

/*
  @author eugen
  @project tdd-labworks
  @class ItemServiceTest
  @version 1.0.0
  @since 3/27/2026 - 11.02
*/

import edu.froliak.tddlabworks.model.Item;
import edu.froliak.tddlabworks.response.ApiResponse;
import edu.froliak.tddlabworks.response.BaseMetaData;
import edu.froliak.tddlabworks.service.ItemService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemServiceTest {


    @Autowired
    private ItemService underTest;

    List<Item> items = new ArrayList<>();

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
        Item item = underTest.getById(id);
        ApiResponse<BaseMetaData, Item> response = underTest.getByIdAsApiResponse(id);
        //then
        assertNotNull(response);
        assertFalse(response.getData().isEmpty());
        assertNotNull(response.getData().get(0));
        assertTrue(response.getMeta().isSuccess());
        assertEquals(200, response.getMeta().getCode());
        assertNull(response.getMeta().getErrorMessage());
        assertEquals(item, response.getData().get(0));
    }

    @Test
    void whenItemIsNotPresentThenReturn400ApiResponseCode_404() {
        // given
        String id = "69aeefcbe5c3d";
        // when
        Item item = underTest.getById(id);
        ApiResponse<BaseMetaData, Item> response = underTest.getByIdAsApiResponse(id);
        //then
        assertNotNull(response);
        assertTrue(response.getData().isEmpty());
        assertFalse(response.getMeta().isSuccess());
        assertEquals(404, response.getMeta().getCode());
        assertNotNull(response.getMeta().getErrorMessage());
        assertEquals("Not found", response.getMeta().getErrorMessage());
    }
}