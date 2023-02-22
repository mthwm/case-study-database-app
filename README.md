# case-study-database-app

SpringBoot aplikace poskytující API pro správu databáze uchazečů

## Databáze uchazečů

### Vytvoření nového uchazeče

1. Metoda: POST
2. Endpoint: `/api/applicants`
3. parametry:
    1. [String] first_name
    2. [String] last_name

### Úprava stávajícího uchazeče

1. Metoda: PUT
2. Endpoint: `/api/applicants/:id`
3. parametry:
    1. [String] first_name
    2. [String] last_name

### Smazání uchazeče z databáze

1. Metoda: DELETE
2. Endpoint: `/api/applicants/:id`

### Získání všech uchazečů v databázi

1. Metoda: GET
2. Endpoint: `/api/applicants`

### Získání konkrétního uchazeče v databázi

1. Metoda: GET
2. Endpoint: `/api/applicants/:id`

## Databáze technologií

### Vytvoření nové technologie

1. Metoda: POST
2. Endpoint: `/applicants/:applicantId/technologies`
3. parametry:
   1. [String] name
   2. [int] proficiency
   3. [String] description

### Úprava stávající technologie

1. Metoda: PUT
2. Endpoint: `/api/technologies/:id`
3. parametry:
    1. [String] name
    2. [int] proficiency
    3. [String] description

### Smazání technologie z databáze

1. Metoda: DELETE
2. Endpoint: `/api/technologies/:id`

### Smazání všech technologií uchazeče z databáze

1. Metoda: DELETE
2. Endpoint: `/applicants/:applicantId/technologies`

### Získání všech technologií uchazeče v databázi

1. Metoda: GET
2. Endpoint: `/applicants/:applicantId/technologies`

### Získání přehledu o technologiích v databázi

1. Metoda: GET
2. Endpoint: `/api/technologies/stats`

### Získání konkrétní technologie v databázi

1. Metoda: GET
2. Endpoint: `/api/technologies/:id`
