package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class GeneralTargets {

    @Id
    private Long id;

    @Column
    private Byte carbohydrates;

    @Column
    private Byte lipids;

    @Column
    private Byte protein;

    @Column
    private Short alcohol;

    @Column
    private Short water;

    @Column
    private Short caffeine;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @MapsId
    private User user;

    public GeneralTargets(final User user) {
        this.user = user;
    }

}
