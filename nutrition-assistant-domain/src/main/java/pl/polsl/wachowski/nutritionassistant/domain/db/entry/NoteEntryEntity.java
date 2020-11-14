package pl.polsl.wachowski.nutritionassistant.domain.db.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(exclude = "diaryEntry")
@EqualsAndHashCode(exclude = "diaryEntry")
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
    @JoinColumn(nullable = false,
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
