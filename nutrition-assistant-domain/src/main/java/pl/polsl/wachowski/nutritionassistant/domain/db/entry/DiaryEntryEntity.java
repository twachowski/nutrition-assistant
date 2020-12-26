package pl.polsl.wachowski.nutritionassistant.domain.db.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@NoArgsConstructor
@Entity
@Table(name = "DIARY_ENTRY")
public class DiaryEntryEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "diaryEntry",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<FoodEntryEntity> foodEntries;

    @OneToMany(mappedBy = "diaryEntry",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<ExerciseEntryEntity> exerciseEntries;

    @OneToMany(mappedBy = "diaryEntry",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<NoteEntryEntity> noteEntries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,
                updatable = false)
    private UserEntity user;

    public DiaryEntryEntity(final LocalDate date, final UserEntity user) {
        this.date = date;
        this.user = user;
        this.foodEntries = new ArrayList<>(1);
        this.exerciseEntries = new ArrayList<>(1);
        this.noteEntries = new ArrayList<>(1);
    }

    public int getSize() {
        return foodEntries.size() + exerciseEntries.size() + noteEntries.size();
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
