package ma.formations.multiconnector.dtos.transaction;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionDto {
    private String createdAt;
    private String transactionType;
    private double amount;
    private String rib;
    private String username;
}
