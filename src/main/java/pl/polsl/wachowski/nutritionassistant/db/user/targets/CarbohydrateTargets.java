package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;

@Data
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

    @OneToOne(mappedBy = "carbohydrateTargets")
    private User user;

}
