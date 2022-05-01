package com.kg.core.security.entity;

import com.kg.core.zuser.entity.ZUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Security 用户实体
 *
 * @author ziro
 * @date 2022/4/27 22:27
 */
@Getter
@Setter
@NoArgsConstructor
public class SecurityUserDetailEntity implements UserDetails {

    public SecurityUserDetailEntity(ZUser zUser) {
        this.zUser = zUser;
    }

    private ZUser zUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return zUser.getPassword();
    }

    @Override
    public String getUsername() {
        return zUser.getUserName();
    }

    /**
     * 是否在有效期内
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证是否未超时
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
