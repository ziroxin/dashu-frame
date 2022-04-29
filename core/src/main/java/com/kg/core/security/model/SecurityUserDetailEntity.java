package com.kg.core.security.model;

import lombok.Getter;
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
public class SecurityUserDetailEntity implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return "$2a$10$.T5HhNJ862boE7A6nWRM/O3wNG9VlL0mdzTiSI9W9adwI5No49jmy";
    }

    @Override
    public String getUsername() {
        return "user";
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
