package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.db.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "DIARY_ENTRY")
public class DiaryEntryEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<FoodEntryEntity> foodEntries;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ExerciseEntryEntity> exerciseEntries;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<NoteEntryEntity> noteEntries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            updatable = false)
    private UserEntity user;

    public DiaryEntryEntity(final LocalDate date, final UserEntity user) {
        this.date = date;
        this.user = user;
        this.foodEntries = new ArrayList<>(1);
        this.exerciseEntries = new ArrayList<>(1);
        this.noteEntries = new ArrayList<>(1);
    }

    public void add(final FoodEntryEntity foodEntry) {
        foodEntries.add(foodEntry);
    }

    public void add(final ExerciseEntryEntity exerciseEntry) {
        exerciseEntries.add(exerciseEntry);
    }

    public void add(final NoteEntryEntity noteEntry) {
        noteEntries.add(noteEntry);
    }

}
