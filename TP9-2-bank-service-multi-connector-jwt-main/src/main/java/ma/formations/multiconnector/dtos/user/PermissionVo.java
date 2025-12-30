package ma.formations.multiconnector.dtos.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionVo {
    private int id;
    private String authority; // ex: GET_ALL_CUSTUMERS
}
