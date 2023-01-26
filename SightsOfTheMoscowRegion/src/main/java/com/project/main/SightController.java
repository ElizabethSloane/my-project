package com.project.main;

import com.project.main.model.Sight;
import com.project.main.model.Town;
import com.project.main.repository.SightRepository;
import com.project.main.repository.TownRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class SightController {

    private final TownRepository townRepository;

    private final SightRepository sightRepository;

    public SightController(TownRepository townRepository, SightRepository sightRepository) throws IOException {
        this.townRepository = townRepository;
        this.sightRepository = sightRepository;
    }


    @PostMapping(value = "/towns/{id}/sights", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> add(@PathVariable int id,
            @RequestBody Map<String, String> data) {
        Optional<Town> optionalTown = townRepository.findById(id);
        if (!optionalTown.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else {
            Sight newSight = new Sight();
            newSight.setName(data.get("name"));
            newSight.setIdTown(id);
            newSight.setInformation(data.get("information"));
            sightRepository.save(newSight);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @GetMapping("/towns/{id}")
    public ResponseEntity<?> get(@PathVariable int id,
                                 Model model) {
        Optional<Town> optionalTask = townRepository.findById(id);

        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else {
            Iterable<Sight> sightsOfTown = sightRepository.findAll();
            ArrayList<Sight> sights = new ArrayList<>();
            for (Sight sight : sightsOfTown) {
                if (sight.getIdTown() == id) {
                    sights.add(sight);
                }
            }
            model.addAttribute("sights", sights);
            return new ResponseEntity(sights, HttpStatus.OK);
        }
    }

    @PatchMapping(value = "/towns/{idOfTown}/sights/{idOfSight}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<?> patch(@RequestBody Map<String, String> data,
                                                 @PathVariable int idOfTown,
                                                 @PathVariable int idOfSight) {
        Optional<Town> optionalTask = townRepository.findById(idOfTown);

        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        else {
            Iterable<Sight> sightsOfTown = sightRepository.findAll();

            for (Sight sight : sightsOfTown) {
                if (sight.getId() == idOfSight) {
                    if (data.containsKey("name") && !(data.get("name").equals(sight.getName()))) {
                        sight.setName(data.get("name"));
                    }
                    if (data.containsKey("information") && !data.get("information").equals(sight.getInformation())) {
                        sight.setInformation(data.get("information"));
                    }
                    sightRepository.save(sight);
                    break;
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @DeleteMapping("/towns/{id}/sights/{idOfSight}")
    public ResponseEntity<?> delete(@PathVariable int idOfSight) {
        Optional<Sight> optionalSight = sightRepository.findById(idOfSight);

        if (!optionalSight.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else {
            sightRepository.deleteById(idOfSight);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
