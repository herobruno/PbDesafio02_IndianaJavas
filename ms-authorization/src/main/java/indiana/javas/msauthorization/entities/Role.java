package indiana.javas.msauthorization.entities;

import indiana.javas.msauthorization.enums.ERole;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "description", unique = true, nullable = false)
    private ERole role;

    public Role(ERole role) {
        this.role = role;
    }

}
