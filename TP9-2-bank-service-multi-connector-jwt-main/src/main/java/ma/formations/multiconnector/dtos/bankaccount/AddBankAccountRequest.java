package ma.formations.multiconnector.dtos.bankaccount;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AddBankAccountRequest {

    @NotBlank(message = "rib is required")
    private String rib;

    @Min(value = 0, message = "amount must be >= 0")
    private double amount;

    @NotBlank(message = "customerIdentityRef is required")
    private String customerIdentityRef;
}
