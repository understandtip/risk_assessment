package com.yushang.risk.admin.domain.dto;

import com.yushang.risk.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class SecurityUser implements UserDetails {
  private User user;
  private List<String> permissions;
  private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (authorities != null) return authorities;
    if (permissions == null) return null;
    permissions.forEach(
        per -> {
          authorities.add(new SimpleGrantedAuthority(per));
        });
    return authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
