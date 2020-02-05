package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class MineralTargets {

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
