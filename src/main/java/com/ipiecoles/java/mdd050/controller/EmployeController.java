package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/count"
    )
    public Long countEmployes(){
        return employeRepository.count();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}",
            produces = "application/json"
    )
    public Employe findById(@PathVariable (value = "id") Long idParam){
        Optional<Employe> employe = employeRepository.findById(idParam);
        if(employe.isEmpty()){
            throw new EntityNotFoundException("L'employé d'identifiant : " + idParam + " n'a pas été trouvé !");
        }
        return employe.get();
    }

//    @RequestMapping(
//            method = RequestMethod.GET,
//            value = "/{matricule}",
//            produces = "application/json"
//    )
//    public Employe findById(@PathVariable (value = "matricule") String matricule){
//        return employeRepository.findByMatricule(matricule);
//    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = "application/json",
            value = "",
            params = "matricule"
    )
    public Employe findByMatricule(@RequestParam("matricule") String matricule){
        Employe employe = employeRepository.findByMatricule(matricule);
        if(employe == null){
            throw new EntityNotFoundException("L'employé ayant pour matricule : " + matricule + " n'a pas été trouvé !");
        }
        return employe;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value="",
            params = {"page","size","sortDirection", "sortProperty"}
    )
    public Page<Employe> listEmployePagging(@RequestParam(defaultValue = "0", value="page") Integer page, @RequestParam("size") Integer size, @RequestParam("sortProperty") String sortProperty, @RequestParam("sortDirection") String sortDirection){
        if(page < 0){
            throw new IllegalArgumentException("La page doit être un entier positif ou null !");
        }
        if(page < 0 || page > 50){
            throw new IllegalArgumentException("La page doit être un entier positif compris entre 0 et 50 !");
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        return employeRepository.findAll(pageRequest);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, //Because that return an Employe
            consumes = MediaType.APPLICATION_JSON_VALUE, //Because that take the data to can create an Employe
            value = ""
    )
    public Employe createEmploye(@RequestBody Employe employeToAdd){
        for(Employe unEmploye : this.employeRepository.findAll()){
            if(unEmploye.getMatricule().equals(employeToAdd.getMatricule())){
                throw new EntityExistsException("L'employé ayant pour matricule :" + employeToAdd.getMatricule() + " existe déjà !");
            }
        }
        if(this.employeRepository.findAll().contains(employeToAdd)){
            throw new EntityExistsException("L'employé ayant pour matricule :" + employeToAdd.getMatricule() + " existe déjà !");
        }
        return employeRepository.save(employeToAdd);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, //Because that return an Employe
            consumes = MediaType.APPLICATION_JSON_VALUE, //Because that take the data to can create an Employe
            value = "/{id}"
    )
    public Employe updateEmploye(@PathVariable Long id, @RequestBody Employe employe){
        Optional<Employe> theEmploye = employeRepository.findById(id);
        if(theEmploye == null){
            throw new EntityNotFoundException("L'employé ayant pour id : " + id + " n'a pas été trouvé !");
        }
        for(Employe unEmploye : this.employeRepository.findAll()){
            if(unEmploye.getMatricule().equals(employe.getMatricule())){
                throw new EntityExistsException("Le matricule : " + employe.getMatricule() + " est déjà attribué à un autre employé !");
            }
        }
        return employeRepository.save(employe);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{id}"
    )
    //@ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmploye(@PathVariable Long id){
        //employeRepository.delete(employeRepository.findById(id).get());
        if(this.employeRepository.findById(id).get() == null){
            throw new EntityNotFoundException("L'employé d'identifiant : " + id + " n'a pas été trouvé, donc impossible de le supprimer !");
        }
        employeRepository.deleteById(id);
    }
}
