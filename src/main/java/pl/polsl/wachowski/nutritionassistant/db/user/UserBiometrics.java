package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.def.activity.ActivityLevel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class UserBiometrics {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            columnDefinition = "CHAR(1)")
    private Sex sex;

    @Column(nullable = false)
    private Short height;

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
    private User user;

    public enum Sex {
        M,  //male
        F,  //female
        O   //other
    }

}
