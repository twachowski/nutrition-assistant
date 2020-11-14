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
@Table(name = "AMINO_ACID_TARGETS")
public class AminoAcidTargetsEntity {

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

    @OneToOne(cascade = CascadeType.ALL,
              fetch = FetchType.LAZY,
              orphanRemoval = true)
    @MapsId
    private UserEntity user;

    public AminoAcidTargetsEntity(final UserEntity user) {
        this.user = user;
    }

}
