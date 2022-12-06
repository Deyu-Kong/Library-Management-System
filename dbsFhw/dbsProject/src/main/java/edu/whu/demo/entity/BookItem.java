package edu.whu.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description="图书实体")
public class BookItem {

    @Id
    @ApiModelProperty("图书编号")
    long id;

    @ApiModelProperty("书名")
    String bookName;

    @ApiModelProperty("出版日期")
    Date publicationDate;

    @ApiModelProperty("作者")
    String authorName;

    @ApiModelProperty("出版商")
    String publisherName;

//    @ApiModelProperty("是否完成")
//    boolean complete;

}
