package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class VerificationToken {

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
    private User user;

    public VerificationToken() {
        this.expiryDate = LocalDateTime.now().plusDays(1);
    }

    public VerificationToken(final String value, final User user) {
        this();
        this.value = value;
        this.user = user;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

}
