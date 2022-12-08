package edu.whu.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
/**
 * @author dzf
 * @date 2022/12/8 15:55
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description="买书联系的实体")
public class BuyerItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("买书id")
    long BuyerId;
    @ApiModelProperty("图书id")
    long bookId;
    @ApiModelProperty("买书人id")
    long userId;


}
