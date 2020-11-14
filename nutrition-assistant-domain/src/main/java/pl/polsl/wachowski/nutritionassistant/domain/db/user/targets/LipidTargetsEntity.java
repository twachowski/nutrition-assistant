package pl.polsl.wachowski.nutritionassistant.domain.db.user.targets;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "LIPID_TARGETS")
public class LipidTargetsEntity {

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

    @OneToOne(cascade = CascadeType.ALL,
              fetch = FetchType.LAZY,
              orphanRemoval = true)
    @MapsId
    private UserEntity user;

    public LipidTargetsEntity(final UserEntity user) {
        this.user = user;
    }

}
