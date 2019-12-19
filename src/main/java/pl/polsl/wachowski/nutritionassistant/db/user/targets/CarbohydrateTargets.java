package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class CarbohydrateTargets {

    @Id
    private Long id;

    @Column
    private Short sugars;

    @Column
    private Short fiber;

    @Column
    private Short starch;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @MapsId
    private User user;

    public CarbohydrateTargets(final User user) {
        this.user = user;
    }

}
