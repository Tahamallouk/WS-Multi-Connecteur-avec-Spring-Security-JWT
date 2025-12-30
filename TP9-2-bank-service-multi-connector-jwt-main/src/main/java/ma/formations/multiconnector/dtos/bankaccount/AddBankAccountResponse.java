package ma.formations.multiconnector.dtos.bankaccount;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AddBankAccountResponse {
    private String message;
    private BankAccountDto bankAccount;
}
