package pl.polsl.wachowski.nutritionassistant.domain.db.user.targets;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

import javax.persistence.*;

@Data
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@NoArgsConstructor
@Entity
@Table(name = "GENERAL_TARGETS")
public class GeneralTargetsEntity {

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

    @OneToOne(cascade = CascadeType.ALL,
              fetch = FetchType.LAZY,
              orphanRemoval = true)
    @MapsId
    private UserEntity user;

    public GeneralTargetsEntity(final UserEntity user) {
        this.user = user;
    }

}
