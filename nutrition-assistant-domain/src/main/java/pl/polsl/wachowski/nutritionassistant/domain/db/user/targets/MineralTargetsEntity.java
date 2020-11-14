package pl.polsl.wachowski.nutritionassistant.domain.db.user.targets;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@NoArgsConstructor
@Entity
@Table(name = "MINERAL_TARGETS")
public class MineralTargetsEntity {

    @Id
    private Long id;

    @Column
    private BigDecimal calcium;

    @Column
    private BigDecimal copper;

    @Column
    private BigDecimal fluoride;

    @Column
    private BigDecimal iron;

    @Column
    private BigDecimal magnesium;

    @Column
    private BigDecimal manganese;

    @Column
    private BigDecimal phosphorus;

    @Column
    private BigDecimal potassium;

    @Column
    private BigDecimal selenium;

    @Column
    private BigDecimal sodium;

    @Column
    private BigDecimal zinc;

    @OneToOne(cascade = CascadeType.ALL,
              fetch = FetchType.LAZY,
              orphanRemoval = true)
    @MapsId
    private UserEntity user;

    public MineralTargetsEntity(final UserEntity user) {
        this.user = user;
    }

}
