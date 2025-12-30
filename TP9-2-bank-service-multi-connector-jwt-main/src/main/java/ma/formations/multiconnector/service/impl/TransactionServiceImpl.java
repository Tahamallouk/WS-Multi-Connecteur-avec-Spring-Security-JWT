package ma.formations.multiconnector.service.impl;

import lombok.RequiredArgsConstructor;
import ma.formations.multiconnector.common.CommonTools;
import ma.formations.multiconnector.dao.BankAccountRepository;
import ma.formations.multiconnector.dao.BankAccountTransactionRepository;
import ma.formations.multiconnector.dao.UserRepository;
import ma.formations.multiconnector.domain.*;
import ma.formations.multiconnector.dtos.transaction.*;
import ma.formations.multiconnector.service.ITransactionService;
import ma.formations.multiconnector.service.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements ITransactionService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountTransactionRepository txRepository;
    private final UserRepository userRepository;
    private final CommonTools commonTools;

    @Override
    public AddWirerTransferResponse wiredTransfer(AddWirerTransferRequest dto) {
        if (dto.getRibFrom().equals(dto.getRibTo())) {
            throw new BusinessException("ribFrom and ribTo must be different");
        }

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BusinessException("User not found username=" + dto.getUsername()));

        BankAccount from = bankAccountRepository.findByRib(dto.getRibFrom())
                .orElseThrow(() -> new BusinessException("BankAccount not found ribFrom=" + dto.getRibFrom()));
        BankAccount to = bankAccountRepository.findByRib(dto.getRibTo())
                .orElseThrow(() -> new BusinessException("BankAccount not found ribTo=" + dto.getRibTo()));

        checkAccountAllowed(from);
        checkAccountAllowed(to);

        if (from.getAmount() < dto.getAmount()) {
            throw new BusinessException("Insufficient balance on ribFrom=" + from.getRib());
        }

        // apply changes
        from.setAmount(from.getAmount() - dto.getAmount());
        to.setAmount(to.getAmount() + dto.getAmount());

        Instant now = commonTools.now();

        BankAccountTransaction txFrom = BankAccountTransaction.builder()
                .createdAt(now)
                .transactionType(TransactionType.DEBIT)
                .amount(dto.getAmount())
                .bankAccount(from)
                .user(user)
                .build();

        BankAccountTransaction txTo = BankAccountTransaction.builder()
                .createdAt(now)
                .transactionType(TransactionType.CREDIT)
                .amount(dto.getAmount())
                .bankAccount(to)
                .user(user)
                .build();

        txRepository.save(txFrom);
        txRepository.save(txTo);
        bankAccountRepository.save(from);
        bankAccountRepository.save(to);

        return AddWirerTransferResponse.builder()
                .message("Transfer done")
                .transactionFrom(toDto(txFrom))
                .transactionTo(toDto(txTo))
                .build();
    }

    @Override
    public List<TransactionDto> getTransactions(GetTransactionListRequest dto) {
        // simple version: return all transactions (or filter by rib if provided)
        List<BankAccountTransaction> all = txRepository.findAll();

        Instant from = parseInstantOrNull(dto.getDateFrom());
        Instant to = parseInstantOrNull(dto.getDateTo());

        return all.stream()
                .filter(tx -> dto.getRib() == null || dto.getRib().isBlank() || tx.getBankAccount().getRib().equals(dto.getRib()))
                .filter(tx -> from == null || !tx.getCreatedAt().isBefore(from))
                .filter(tx -> to == null || !tx.getCreatedAt().isAfter(to))
                .map(this::toDto)
                .toList();
    }

    private void checkAccountAllowed(BankAccount account) {
        if (account.getAccountStatus() == AccountStatus.CLOSED || account.getAccountStatus() == AccountStatus.BLOCKED) {
            throw new BusinessException("No transaction allowed on account status " + account.getAccountStatus());
        }
    }

    private Instant parseInstantOrNull(String s) {
        if (s == null || s.isBlank()) return null;
        return Instant.parse(s);
    }

    private TransactionDto toDto(BankAccountTransaction tx) {
        return TransactionDto.builder()
                .createdAt(tx.getCreatedAt().toString())
                .transactionType(tx.getTransactionType().name())
                .amount(tx.getAmount())
                .rib(tx.getBankAccount().getRib())
                .username(tx.getUser().getUsername())
                .build();
    }
}
