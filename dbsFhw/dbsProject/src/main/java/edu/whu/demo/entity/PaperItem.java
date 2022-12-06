package edu.whu.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author 孔德昱
 * @date 2022/12/6 14:54 星期二
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description="论文实体")
public class PaperItem {
    @Id
    @ApiModelProperty("待办事项编号")
    long id;

    @ApiModelProperty("待办事项名称")
    String name;

    @ApiModelProperty("是否完成")
    boolean complete;
}