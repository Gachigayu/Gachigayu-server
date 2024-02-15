package team.a5.gachigayu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Entity
public class User extends BaseEntity implements UserDetails {

    @Column(name = "email", unique = true, nullable = false, updatable = false, length = 50)
    private String email;

    @Column(name = "name", length = 12)
    private String name;

    @Column(name = "account_id", length = 30)
    private String accountId;

    protected User() {
    }

    @Builder
    public User(String email) {
        this.email = email;
    }

    public static User of(String email) {
        return User.builder()
                .email(email)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateInfo(String name, String accountId) {
        this.name = name;
        this.accountId = accountId;
    }
}
