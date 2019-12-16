package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;

@Data
@Entity
public class VitaminTargets {

    @Id
    private final Long id;

    @Column(name = "A")
    private Short vitaminA;

    @Column(name = "B1")
    private Short vitaminB1;

    @Column(name = "B2")
    private Short vitaminB2;

    @Column(name = "B3")
    private Short vitaminB3;

    @Column(name = "B5")
    private Short vitaminB5;

    @Column(name = "B6")
    private Short vitaminB6;

    @Column(name = "B7")
    private Short vitaminB7;

    @Column(name = "B12")
    private Short vitaminB12;

    @Column(name = "C")
    private Short vitaminC;

    @Column(name = "D")
    private Short vitaminD;

    @Column(name = "E")
    private Short vitaminE;

    @Column
    private Short folate;

    @Column(name = "K")
    private Short vitaminK;

    @OneToOne(mappedBy = "vitaminTargets")
    private final User user;

}
