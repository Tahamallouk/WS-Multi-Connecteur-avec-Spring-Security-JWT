package ma.formations.multiconnector.dtos.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleVo implements GrantedAuthority {

    private int id;

    // ex: ROLE_CLIENT ou bien une permission comme GET_ALL_CUSTUMERS (voir flatten)
    private String authority;

    @Builder.Default
    private List<PermissionVo> authorities = new ArrayList<>();
}
