package org.example.restaurantmanagementapplication.model.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.restaurantmanagementapplication.entity.Role;
import org.example.restaurantmanagementapplication.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOut {

  private Integer id;
  private String email;
  private String[] roles;

  public static UserOut fromEntity(final User user) {
    return new UserOut(
        user.getId(),
        user.getEmail(),
        user.getRoles().stream()
            .map(Role::getName)
            .toArray(String[]::new)
    );
  }
}
