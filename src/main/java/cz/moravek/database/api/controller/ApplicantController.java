package cz.moravek.database.api.controller;

import cz.moravek.database.api.exception.ResourceNotFoundException;
import cz.moravek.database.api.model.Applicant;
import cz.moravek.database.api.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ApplicantController {
    @Autowired
    ApplicantRepository applicantRepository;

    @GetMapping("/applicants")
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        List<Applicant> applicants = new ArrayList<Applicant>();

        applicantRepository.findAll().forEach(applicants::add);


        if (applicants.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(applicants, HttpStatus.OK);
    }

    @GetMapping("/applicants/{id}")
    public ResponseEntity<Applicant> getApplicantById(@PathVariable("id") long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Applicant with id = " + id));

        return new ResponseEntity<>(applicant, HttpStatus.OK);
    }

    @PostMapping("/applicants")
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) {
        Applicant _applicant = applicantRepository.save(new Applicant(applicant.getFirst_name(), applicant.getLast_name()));
        return new ResponseEntity<>(_applicant, HttpStatus.CREATED);
    }

    @PutMapping("/applicants/{id}")
    public ResponseEntity<Applicant> updateApplicant(@PathVariable("id") long id, @RequestBody Applicant applicant) {
        Applicant _applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Applicant with id = " + id));

        _applicant.setFirst_name(applicant.getFirst_name());
        _applicant.setLast_name(applicant.getLast_name());

        return new ResponseEntity<>(applicantRepository.save(_applicant), HttpStatus.OK);
    }

    @DeleteMapping("/applicants/{id}")
    public ResponseEntity<HttpStatus> deleteApplicant(@PathVariable("id") long id) {
        applicantRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/applicants")
    public ResponseEntity<HttpStatus> deleteAllApplicants() {
        applicantRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
