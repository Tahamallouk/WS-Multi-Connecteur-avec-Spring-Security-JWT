package ma.formations.multiconnector.service;

import ma.formations.multiconnector.dtos.bankaccount.*;

import java.util.List;

public interface IBankAccountService {
    List<BankAccountDto> getAllBankAccounts();
    BankAccountDto getBankAccountByRib(String rib);
    AddBankAccountResponse saveBankAccount(AddBankAccountRequest dto);
}
