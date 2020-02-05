package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class AminoAcidTargets {

    @Id
    private Long id;

    @Column
    private BigDecimal cysteine;

    @Column
    private BigDecimal histidine;

    @Column
    private BigDecimal isoleucine;

    @Column
    private BigDecimal leucine;

    @Column
    private BigDecimal lysine;

    @Column
    private BigDecimal methionine;

    @Column
    private BigDecimal phenylalanine;

    @Column
    private BigDecimal threonine;

    @Column
    private BigDecimal tryptophan;

    @Column
    private BigDecimal tyrosine;

    @Column
    private BigDecimal valine;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @MapsId
    private User user;

    public AminoAcidTargets(final User user) {
        this.user = user;
    }

}
