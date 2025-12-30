package ma.formations.multiconnector.service.impl;

import lombok.RequiredArgsConstructor;
import ma.formations.multiconnector.common.CommonTools;
import ma.formations.multiconnector.dao.BankAccountRepository;
import ma.formations.multiconnector.dao.CustomerRepository;
import ma.formations.multiconnector.domain.AccountStatus;
import ma.formations.multiconnector.domain.BankAccount;
import ma.formations.multiconnector.domain.Customer;
import ma.formations.multiconnector.dtos.bankaccount.*;
import ma.formations.multiconnector.service.IBankAccountService;
import ma.formations.multiconnector.service.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BankAccountServiceImpl implements IBankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final CommonTools commonTools;

    @Override
    public List<BankAccountDto> getAllBankAccounts() {
        return bankAccountRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public BankAccountDto getBankAccountByRib(String rib) {
        BankAccount b = bankAccountRepository.findByRib(rib)
                .orElseThrow(() -> new BusinessException("BankAccount not found with rib=" + rib));
        return toDto(b);
    }

    @Override
    public AddBankAccountResponse saveBankAccount(AddBankAccountRequest dto) {
        if (bankAccountRepository.existsByRib(dto.getRib())) {
            throw new BusinessException("rib already exists: " + dto.getRib());
        }
        Customer customer = customerRepository.findByIdentityRef(dto.getCustomerIdentityRef())
                .orElseThrow(() -> new BusinessException("Customer not found with identityRef=" + dto.getCustomerIdentityRef()));

        BankAccount bankAccount = BankAccount.builder()
                .rib(dto.getRib())
                .amount(dto.getAmount())
                .createdAt(commonTools.now())
                .accountStatus(AccountStatus.OPENED)
                .customer(customer)
                .build();

        BankAccount saved = bankAccountRepository.save(bankAccount);

        return AddBankAccountResponse.builder()
                .message("BankAccount created")
                .bankAccount(toDto(saved))
                .build();
    }

    private BankAccountDto toDto(BankAccount b) {
        return BankAccountDto.builder()
                .id(b.getId())
                .rib(b.getRib())
                .amount(b.getAmount())
                .createdAt(b.getCreatedAt().toString())
                .accountStatus(b.getAccountStatus().name())
                .customerIdentityRef(b.getCustomer().getIdentityRef())
                .build();
    }
}
