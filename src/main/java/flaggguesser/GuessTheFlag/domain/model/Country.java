package flaggguesser.GuessTheFlag.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String isoCode;

    public Country(String name, String isoCode) {
        this.name = name;
        this.isoCode = isoCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getFlagUrl() {
        return "https://flagcdn.com/w320/" + isoCode + ".png";
    }
}
