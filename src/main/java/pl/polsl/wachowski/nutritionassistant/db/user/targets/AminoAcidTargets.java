package pl.polsl.wachowski.nutritionassistant.db.user.targets;

import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import javax.persistence.*;

@Data
@Entity
public class AminoAcidTargets {

    @Id
    private final Long id;

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

    @OneToOne(mappedBy = "aminoAcidTargets")
    private final User user;

}
