package pl.polsl.wachowski.nutritionassistant.domain.db.user.targets;

import lombok.*;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.*;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

import javax.persistence.*;

@Data
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@NoArgsConstructor
@Setter(value = AccessLevel.PRIVATE)
@Entity
@Table(name = "HIGHLIGHTED_TARGETS")
public class HighlightedTargetsEntity {

    @Id
    private Long id;

    @Column(nullable = false,
            length = 30)
    private String nutrient1;

    @Column(nullable = false,
            length = 30)
    private String nutrient2;

    @Column(nullable = false,
            length = 30)
    private String nutrient3;

    @Column(nullable = false,
            length = 30)
    private String nutrient4;

    @Column(nullable = false,
            length = 30)
    private String nutrient5;

    @Column(nullable = false,
            length = 30)
    private String nutrient6;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY,
              optional = false)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    public HighlightedTargetsEntity(final Nutrient nutrient1,
                                    final Nutrient nutrient2,
                                    final Nutrient nutrient3,
                                    final Nutrient nutrient4,
                                    final Nutrient nutrient5,
                                    final Nutrient nutrient6,
                                    final UserEntity user) {
        this.nutrient1 = nutrient1.getName();
        this.nutrient2 = nutrient2.getName();
        this.nutrient3 = nutrient3.getName();
        this.nutrient4 = nutrient4.getName();
        this.nutrient5 = nutrient5.getName();
        this.nutrient6 = nutrient6.getName();
        this.user = user;
    }

    public static HighlightedTargetsEntity getDefault(final UserEntity user) {
        return new HighlightedTargetsEntity(Carbohydrate.SUGAR,
                                            Lipid.CHOLESTEROL,
                                            Vitamin.B12,
                                            Vitamin.D,
                                            Mineral.SODIUM,
                                            Mineral.ZINC,
                                            user);
    }

    public void setNutrient1(final Nutrient nutrient) {
        this.nutrient1 = nutrient.getName();
    }

    public void setNutrient2(final Nutrient nutrient) {
        this.nutrient2 = nutrient.getName();
    }

    public void setNutrient3(final Nutrient nutrient) {
        this.nutrient3 = nutrient.getName();
    }

    public void setNutrient4(final Nutrient nutrient) {
        this.nutrient4 = nutrient.getName();
    }

    public void setNutrient5(final Nutrient nutrient) {
        this.nutrient5 = nutrient.getName();
    }

    public void setNutrient6(final Nutrient nutrient) {
        this.nutrient6 = nutrient.getName();
    }

}
