package com.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by lizhihao on 2018/3/11.
 */
@Data
@ApiModel(value = "gif请求模型")
public class Form {
    @ApiModelProperty(value = "模板名称", example = "sorry")
    private String templateName;
    @ApiModelProperty(value = "记录id", example = "541345831815479296")
    private String sentence;
    @ApiModelProperty(value = "图片大小模式 表情包模式:sample 大图:normal", example = "sample")
    private String mode;
    @ApiModelProperty(value = "上传的图片 如果获取的人脸有3张,但是填的只有2张可以将不需要填的传一个空的png")
    private List<MultipartFile> imageList;
}
