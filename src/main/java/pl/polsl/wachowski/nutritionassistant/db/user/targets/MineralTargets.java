package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;

@Data
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

    @OneToOne(mappedBy = "mineralTargets")
    private User user;

}
