package edu.froliak.tddlabworks.controller;

/*
  @author eugen
  @project tdd-labworks
  @class WeaponRestController
  @version 1.0.0
  @since 3/27/2026 - 10.50
*/

import edu.froliak.tddlabworks.model.Weapon;
import edu.froliak.tddlabworks.request.WeaponCreateRequest;
import edu.froliak.tddlabworks.request.WeaponUpdateRequest;
import edu.froliak.tddlabworks.service.WeaponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items/")
@RequiredArgsConstructor
public class WeaponRestController {

    private final WeaponService weaponService;

    // CRUD   create read update delete

    // read all
    @GetMapping
    public List<Weapon> showAll() {
        return weaponService.getAll();
    }

    // read one
    @GetMapping("{id}")
    public Weapon showOneById(@PathVariable String id) {
        return weaponService.getById(id);
    }

    @PostMapping
    public Weapon insert(@RequestBody Weapon weapon) {
        return weaponService.create(weapon);
    }

    //============== request =====================
    @PostMapping("/dto")
    public Weapon insert(@RequestBody WeaponCreateRequest request) {
        return weaponService.create(request);
    }

    @PutMapping
    public Weapon edit(@RequestBody Weapon weapon) {
        return weaponService.update(weapon);
    }
    //============== request =====================
    @PutMapping("/dto")
    public Weapon edit(@RequestBody WeaponUpdateRequest request) {
        return weaponService.update(request);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        weaponService.delById(id);
    }
}