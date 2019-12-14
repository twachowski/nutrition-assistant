package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Value
@Entity
public class DiaryEntry {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    LocalDate date;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<FoodEntry> foodEntries;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<ExerciseEntry> exerciseEntries;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<NoteEntry> noteEntries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            updatable = false)
    User user;

}
