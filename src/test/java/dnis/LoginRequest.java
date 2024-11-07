package dnis;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String userName;
    private String password;
    private String tenant;
}
