package ma.formations.multiconnector.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVo implements UserDetails {

    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Builder.Default
    private List<RoleVo> authorities = new ArrayList<>();

    private String email;
}
