package edu.froliak.tddlabworks.service;

/*
  @author eugen
  @project tdd-labworks
  @class WeaponService
  @version 1.0.0
  @since 3/27/2026 - 10.45
*/

import edu.froliak.tddlabworks.model.Weapon;
import edu.froliak.tddlabworks.repository.WeaponRepository;
import edu.froliak.tddlabworks.request.WeaponCreateRequest;
import edu.froliak.tddlabworks.request.WeaponUpdateRequest;
import edu.froliak.tddlabworks.response.ApiResponse;
import edu.froliak.tddlabworks.response.BaseMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponService {

    private final WeaponRepository weaponRepository;

    private List<Weapon> weapons = new ArrayList<>();

    {
        weapons.add(new Weapon( "Freddie Mercury", "Queen","vocal, piano"));
        weapons.add(new Weapon( "Paul McCartney", "Beatles","guitar"));
        weapons.add(new Weapon( "Till Lindemann", "Rammstein","vocal"));
        weapons.add(new Weapon( "John Lennon", "Beatles","piano"));
        weapons.add(new Weapon( "Brian May", "Queen","solo guitar"));
        weapons.add(new Weapon( "Tarja Turunen", "Nightwish","vocal"));
        weapons.add(new Weapon( "Roger Waters", "Pink Floyd","poet"));
    }

    //  @PostConstruct
    void init() {
        this.weaponRepository.deleteAll();
        for(Weapon weapon : weapons) {
            create(weapon);
        }

    }
    //  CRUD   - create read update delete

    public List<Weapon> getAll() {
        return weaponRepository.findAll();
    }

    public Weapon getById(String id) {
        return weaponRepository.findById(id).orElse(null);
    }

    public Weapon create(Weapon weapon) {
        return weaponRepository.save(weapon);
    }

    public Weapon create(WeaponCreateRequest request) {
        return mapToItem(request);
    }

    public Weapon update(Weapon weapon) {
        return weaponRepository.save(weapon);
    }


    public void delById(String id) {
        weaponRepository.deleteById(id);
    }

    private Weapon mapToItem(WeaponCreateRequest request) {
        Weapon weapon = new Weapon(request.name(), request.code(), request.description());
        return weapon;
    }

    public Weapon update(WeaponUpdateRequest request) {
        Weapon weaponPersisted = weaponRepository.findById(request.id()).orElse(null);
        if (weaponPersisted != null) {
            Weapon weaponToUpdate =
                    Weapon.builder()
                            .id(request.id())
                            .name(request.name())
                            .code(request.code())
                            .description(request.description())
                            .build();
            return weaponRepository.save(weaponToUpdate);

        }
        return null;
    }

    public List<Weapon> createAll(List<Weapon> weapons) {
        return weaponRepository.saveAll(weapons);
    }

    public void deleteAll() {
        weaponRepository.deleteAll();
    }

    public ApiResponse<BaseMetaData, Weapon> getByIdAsApiResponse(String id) {
        Weapon weapon = weaponRepository.findById(id).orElse(null);
        if (weapon != null) {
            return new ApiResponse<>(new BaseMetaData(200, true), weapon);
        }
        return new ApiResponse<>(new BaseMetaData(404, false, "Not found"));
    }

    public ApiResponse<BaseMetaData, Weapon> getAllAsApiResponse() {
        List<Weapon> all = weaponRepository.findAll();
        BaseMetaData meta = new BaseMetaData(200, true);
        return ApiResponse.<BaseMetaData, Weapon>builder()
                .meta(meta)
                .data(all)
                .build();
    }

    public ApiResponse<BaseMetaData, Weapon> updateAsApiResponse(Weapon weapon) {
        if (weapon.getId() != null && weaponRepository.existsById(weapon.getId())) {
            Weapon updated = weaponRepository.save(weapon);
            return new ApiResponse<>(new BaseMetaData(200, true), updated);
        }
        return new ApiResponse<>(new BaseMetaData(404, false, "Not found"));
    }
}