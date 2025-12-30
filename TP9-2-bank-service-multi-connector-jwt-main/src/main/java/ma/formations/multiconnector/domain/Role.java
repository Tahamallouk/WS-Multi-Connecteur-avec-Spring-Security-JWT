package ma.formations.multiconnector.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String authority; // ex: ROLE_CLIENT

    @ManyToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<Permission> authorities = new ArrayList<>();
}
