package NM.SpringBoot.BlogApp.API.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthResponse {
    
    private String AccessToken;
    private String RefreshToken;
    private String username;
    private Boolean success;

}
