package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.testng.annotations.Test;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.List;
@AllArgsConstructor
@Service
public class PisteServicesImpl implements  IPisteServices{

    private final TaskExecutionProperties taskExecutionProperties;
    private IPisteRepository pisteRepository;

    @Override
    @Test
    @Order(1)
    public List<Piste> retrieveAllPistes() {
        return pisteRepository.findAll();
    }

    @Override
    @Test
@Order(0)
    public Piste addPiste(Piste piste) {
        return pisteRepository.save(piste);
    }

    @Override
    @Test
    @Order(2)
    public void removePiste(Long numPiste) {
        pisteRepository.deleteById(numPiste);
    }

    @Override
    @Test
    @Order(3)
    public Piste retrievePiste(Long numPiste) {
        return pisteRepository.findById(numPiste).orElse(null);
    }
}
