package cz.moravek.database.api.repository;

import cz.moravek.database.api.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    List<Technology> findByApplicantId(long applicantId);
    void deleteByApplicantId(long applicantId);
}