package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class DiaryEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<FoodEntry> foodEntries;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ExerciseEntry> exerciseEntries;

    @OneToMany(
            mappedBy = "diaryEntry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<NoteEntry> noteEntries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            updatable = false)
    private User user;

    public DiaryEntry(final LocalDate date, final User user) {
        this.date = date;
        this.user = user;
        this.foodEntries = new ArrayList<>(1);
        this.exerciseEntries = new ArrayList<>(1);
        this.noteEntries = new ArrayList<>(1);
    }

}
