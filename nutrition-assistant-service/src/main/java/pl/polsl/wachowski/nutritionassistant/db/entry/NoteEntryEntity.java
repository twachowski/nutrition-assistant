package pl.polsl.wachowski.nutritionassistant.db.entry;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "NOTE_ENTRY")
public class NoteEntryEntity implements Sortable {

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
    private DiaryEntryEntity diaryEntry;

    public NoteEntryEntity(final String content, final Short position, final DiaryEntryEntity diaryEntry) {
        this.content = content;
        this.position = position;
        this.diaryEntry = diaryEntry;
    }

    @Override
    public void moveUp() {
        position = (short) (position - 1);
    }

}
