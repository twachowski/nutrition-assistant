package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.db.entry.DiaryEntry;
import pl.polsl.wachowski.nutritionassistant.db.user.targets.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,
            nullable = false,
            updatable = false,
            length = 64)
    private String email;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false,
            orphanRemoval = true)
    private UserBiometrics userBiometrics;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false,
            orphanRemoval = true)
    private UserCredentials userCredentials;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<DiaryEntry> diaryEntries;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinTable(
            name = "user_general_targets",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "general_targets_id", referencedColumnName = "id", nullable = false))
    private GeneralTargets generalTargets;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinTable(
            name = "user_carbohydrate_targets",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "carbohydrate_targets_id", referencedColumnName = "id", nullable = false))
    private CarbohydrateTargets carbohydrateTargets;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinTable(
            name = "user_lipid_targets",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "lipid_targets_id", referencedColumnName = "id", nullable = false))
    private LipidTargets lipidTargets;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinTable(
            name = "user_amino_acid_targets",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "amino_acid_targets_id", referencedColumnName = "id", nullable = false))
    private AminoAcidTargets aminoAcidTargets;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinTable(
            name = "user_mineral_targets",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "mineral_targets_id", referencedColumnName = "id", nullable = false))
    private MineralTargets mineralTargets;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinTable(
            name = "user_vitamin_targets",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "vitamin_targets_id", referencedColumnName = "id", nullable = false))
    private VitaminTargets vitaminTargets;

    public User(final String email) {
        this.email = email;
    }

}
