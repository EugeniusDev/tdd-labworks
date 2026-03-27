package edu.froliak.tddlabworks.service;

/*
  @author eugen
  @project tdd-labworks
  @class ItemService
  @version 1.0.0
  @since 3/27/2026 - 10.45
*/

import edu.froliak.tddlabworks.model.Item;
import edu.froliak.tddlabworks.repository.ItemRepository;
import edu.froliak.tddlabworks.request.ItemCreateRequest;
import edu.froliak.tddlabworks.request.ItemUpdateRequest;
import edu.froliak.tddlabworks.response.ApiResponse;
import edu.froliak.tddlabworks.response.BaseMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private List<Item> items = new ArrayList<>();

    {
        items.add(new Item( "Freddie Mercury", "Queen","vocal, piano"));
        items.add(new Item( "Paul McCartney", "Beatles","guitar"));
        items.add(new Item( "Till Lindemann", "Rammstein","vocal"));
        items.add(new Item( "John Lennon", "Beatles","piano"));
        items.add(new Item( "Brian May", "Queen","solo guitar"));
        items.add(new Item( "Tarja Turunen", "Nightwish","vocal"));
        items.add(new Item( "Roger Waters", "Pink Floyd","poet"));
    }

    //  @PostConstruct
    void init() {
        this.itemRepository.deleteAll();
        for(Item item : items) {
            create(item);
        }

    }
    //  CRUD   - create read update delete

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item getById(String id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item create(ItemCreateRequest request) {
        return mapToItem(request);
    }

    public  Item update(Item item) {
        return itemRepository.save(item);
    }


    public void delById(String id) {
        itemRepository.deleteById(id);
    }

    private Item mapToItem(ItemCreateRequest request) {
        Item item = new Item(request.name(), request.code(), request.description());
        return item;
    }

    public Item update(ItemUpdateRequest request) {
        Item itemPersisted = itemRepository.findById(request.id()).orElse(null);
        if (itemPersisted != null) {
            Item itemToUpdate =
                    Item.builder()
                            .id(request.id())
                            .name(request.name())
                            .code(request.code())
                            .description(request.description())
                            .build();
            return itemRepository.save(itemToUpdate);

        }
        return null;
    }

    public List<Item> createAll(List<Item> items) {
        return itemRepository.saveAll(items);
    }

    public void deleteAll() {
        itemRepository.deleteAll();
    }

    //------------------------- 12 03 response impl ------------------------------
    public ApiResponse<BaseMetaData, Item> getByIdAsApiResponse(String id) {
        Item itemPersisted = itemRepository.findById(id).orElse(null);
        BaseMetaData baseMetaData = new BaseMetaData();
        if (itemPersisted != null) {
            ApiResponse<BaseMetaData, Item> response = new ApiResponse<>(baseMetaData, itemPersisted);
            return response;
        }

        return null;
    }

    public  ApiResponse<BaseMetaData, Item> getAllAsApiResponse() {
        return null;
    }

    public  ApiResponse<BaseMetaData, Item> updateAsApiResponse(Item item) {
        return null;
    }
}