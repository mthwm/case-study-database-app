package com.moravek.applicantsdatabase.repository;

import com.moravek.applicantsdatabase.model.Applicant;
import com.moravek.applicantsdatabase.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    List<Technology> findByNameContaining(String name);
}
