package ma.formations.multiconnector.presentation.graphql;

import lombok.RequiredArgsConstructor;
import ma.formations.multiconnector.dtos.bankaccount.*;
import ma.formations.multiconnector.service.IBankAccountService;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BankAccountGraphqlController {

    private final IBankAccountService bankAccountService;

    @QueryMapping
    public List<BankAccountDto> bankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    @QueryMapping
    public BankAccountDto bankAccountByRib(@Argument String rib) {
        return bankAccountService.getBankAccountByRib(rib);
    }

    @MutationMapping
    public AddBankAccountResponse addBankAccount(@Argument("dto") AddBankAccountRequest dto) {
        return bankAccountService.saveBankAccount(dto);
    }
}
