package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.def.activity.ActivityLevel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class UserBiometrics {

    @Id
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
    @JoinColumn(name = "USER_ID")
    private User user;

    public enum Sex {
        M,  //male
        F,  //female
        O   //other
    }

    public UserBiometrics(final LocalDate dateOfBirth,
                          final Sex sex,
                          final Short height,
                          final BigDecimal weight,
                          final ActivityLevel activityLevel,
                          final User user) {
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.user = user;
    }

    public static UserBiometrics getDefault(final User user) {
        return new UserBiometrics(
                LocalDate.of(2000, 1, 1),
                Sex.M,
                (short) 180,
                new BigDecimal(80),
                ActivityLevel.NONE,
                user);
    }

}
