package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class ExerciseEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,
            updatable = false,
            length = 32)
    private final String name;

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

    public enum TimeUnit {
        MINUTE,
        HOUR
    }

}
