package edu.froliak.tddlabworks.controller;

/*
  @author eugen
  @project tdd-labworks
  @class ItemRestController
  @version 1.0.0
  @since 3/27/2026 - 10.50
*/

import edu.froliak.tddlabworks.model.Item;
import edu.froliak.tddlabworks.request.ItemCreateRequest;
import edu.froliak.tddlabworks.request.ItemUpdateRequest;
import edu.froliak.tddlabworks.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items/")
@RequiredArgsConstructor
public class ItemRestController {

    private final ItemService itemService;

    // CRUD   create read update delete

    // read all
    @GetMapping
    public List<Item> showAll() {
        return itemService.getAll();
    }

    // read one
    @GetMapping("{id}")
    public Item showOneById(@PathVariable String id) {
        return itemService.getById(id);
    }

    @PostMapping
    public Item insert(@RequestBody Item item) {
        return itemService.create(item);
    }

    //============== request =====================
    @PostMapping("/dto")
    public Item insert(@RequestBody ItemCreateRequest request) {
        return itemService.create(request);
    }

    @PutMapping
    public Item edit(@RequestBody Item item) {
        return itemService.update(item);
    }
    //============== request =====================
    @PutMapping("/dto")
    public Item edit(@RequestBody ItemUpdateRequest request) {
        return itemService.update(request);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        itemService.delById(id);
    }
}