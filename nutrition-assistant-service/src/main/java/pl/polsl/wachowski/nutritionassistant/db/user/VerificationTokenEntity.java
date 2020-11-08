package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "VERIFICATION_TOKEN")
public class VerificationTokenEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,
            nullable = false,
            updatable = false,
            columnDefinition = "CHAR(36)")
    private String value;

    @Column(nullable = false,
            updatable = false)
    private LocalDateTime expiryDate;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            updatable = false)
    private UserEntity user;

    public VerificationTokenEntity() {
        this.expiryDate = LocalDateTime.now().plusDays(1);
    }

    public VerificationTokenEntity(final String value, final UserEntity user) {
        this();
        this.value = value;
        this.user = user;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

}
