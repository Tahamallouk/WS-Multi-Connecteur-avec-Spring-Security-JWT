package ma.formations.multiconnector.dtos.bankaccount;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccountDto {
    private Long id;
    private String rib;
    private double amount;
    private String createdAt;
    private String accountStatus;
    private String customerIdentityRef;
}
