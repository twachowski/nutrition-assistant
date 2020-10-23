package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.api.user.ActivityLevel;
import pl.polsl.wachowski.nutritionassistant.api.user.Sex;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class UserBiometricsEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            length = 6)
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

    @Column(nullable = false)
    private Short calorieGoal;

    @OneToOne(fetch = FetchType.LAZY,
              optional = false)
    @MapsId
    @JoinColumn(name = "USER_ID")
    private User user;

    public UserBiometricsEntity(final LocalDate dateOfBirth,
                                final Sex sex,
                                final Short height,
                                final BigDecimal weight,
                                final ActivityLevel activityLevel,
                                final Short calorieGoal,
                                final User user) {
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.calorieGoal = calorieGoal;
        this.user = user;
    }

    public static UserBiometricsEntity getDefault(final User user) {
        return new UserBiometricsEntity(
                LocalDate.of(2000, 1, 1),
                Sex.MALE,
                (short) 180,
                new BigDecimal(80),
                ActivityLevel.NONE,
                (short) 0,
                user);
    }

}
