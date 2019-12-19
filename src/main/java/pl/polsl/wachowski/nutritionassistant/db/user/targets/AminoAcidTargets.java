package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class AminoAcidTargets {

    @Id
    private Long id;

    @Column
    private Short arginine;

    @Column
    private Short histidine;

    @Column
    private Short isoleucine;

    @Column
    private Short leucine;

    @Column
    private Short lysine;

    @Column
    private Short methionine;

    @Column
    private Short phenylalanine;

    @Column
    private Short threonine;

    @Column
    private Short tryptophan;

    @Column
    private Short valine;

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
