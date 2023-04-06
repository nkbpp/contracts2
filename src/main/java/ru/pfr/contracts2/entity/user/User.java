package ru.pfr.contracts2.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
// генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private Long active;

    private LocalDateTime date_last_entry; //дата последней попытки входа

    private Long id_user_zir;

    private Long id_ondel_zir;

    private String name_ondel_zir;

    private String email;


    @LastModifiedDate
    private LocalDateTime date_update;

    @CreatedDate
    private LocalDateTime date_create;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    Rayon rayon;

    public void setUserRoles(Collection<GrantedAuthority> userRoles) {
        List<UserRole> roles = new ArrayList<>();
        for (var role :
                userRoles) {
            roles.add(
                    new UserRole(
                            ROLE_ENUM.customValueOf(role.getAuthority())
                    )
            );
        }
        this.userRoles = roles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    // указывает, что свойство не нужно записывать. Значения под этой аннотацией
    // не записываются в базу данных (также не участвуют в сериализации).
    // static и final переменные экземпляра всегда transient.
    @Transient
    List<UserRole> userRoles;

    public User(String login, Rayon rayon) {
        this.login = login;
        this.active = 1L;
        this.date_last_entry = LocalDateTime.now();
        this.rayon = rayon;
    }

/*    public static String getRole(Authentication authentication) {
        String role = null;
        for (GrantedAuthority g :
                authentication.getAuthorities()) {
            if (g.getAuthority().equals(ROLE_ENUM.ROLE_UPDATE_IT.getString()) ||
                    g.getAuthority().equals(ROLE_ENUM.ROLE_READ_IT.getString())
            ) {
                role = "IT";
                break;
            }
            if (g.getAuthority().equals(ROLE_ENUM.ROLE_UPDATE_AXO.getString()) ||
                    g.getAuthority().equals(ROLE_ENUM.ROLE_READ_AXO.getString())
            ) {
                role = "AXO";
                break;
            }
        }

        return role;
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.login;
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
}
