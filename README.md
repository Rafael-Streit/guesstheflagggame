# Flag Guesser

Ein kleines Lernprojekt: In diesem Spiel errätst du, zu welchem Land eine angezeigte Flagge gehört. Ich habe es gebaut, um Spring Boot und Vaadin praktisch auszuprobieren.

## Was ich dabei gelernt habe

- Spring Boot: Application Context, Component Scan und Auto-Configuration
- Dependency Injection mit @Service und Konstruktor-Injektion
- REST-Endpunkte mit @RestController
- UI-Komponenten mit Vaadin

## So funktioniert das Spiel

- Pro Runde wird eine zufällige Flagge angezeigt.
- Du wählst das passende Land aus mehreren Optionen.
- Der Punktestand zählt die richtigen Antworten.

## Verwendete Technologien

| Technologie | Version |
| --- | --- |
| Java | 25+ |
| Spring Boot | 4.0.x |
| Vaadin | 24.0.x |
| Maven | — |

## Lokal starten

Voraussetzungen: Java 25 oder neuer und Maven 3.8 oder neuer.

    git clone <repository-url>
    cd flag-guesser
    mvn spring-boot:run

Ersetze den Platzhalter durch die Adresse des Git-Repositories. Öffne anschließend http://localhost:8080.

## Was noch fehlt

Das Projekt ist bewusst einfach gehalten. Als Nächstes würde ich gern mehr Antwortmöglichkeiten und Schwierigkeitsstufen ergänzen. Eine Bestenliste und Tests für den Service wären ebenfalls sinnvoll.

