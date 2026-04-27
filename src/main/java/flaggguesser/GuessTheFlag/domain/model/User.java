package flaggguesser.GuessTheFlag.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

    @Id@Column(name = "USER_NAME", nullable = false)
    String username;

    @Column(name = "password",  nullable = false)
    String password;

    public User() {
        this.creationDate = LocalDateTime.now();
    }


    public User(String username, String password) {
        this.creationDate = LocalDateTime.now();
        this.password = password;
        this.username = username;
        this.score = 0;
        System.out.println("USER CREATED WITH: " + this.username + "\nCreation: " + this.creationDate + "\nPASSWORD: " +  this.password);
    }

    public User(String username, Integer score) {
        this.creationDate = LocalDateTime.now();
        this.username = username;
        this.score = score;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}