package ma.formations.multiconnector.dtos.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AddCustomerRequest {
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "identityRef is required")
    private String identityRef;

    @NotBlank(message = "firstname is required")
    private String firstname;

    @NotBlank(message = "lastname is required")
    private String lastname;
}
