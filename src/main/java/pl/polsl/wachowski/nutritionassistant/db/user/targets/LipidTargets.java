package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class LipidTargets {

    @Id
    private Long id;

    @Column
    private Short saturatedFat;

    @Column
    private Short unsaturatedFat;

    @Column
    private Short monounsaturatedFat;

    @Column
    private Short polyunsaturatedFat;

    @Column
    private Short omega6;

    @Column
    private Short omega3;

    @Column
    private Short transFat;

    @Column
    private Short cholesterol;

    @OneToOne(mappedBy = "lipidTargets")
    private User user;

}
