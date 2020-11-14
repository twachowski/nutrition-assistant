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
@Table(name = "VITAMIN_TARGETS")
public class VitaminTargetsEntity {

    @Id
    private Long id;

    @Column(name = "A")
    private BigDecimal vitaminA;

    @Column(name = "B1")
    private BigDecimal vitaminB1;

    @Column(name = "B2")
    private BigDecimal vitaminB2;

    @Column(name = "B3")
    private BigDecimal vitaminB3;

    @Column(name = "B4")
    private BigDecimal vitaminB4;

    @Column(name = "B5")
    private BigDecimal vitaminB5;

    @Column(name = "B6")
    private BigDecimal vitaminB6;

    @Column(name = "B7")
    private BigDecimal vitaminB7;

    @Column(name = "B12")
    private BigDecimal vitaminB12;

    @Column(name = "C")
    private BigDecimal vitaminC;

    @Column(name = "D")
    private BigDecimal vitaminD;

    @Column(name = "E")
    private BigDecimal vitaminE;

    @Column
    private BigDecimal folate;

    @Column(name = "K")
    private BigDecimal vitaminK;

    @OneToOne(cascade = CascadeType.ALL,
              fetch = FetchType.LAZY,
              orphanRemoval = true)
    @MapsId
    private UserEntity user;

    public VitaminTargetsEntity(final UserEntity user) {
        this.user = user;
    }

}
