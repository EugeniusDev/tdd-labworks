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
import edu.froliak.tddlabworks.request.WeaponPageRequest;
import edu.froliak.tddlabworks.request.WeaponUpdateRequest;
import edu.froliak.tddlabworks.response.ApiResponse;
import edu.froliak.tddlabworks.response.BaseMetaData;
import edu.froliak.tddlabworks.response.PaginationMetaData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeaponService {

    private final WeaponRepository weaponRepository;

    private List<Weapon> weapons = new ArrayList<>();
    {
        weapons.add(new Weapon("Glock 17", "PST-G17", "9mm semi-automatic service pistol"));
        weapons.add(new Weapon("AK-47", "RFL-AK47", "7.62x39mm gas-operated assault rifle"));
        weapons.add(new Weapon("M16A4", "RFL-M16", "5.56mm air-cooled service rifle"));
        weapons.add(new Weapon("Barrett M82", "RFL-M82", "Semi-auto .50 BMG anti-materiel rifle"));
        weapons.add(new Weapon("Desert Eagle", "PST-DE50", ".50 Action Express magnum handgun"));
        weapons.add(new Weapon("Heckler & Koch MP5", "SMG-MP5", "9mm roller-delayed submachine gun"));
        weapons.add(new Weapon("Remington 870", "SHG-R870", "12-gauge pump-action shotgun"));
        weapons.add(new Weapon("SIG P320", "PST-P320", "Modular striker-fired service pistol"));
        weapons.add(new Weapon("FN P90", "PDW-P90", "5.7x28mm personal defense weapon"));
        weapons.add(new Weapon("Steyr AUG", "RFL-AUG", "Bullpup configuration assault rifle"));
        weapons.add(new Weapon("Kriss Vector", "SMG-VEC", ".45 ACP recoil-compensated submachine gun"));
        weapons.add(new Weapon("M249 SAW", "LMG-M249", "5.56mm light machine gun"));
        weapons.add(new Weapon("FIM-92 Stinger", "SAM-F92", "Man-portable surface-to-air missile"));
        weapons.add(new Weapon("RPG-7", "LCH-RPG", "Rocket-propelled grenade launcher"));
        weapons.add(new Weapon("Beretta M9", "PST-BM9", "9mm military standard sidearm"));
        weapons.add(new Weapon("Katana", "SWD-KTN", "Traditional Japanese curved longsword"));
        weapons.add(new Weapon("Scottish Claymore", "SWD-CLM", "Heavy two-handed medieval greatsword"));
        weapons.add(new Weapon("Roman Gladius", "SWD-GLD", "Infantry short sword used by legionaries"));
        weapons.add(new Weapon("English Longbow", "BOW-LNG", "Powerful yew wood bow used in medieval warfare"));
        weapons.add(new Weapon("Morning Star", "MCE-MST", "Spiked medieval crushing weapon"));
        weapons.add(new Weapon("Rapier", "SWD-RPR", "Slender thrusting sword for dueling"));
        weapons.add(new Weapon("Halberd", "PLM-HLB", "Polearm combining an axe and a spearhead"));
        weapons.add(new Weapon("Scimitar", "SWD-SCM", "Curved blade popular in the Middle East"));
        weapons.add(new Weapon("Kukri", "KNF-KKR", "Forward-curved Nepalese utility knife"));
        weapons.add(new Weapon("Tomahawk", "AXE-TMH", "Tactical throwing and utility axe"));
        weapons.add(new Weapon("Flail", "MCE-FLL", "Chain-linked striking weapon with spiked ball"));
        weapons.add(new Weapon("Crossbow", "BOW-CRS", "High-tension mechanical bolt-firing weapon"));
        weapons.add(new Weapon("Mace", "MCE-STD", "Solid metal blunt-force combat weapon"));
        weapons.add(new Weapon("Saber", "SWD-SBR", "Curved cavalry sword with a hand guard"));
        weapons.add(new Weapon("Excalibur", "SWD-EXC", "Legendary mythical sword of King Arthur"));
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

    /////////////////   17.04 ////////////////////////////////
    public ApiResponse<PaginationMetaData, Weapon> getWeaponsPage(WeaponPageRequest request){

        Pageable pageable = PageRequest.of(request.page(), request.size(),
                Sort.by(Sort.Direction.DESC, "id"));

        Page<Weapon> page = weaponRepository.findAll(pageable);

        PaginationMetaData metaData = new PaginationMetaData();
        metaData.setCode(200);
        metaData.setSuccess(true);
        metaData.setErrorMessage(null);
        metaData.setNumber(page.getNumber());
        metaData.setSize(page.getSize());
        metaData.setTotalElements(page.getTotalElements());
        metaData.setTotalPages(page.getTotalPages());
        metaData.setFirst(page.isFirst());
        metaData.setLast(page.isLast());
        if (request.page() >= page.getTotalPages() && page.getTotalPages() > 0) {
            metaData.setErrorMessage("Requested page is not in range");
            return new ApiResponse<>(metaData, new ArrayList<>());
        }


        if (page.getTotalElements() == 0) {
            metaData.setErrorMessage("No weapons found");
            return new ApiResponse<>(metaData, new ArrayList<>());
        }

        metaData.setErrorMessage(null);

        return new ApiResponse<>(metaData, page.getContent());
    }
}