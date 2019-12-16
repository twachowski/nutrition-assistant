package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.def.activity.ActivityLevel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
public class UserBiometrics {

    @Id
    @GeneratedValue
    private final Long id;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            columnDefinition = "CHAR(1)")
    private Sex sex;

    @Column(nullable = false,
            columnDefinition = "SMALLINT")
    private Integer height;

    @Column(nullable = false,
            precision = 5,
            scale = 2)
    private BigDecimal weight;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            length = 9)
    private ActivityLevel activityLevel;

    @OneToOne
    @MapsId
    private final User user;

    public enum Sex {
        M,  //male
        F,  //female
        O   //other
    }

}
