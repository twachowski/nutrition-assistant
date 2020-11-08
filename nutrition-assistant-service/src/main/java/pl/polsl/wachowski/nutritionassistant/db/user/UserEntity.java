package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.db.entry.DiaryEntryEntity;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,
            nullable = false,
            updatable = false,
            length = 64)
    private String email;

    @Enumerated
    @Column(nullable = false,
            columnDefinition = "TINYINT(1)")
    private UserStatus status;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false,
            orphanRemoval = true)
    private UserBiometricsEntity userBiometrics;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false,
            orphanRemoval = true)
    private UserCredentialsEntity userCredentials;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<DiaryEntryEntity> diaryEntries;

    public enum UserStatus {
        INACTIVE,
        ACTIVE
    }

    public UserEntity(final String email) {
        this.email = email;
        this.status = UserStatus.INACTIVE;
    }

    public boolean isActive() {
        return status.equals(UserStatus.ACTIVE);
    }

    public void activate() {
        setStatus(UserStatus.ACTIVE);
    }

}
