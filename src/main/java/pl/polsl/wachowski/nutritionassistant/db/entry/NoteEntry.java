package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
public class NoteEntry {

    @Id
    @GeneratedValue
    private final Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false,
            columnDefinition = "TINYINT")
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            updatable = false)
    private final DiaryEntry diaryEntry;

}
