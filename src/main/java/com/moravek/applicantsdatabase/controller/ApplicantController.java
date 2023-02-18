package com.moravek.applicantsdatabase.controller;

import com.moravek.applicantsdatabase.model.Applicant;
import com.moravek.applicantsdatabase.model.Technology;
import com.moravek.applicantsdatabase.repository.ApplicantRepository;
import com.moravek.applicantsdatabase.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ApplicantController {

    @Autowired
    ApplicantRepository applicantRepository;
    @Autowired
    TechnologyRepository technologyRepository;

    @GetMapping("/applicants")
    public ResponseEntity<List<Applicant>> getAllApplicants(@RequestParam(required = false) String first_name) {
        try {
            List<Applicant> applicants = new ArrayList<Applicant>();

            if (first_name == null)
                applicantRepository.findAll().forEach(applicants::add);
            else
                applicantRepository.findByFirstNameContaining(first_name).forEach(applicants::add);

            if (applicants.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(applicants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/applicants/{id}")
    public ResponseEntity<Applicant> getApplicant(@PathVariable("id") long id, @RequestBody Applicant applicant) {
        Optional<Applicant> applicantData = applicantRepository.findById(id);

        if (applicantData.isPresent()) {
            Applicant _applicant = applicantData.get();

            return new ResponseEntity<>(applicantRepository.save(_applicant), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/technologies")
    public ResponseEntity<List<Technology>> getAllTechnologies(@RequestParam(required = false) String name) {
        try {
            List<Technology> technologies = new ArrayList<Technology>();

            if (name == null)
                technologyRepository.findAll().forEach(technologies::add);
            else
                technologyRepository.findByNameContaining(name).forEach(technologies::add);

            if (technologies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(technologies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/technologies")
    public ResponseEntity<Technology> createTechnology(@RequestBody Technology technology) {
        try {
            Technology _technology = technologyRepository
                    .save(new Technology(
                            technology.getApplicantId(),
                            technology.getName(),
                            technology.getProficiency(),
                            technology.getDescription()
                    ));
            return new ResponseEntity<>(_technology, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/applicants")
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) {
        try {
            Applicant _applicant = applicantRepository
                    .save(new Applicant(
                            applicant.getFirstName(),
                            applicant.getLastName()
                    ));
            return new ResponseEntity<>(_applicant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/applicants/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            applicantRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/technologies/{id}")
    public ResponseEntity<HttpStatus> deleteTechnology(@PathVariable("id") long id) {
        try {
            technologyRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/applicants/{id}")
    public ResponseEntity<Applicant> updateApplicant(@PathVariable("id") long id, @RequestBody Applicant applicant) {
        Optional<Applicant> applicantData = applicantRepository.findById(id);

        if (applicantData.isPresent()) {
            Applicant _applicant = applicantData.get();
            _applicant.setFirstName(applicant.getFirstName());
            _applicant.setLastName(applicant.getLastName());
            return new ResponseEntity<>(applicantRepository.save(_applicant), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/technologies/{id}")
    public ResponseEntity<Technology> updateTechnology(@PathVariable("id") long id, @RequestBody Technology technology) {
        Optional<Technology> technologyData = technologyRepository.findById(id);

        if (technologyData.isPresent()) {
            Technology _technology = technologyData.get();
            _technology.setApplicantId(technology.getApplicantId());
            _technology.setDescription(technology.getDescription());
            _technology.setName(technology.getName());
            _technology.setProficiency(technology.getProficiency());
            return new ResponseEntity<>(technologyRepository.save(_technology), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
