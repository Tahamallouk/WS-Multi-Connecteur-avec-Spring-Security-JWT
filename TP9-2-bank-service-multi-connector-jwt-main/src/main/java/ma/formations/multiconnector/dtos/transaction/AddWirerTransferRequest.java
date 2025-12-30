package ma.formations.multiconnector.dtos.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AddWirerTransferRequest {

    @NotBlank(message = "ribFrom is required")
    private String ribFrom;

    @NotBlank(message = "ribTo is required")
    private String ribTo;

    @Min(value = 1, message = "amount must be >= 1")
    private double amount;

    @NotBlank(message = "username is required")
    private String username;
}
