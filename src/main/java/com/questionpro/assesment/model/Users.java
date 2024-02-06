package com.questionpro.assesment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users {

  @Id
  private String username;
  private String password;
  private Boolean enabled;

  @OneToMany(mappedBy = "user")
  private List<GroceryOrder> orders;
}
