package dnis;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TilsterTokenResponse {
    public CallCenterId callCenterId;
    public String ticket;
    public AgentId agentId;
    public String role;
    public long expiresInMs;
}
