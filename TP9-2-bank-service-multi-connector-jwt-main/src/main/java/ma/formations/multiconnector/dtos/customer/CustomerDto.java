package ma.formations.multiconnector.dtos.customer;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerDto {
    private Long id;
    private String username;
    private String identityRef;
    private String firstname;
    private String lastname;
}
