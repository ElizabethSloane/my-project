package com.project.main;

import com.project.main.model.Sight;
import com.project.main.model.Town;
import com.project.main.repository.SightRepository;
import com.project.main.repository.TownRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SightController {

    private final TownRepository townRepository;

    private final SightRepository sightRepository;

    public SightController(TownRepository townRepository, SightRepository sightRepository) throws IOException {
        this.townRepository = townRepository;
        this.sightRepository = sightRepository;
    }

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<Town> townIterable = townRepository.findAll();
        Iterable<Sight> sightIterable = sightRepository.findAll();

        ArrayList<Town> towns = new ArrayList<>();
        for (Town town : townIterable) {
            towns.add(town);
        }
        model.addAttribute("towns", towns);
        model.addAttribute("townsCount", towns.size());



        LocalDateTime date = LocalDateTime.now();
        return "index";
    }
}
