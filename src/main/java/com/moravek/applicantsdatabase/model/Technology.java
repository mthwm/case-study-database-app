package com.moravek.applicantsdatabase.model;

import jakarta.persistence.*;

@Entity
@Table(name = "technologies")
public class Technology {
    private final int MIN_PROFICIENCY = 1;
    private final int MAX_PROFICIENCY = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long technologyId;

    @Column(name = "applicant_id")
    private long applicantId;

    @Column(name = "technology_name")
    private String name;

    @Column(name = "level_of_proficiency")
    private int proficiency;

    @Column(name = "description")
    private String description;

    public Technology(long applicantId, String name, int proficiency, String description) {
        this.applicantId = applicantId;
        this.name = name;

        if (proficiency >= MIN_PROFICIENCY && proficiency <= MAX_PROFICIENCY) {
            this.proficiency = proficiency;
        }

        this.description = description;
    }

    public Technology() { }

    public long getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(long technologyId) {
        this.technologyId = technologyId;
    }

    public long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(long applicantId) {
        this.applicantId = applicantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
