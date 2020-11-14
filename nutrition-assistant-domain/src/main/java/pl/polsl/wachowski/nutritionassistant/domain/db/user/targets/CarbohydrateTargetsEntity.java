package pl.polsl.wachowski.nutritionassistant.domain.db.user.targets;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CARBOHYDRATE_TARGETS")
public class CarbohydrateTargetsEntity {

    @Id
    private Long id;

    @Column
    private Short sugars;

    @Column
    private Short fiber;

    @Column
    private Short starch;

    @OneToOne(cascade = CascadeType.ALL,
              fetch = FetchType.LAZY,
              orphanRemoval = true)
    @MapsId
    private UserEntity user;

    public CarbohydrateTargetsEntity(final UserEntity user) {
        this.user = user;
    }

}
