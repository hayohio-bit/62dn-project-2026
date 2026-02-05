package com.antigravity.animal.controller;

import com.antigravity.animal.dto.AnimalResponseDTO;
import com.antigravity.animal.entity.AnimalStatus;
import com.antigravity.animal.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AnimalViewController {

    private final AnimalService animalService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/animals")
    public String animalList(
            @RequestParam(required = false) AnimalStatus status,
            @PageableDefault(size = 9) Pageable pageable,
            Model model) {
        Page<AnimalResponseDTO> animals = animalService.getAnimals(status, pageable);
        model.addAttribute("animals", animals);
        model.addAttribute("status", status);
        return "animal/list";
    }

    @GetMapping("/animals/{id}")
    public String animalDetail(@PathVariable Long id, Model model) {
        AnimalResponseDTO animal = animalService.getAnimal(id);
        model.addAttribute("animal", animal);
        return "animal/detail";
    }

    @GetMapping("/animals/register")
    public String registerForm(Model model) {
        return "animal/write";
    }

    @GetMapping("/animals/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        AnimalResponseDTO animal = animalService.getAnimal(id);
        model.addAttribute("animal", animal);
        return "animal/write";
    }
}
