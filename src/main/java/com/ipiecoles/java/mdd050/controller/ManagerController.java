package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(value = "/managers")
public class ManagerController {

    //Les repository permettent d'accéder en données en DB, en lecture comme écriture
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired
    private EmployeRepository employeRepository;


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{idManager}/equipe/{matricule}/add",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Technicien addAnEmployeToTheEquipe(@PathVariable(value = "idManager") Long idManager , @PathVariable (value = "matricule") String matriculeTechnicien){
        Technicien techToAdd = (Technicien) employeRepository.findByMatricule(matriculeTechnicien);
        Manager myManager = this.managerRepository.findById(idManager).get();
        techToAdd.setManager(myManager);
        return techToAdd;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{idManager}/equipe/{idTechnicien}/remove"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteATechnicientFromTheEquipe(@PathVariable(value = "idManager") Long idManager , @PathVariable (value = "idTechnicien") Long idTechnicien){
        //Not working
//        Manager myManager = this.managerRepository.findById(idManager).get();
//        Set<Technicien> maList = myManager.getEquipe();
//        for (Technicien aTech : maList){
//            if(aTech.getId() == idTechnicien){
//                maList.remove(aTech);
//            }
//        }
        Technicien unTech = technicienRepository.findById(idTechnicien).get();
        unTech.setManager(null);
        technicienRepository.save(unTech);
    }
}
