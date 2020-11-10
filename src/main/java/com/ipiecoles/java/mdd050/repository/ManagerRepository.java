package com.ipiecoles.java.mdd050.repository;

import com.ipiecoles.java.mdd050.model.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @EntityGraph(attributePaths = "equipe")
    Manager findOneWithEquipeById(Long id);
}
