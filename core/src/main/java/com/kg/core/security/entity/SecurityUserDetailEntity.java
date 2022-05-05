package com.kg.core.security.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.kg.core.zuser.entity.ZUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    // 用户信息
    private ZUser zUser;

    // 用户权限列表
    private List<String> apiPermissions;

    // Security授权列表
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public SecurityUserDetailEntity(ZUser zUser, List<String> permLists) {
        this.zUser = zUser;
        this.apiPermissions = permLists;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        // 把权限标记字符串list，转换成security所需的list
        authorities = apiPermissions.stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
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
