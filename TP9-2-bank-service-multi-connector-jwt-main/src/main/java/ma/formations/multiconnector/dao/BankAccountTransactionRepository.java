package ma.formations.multiconnector.dao;

import ma.formations.multiconnector.domain.BankAccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountTransactionRepository extends JpaRepository<BankAccountTransaction, Long> {
}
