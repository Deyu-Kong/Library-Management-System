package edu.whu.demo.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description="用户实体")
@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    long id;

    @NotNull
    String name;

    private String password;

    private Integer age;

    private String tel;

    //该字段不存数据库
    @Transient
    private boolean online;

    /**
     * 多对多关联。会自动创建关联表
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Role> roles = new java.util.ArrayList<>();


}
