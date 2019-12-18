package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class FoodEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,
            updatable = false)
    private Integer externalId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            updatable = false,
            length = 11)
    private NutritionDataProvider provider;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            length = 9)
    private MeasureUnit measureUnit;

    @Column(nullable = false,
            precision = 10,
            scale = 3)
    private BigDecimal amount;

    @Column(nullable = false)
    private Short position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            updatable = false)
    private DiaryEntry diaryEntry;

    public enum MeasureUnit {
        MICROGRAM,
        MILLIGRAM,
        GRAM
    }

}
