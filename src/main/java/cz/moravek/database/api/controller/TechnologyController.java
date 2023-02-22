package cz.moravek.database.api.controller;

import cz.moravek.database.api.exception.ResourceNotFoundException;
import cz.moravek.database.api.model.Technology;
import cz.moravek.database.api.repository.ApplicantRepository;
import cz.moravek.database.api.repository.TechnologyRepository;
import cz.moravek.database.api.TechnologyStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TechnologyController {
    @Autowired
    ApplicantRepository applicantRepository;
    @Autowired
    TechnologyRepository technologyRepository;

    @GetMapping("/applicants/{applicantId}/technologies")
    public ResponseEntity<List<Technology>> getAllTechnologiesByApplicantId(@PathVariable(value = "applicantId") Long applicantId) {
        if (!applicantRepository.existsById(applicantId)) {
            throw new ResourceNotFoundException("Not found Applicant with id = " + applicantId);
        }

        List<Technology> technologies = technologyRepository.findByApplicantId(applicantId);
        return new ResponseEntity<>(technologies, HttpStatus.OK);
    }

    @GetMapping("/technologies/{id}")
    public ResponseEntity<Technology> getTechnologiesByTechnologyId(@PathVariable(value = "id") Long id) {
        Technology technology = technologyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));

        return new ResponseEntity<>(technology, HttpStatus.OK);
    }

    @GetMapping("/technologies/stats")
    public List<TechnologyStats> calculateStatistics() {
        List<TechnologyStats> technologies = new ArrayList<>();

        technologyRepository.findAll().forEach(technology -> {
            if (technologies.size() == 0) {
                technologies.add(new TechnologyStats(technology.getName(), technology.getProficiency()));
            } else {
                boolean exists = false;
                for (TechnologyStats technologyStats : technologies) {
                    if (technology.getName().equals(technologyStats.getName())) {
                        technologyStats.increaseCount();
                        technologyStats.getLevels().add(technology.getProficiency());
                        exists = true;
                    }
                }

                if (!exists) {
                    technologies.add(new TechnologyStats(technology.getName(), technology.getProficiency()));
                }
            }
        });

        technologies.forEach(technologyStats -> {
            technologyStats.calculateAverageLevel();
            technologyStats.calculateMedian();
        });

        return technologies;
    }

    @PostMapping("/applicants/{applicantId}/technologies")
    public ResponseEntity<Technology> createTechnology(@PathVariable(value = "applicantId") Long applicantId, @RequestBody Technology technologyRequest) {
        Technology technology = applicantRepository.findById(applicantId).map(applicant -> {
            technologyRequest.setApplicant(applicant);
            if (!(technologyRequest.getProficiency() >= 1 && technologyRequest.getProficiency() <= 10)) {
                technologyRequest.setProficiency(-1);
            }
            return technologyRepository.save(technologyRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Applicant with id = " + applicantId));

        return new ResponseEntity<>(technology, HttpStatus.CREATED);
    }

    @PutMapping("/technologies/{id}")
    public ResponseEntity<Technology> updateTechnology(@PathVariable("id") long id, @RequestBody Technology technologyRequest) {
        Technology technology = technologyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TechnologyId " + id + "not found"));

        if (!(technologyRequest.getProficiency() >= 1 && technologyRequest.getProficiency() <= 10)) {
            technologyRequest.setProficiency(-1);
        }

        technology.setName(technologyRequest.getName());
        technology.setDescription(technologyRequest.getDescription());
        technology.setProficiency(technologyRequest.getProficiency());

        return new ResponseEntity<>(technologyRepository.save(technology), HttpStatus.OK);
    }

    @DeleteMapping("/technologies/{id}")
    public ResponseEntity<HttpStatus> deleteTechnology(@PathVariable("id") long id) {
        technologyRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/applicants/{applicantId}/technologies")
    public ResponseEntity<List<Technology>> deleteAllTechnologiesOfApplicant(@PathVariable(value = "applicantId") Long applicantId) {
        if (!applicantRepository.existsById(applicantId)) {
            throw new ResourceNotFoundException("Not found Applicant with id = " + applicantId);
        }

        technologyRepository.deleteByApplicantId(applicantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
