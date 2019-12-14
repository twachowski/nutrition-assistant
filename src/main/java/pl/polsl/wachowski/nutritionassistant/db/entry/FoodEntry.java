package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Entity
public class FoodEntry {

    @Id
    @GeneratedValue
    private final Long id;

    @Column(nullable = false,
            updatable = false)
    private final Integer externalId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            updatable = false,
            length = 11)
    private final NutritionDataProvider provider;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            length = 9)
    private MeasureUnit measureUnit;

    @Column(nullable = false,
            precision = 10,
            scale = 3)
    private BigDecimal amount;

    @Column(nullable = false,
            columnDefinition = "TINYINT")
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            updatable = false)
    private final DiaryEntry diaryEntry;

    public enum MeasureUnit {
        MICROGRAM,
        MILLIGRAM,
        GRAM
    }

}
