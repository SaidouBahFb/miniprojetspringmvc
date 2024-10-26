package com.groupeisi.minievaluation.controller;

import com.groupeisi.minievaluation.dto.CoursDto;
import com.groupeisi.minievaluation.service.CoursService;
import com.groupeisi.minievaluation.service.ICoursService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CoursController {
    private static final Logger logger = LoggerFactory.getLogger(CoursController.class);

    private ICoursService coursService = new CoursService();

    @GetMapping("/cours")
    public String showCours(Model model) {

        try {
            Optional<List<CoursDto>> cours = coursService.findAll();
            if (cours.isPresent()) {
                logger.info("Liste des cours récupérée avec succès");
                model.addAttribute("coursList", cours.get());
            } else {
                logger.info("Aucun produit trouvé");
                model.addAttribute("coursList", new ArrayList<CoursDto>());
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des produits", e);
        }

        return "cours/cours";
    }

    @PostMapping("/cours")
    public String saveProduct(
            @RequestParam("matiere") String matiere,
            @RequestParam("professeur") String professeur,
            @RequestParam("classe") String classe)
             {
        CoursDto coursDto = new CoursDto();
        coursDto.setMatiere(matiere);
        coursDto.setProfesseur(professeur);
        coursDto.setClasse(classe);

        try {
            boolean coursSaved = coursService.save(coursDto);
            if (coursSaved) {
                logger.info("Cours enregistré avec succès");
            } else {
                logger.warn("Échec de l'enregistrement du cours");
            }
        } catch (Exception e) {
            logger.error("Erreur lors de l'enregistrement du cours", e);
        }

        return "redirect:/cours";
    }
}
