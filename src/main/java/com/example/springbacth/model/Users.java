package com.example.springbacth.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

  @Id
  private Integer id;
  private String name;
  private String dept;
  private Integer salary;
  private Date timestamp;
}
