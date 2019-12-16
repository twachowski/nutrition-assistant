package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class NoteEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Short position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            nullable = false,
            updatable = false)
    private DiaryEntry diaryEntry;

}
