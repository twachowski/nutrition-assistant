package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class DiaryEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private final LocalDate date;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<FoodEntry> foodEntries;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<ExerciseEntry> exerciseEntries;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<NoteEntry> noteEntries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            updatable = false)
    private User user;

}
