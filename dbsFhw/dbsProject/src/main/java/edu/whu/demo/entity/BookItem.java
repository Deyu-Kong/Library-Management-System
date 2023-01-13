package edu.whu.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description="图书实体")
public class BookItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ApiModelProperty("评分")
    @Min(0)
    @Max(10)
    Double rating;

    @ApiModelProperty("图片链接")
    String imgUrl;

//    @ApiModelProperty("是否完成")
//    boolean complete;

}
