package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/techniciens")
public class TechnicienController {

    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{idTechnicien}/manager/{matricule}/add",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Manager addAnManagerToATechnicient(@PathVariable(value = "idTechnicien") Long idTechnicien , @PathVariable (value = "matricule") String matriculeOfTheManager){
        Manager myManager = (Manager)this.employeRepository.findByMatricule(matriculeOfTheManager);
        Technicien techToAddToTheEquipe = (Technicien)this.employeRepository.findById(idTechnicien).get();
        techToAddToTheEquipe.setManager(myManager);
        this.employeRepository.save(techToAddToTheEquipe);
        return myManager;
    }
}
