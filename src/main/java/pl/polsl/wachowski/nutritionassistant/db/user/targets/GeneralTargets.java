package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;

@Data
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

    @OneToOne(mappedBy = "generalTargets")
    private User user;

}
