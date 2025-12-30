package ma.formations.multiconnector.dtos.customer;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateCustomerResponse {
    private String message;
    private CustomerDto customer;
}
