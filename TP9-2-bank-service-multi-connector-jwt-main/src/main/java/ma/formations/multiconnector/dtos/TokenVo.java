package ma.formations.multiconnector.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenVo implements Serializable {
    private String username;
    private String jwtToken;
    @Builder.Default
    private List<String> roles = new ArrayList<>();
}
