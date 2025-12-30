package ma.formations.multiconnector.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false, unique = true)
    protected String username;

    protected String firstname;
    protected String lastname;

    // nullable pour ne pas casser les users "customer" existants si besoin
    private String password;

    @OneToMany(mappedBy = "user")
    private List<BankAccountTransaction> bankAccountTransactionList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE")
    @Builder.Default
    private List<Role> authorities = new ArrayList<>();

    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;

    private String email;

    public User(String username) {
        this.username = username;
    }
}
