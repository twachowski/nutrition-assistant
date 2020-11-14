package pl.polsl.wachowski.nutritionassistant.domain.db.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "FOOD_ENTRY")
public class FoodEntryEntity implements Sortable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,
            updatable = false)
    private String externalId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            updatable = false,
            length = 11)
    private NutritionDataProvider provider;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            length = 5)
    private FoodMassUnit unit;

    @Column(nullable = false,
            precision = 10,
            scale = 3)
    private BigDecimal amount;

    @Column(nullable = false)
    private Short position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,
                updatable = false)
    private DiaryEntryEntity diaryEntry;

    public FoodEntryEntity(final String externalId,
                           final NutritionDataProvider provider,
                           final FoodMassUnit unit,
                           final BigDecimal amount,
                           final Short position,
                           final DiaryEntryEntity diaryEntry) {
        this.externalId = externalId;
        this.provider = provider;
        this.unit = unit;
        this.amount = amount;
        this.position = position;
        this.diaryEntry = diaryEntry;
    }

    @Override
    public void moveUp() {
        position = (short) (position - 1);
    }

}
