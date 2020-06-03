package ca.sheridancollege.beans
;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
private Long userId;
private String userName;
private String encryptedPassword;
               
}
