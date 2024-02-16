package team.a5.gachigayu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
public class User extends BaseEntity implements UserDetails {

    @Column(name = "email", unique = true, nullable = false, updatable = false, length = 50)
    private String email;

    @Column(name = "nickname", nullable = false, length = 12)
    private String nickname;

    @Column(name = "name", length = 12)
    private String name;

    @Column(name = "account_id", length = 30)
    private String accountId;

    @Column(name = "profile_image", length = 200)
    private String profileImage;

    @Column(name = "certificate", nullable = false)
    private boolean certificate;

    private int totalLength;

    private int totalTime;

    @OneToMany(mappedBy = "user")
    private List<Activity> activities = new ArrayList<>();

    protected User() {
    }

    @Builder
    public User(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public static User of(String email, String nickname) {
        return User.builder()
                .email(email)
                .nickname(nickname)
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

    public void updateInfo(String name, String accountId, String imageURL) {
        this.name = name;
        this.accountId = accountId;
        if (imageURL != null) {
            this.profileImage = imageURL;
        }
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateActivity(Promenade promenade) {
        this.totalLength += promenade.getLength();
        this.totalTime += promenade.getTime();
    }

    public void verify() {
        this.certificate = true;
    }
}
