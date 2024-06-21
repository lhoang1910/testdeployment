package com.demo.teststttstts.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity {

    @Id
    private String id;
    private String userCode;
    private String username;
    private String password;
    private Integer role;
    private Boolean deleted;

}
