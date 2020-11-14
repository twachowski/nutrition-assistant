package pl.polsl.wachowski.nutritionassistant.domain.db.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@ToString(exclude = "diaryEntry")
@EqualsAndHashCode(exclude = "diaryEntry")
@NoArgsConstructor
@Entity
@Table(name = "EXERCISE_ENTRY")
public class ExerciseEntryEntity implements Sortable {

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
    @JoinColumn(nullable = false,
                updatable = false)
    private DiaryEntryEntity diaryEntry;

    public ExerciseEntryEntity(final String name,
                               final TimeUnit timeUnit,
                               final BigDecimal amount,
                               final Short position,
                               final DiaryEntryEntity diaryEntry) {
        this.name = name;
        this.timeUnit = timeUnit;
        this.amount = amount;
        this.position = position;
        this.diaryEntry = diaryEntry;
    }

    @Override
    public void moveUp() {
        position = (short) (position - 1);
    }

}
