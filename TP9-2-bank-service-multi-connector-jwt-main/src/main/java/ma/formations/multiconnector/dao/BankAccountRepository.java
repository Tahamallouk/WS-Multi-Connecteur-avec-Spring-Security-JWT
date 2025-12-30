package ma.formations.multiconnector.dao;

import ma.formations.multiconnector.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByRib(String rib);
    boolean existsByRib(String rib);
}
