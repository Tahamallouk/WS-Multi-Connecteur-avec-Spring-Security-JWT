package ma.formations.multiconnector.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "BANK_ACCOUNT_TRANSACTION")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private double amount;

    @ManyToOne(optional = false)
    private BankAccount bankAccount;

    @ManyToOne(optional = false)
    private User user;
}
