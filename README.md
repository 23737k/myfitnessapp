# myfitnessapp
Fitness Gym Routine Tracker App Project

Features
-
- CRUD operations for routines
- CRUD operations for exercises and sets
- CRUD operations for custom exercises

Getting started
-
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+
* MySql

1) Database Configuration

    1. Make sure MySQL is installed and running on your system. By default, it will connect to the database on port 3306 with the user `root` and an empty password.
    
    2. If you have a different MySQL configuration, update the [application.yml](src/main/resources/application.yml) accordingly

2) To build and run the project, follow these steps:


   1. Clone the repository:
      ```
      git clone https://github.com/23737k/myfitnessapp.git
      ```
      
   2. Navigate to myfitnessapp directory:
      ```
      cd myfitnessapp
      ```
   3. Install dependencies (assuming Maven is installed):
      ```
      mvn clean install
      ```
   4. Run the project
      ```
      mvn spring-boot:run
      ```
   5. Access the API documentation using Swagger UI:
      
      Open a web browser and go to http://localhost/doc/swagger-ui.html


   -> The application will be available at http://localhost:8080


Class Diagram
-

```mermaid
classDiagram
    class Ejercicio {
        +Integer id
        +String nombre
        +GrupoMuscular grupoMuscularPrimario
        +GrupoMuscular grupoMuscularSecundario
        +TipoDeEjercicio tipoDeEjercicio
    }
    
    class GrupoMuscular {
        +Integer id
        +String nombre
    }
    
    class TipoDeEjercicio {
    <<Enum>>
    }
    
    class ItemRutina {
        +Integer id
        +Ejercicio ejercicio
        +int descansoEnSeg
        +String nota
        +List~Serie~ series
    }
    
    class Rutina {
        +Integer id
        +String nombre
        +String descripcion
        +List~ItemRutina~ items
    }
    
    class Serie {
    <<Abstract>>
        +Integer id
    }
    
    class SeriePesoCorpYReps {
        +Integer reps
    }
    
    class SeriePesoYReps {
        +Integer reps
        +Double pesoEnKg
    }
    
    class SerieTiempo {
        +Integer tiempoEnSeg
    }
    
    Ejercicio --> "1" GrupoMuscular : grupoMuscularPrimario
    Ejercicio --> "1" GrupoMuscular : grupoMuscularSecundario
    Ejercicio --> "1" TipoDeEjercicio : tipoDeEjercicio
    
    ItemRutina --> "1" Ejercicio : ejercicio
    ItemRutina --> "0..*" Serie : series
    
    Rutina --> "0..*" ItemRutina : items
    
    Serie <|-- SeriePesoCorpYReps
    Serie <|-- SeriePesoYReps
    Serie <|-- SerieTiempo

```
