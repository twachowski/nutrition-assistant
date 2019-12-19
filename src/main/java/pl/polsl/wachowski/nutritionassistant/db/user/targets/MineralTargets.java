package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class MineralTargets {

    @Id
    private Long id;

    @Column
    private Short calcium;

    @Column
    private Short copper;

    @Column
    private Short iodine;

    @Column
    private Short iron;

    @Column
    private Short magnesium;

    @Column
    private Short manganese;

    @Column
    private Short phosphorus;

    @Column
    private Short potassium;

    @Column
    private Short selenium;

    @Column
    private Short sodium;

    @Column
    private Short zinc;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @MapsId
    private User user;

    public MineralTargets(final User user) {
        this.user = user;
    }

}
