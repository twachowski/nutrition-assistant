package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.def.measure.TimeUnit;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class ExerciseEntry implements Sortable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,
            updatable = false,
            length = 32)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,
            length = 6)
    private TimeUnit timeUnit;

    @Column(nullable = false,
            precision = 6,
            scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Short position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            updatable = false)
    private DiaryEntry diaryEntry;

    public ExerciseEntry(final String name,
                         final TimeUnit timeUnit,
                         final BigDecimal amount,
                         final Short position,
                         final DiaryEntry diaryEntry) {
        this.name = name;
        this.timeUnit = timeUnit;
        this.amount = amount;
        this.position = position;
        this.diaryEntry = diaryEntry;
    }

}
