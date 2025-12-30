package ma.formations.multiconnector.dtos.transaction;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class GetTransactionListRequest {
    private String rib;
    private String dateFrom; // ISO-8601: 2025-01-01T00:00:00Z (ou vide)
    private String dateTo;
}
