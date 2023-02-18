package com.moravek.applicantsdatabase.repository;

import com.moravek.applicantsdatabase.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    List<Applicant> findByFirstNameContaining(String first_name);
}
