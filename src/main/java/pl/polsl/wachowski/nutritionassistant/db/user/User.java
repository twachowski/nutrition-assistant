package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.db.entry.DiaryEntry;

import javax.persistence.*;
import java.util.List;

@Value
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true,
            nullable = false,
            updatable = false,
            length = 64)
    String email;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false,
            orphanRemoval = true)
    UserBiometrics userBiometrics;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false,
            orphanRemoval = true)
    UserCredentials userCredentials;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<DiaryEntry> diaryEntries;

}
