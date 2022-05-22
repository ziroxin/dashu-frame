package com.kg.core.zapi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * <p>
 * API信息表
 * </p>
 *
 * @author ziro
 * @since 2022-05-05
 */
@TableName("z_api")
@ApiModel(value = "ZApi对象", description = "API信息表")
@ToString
public class ZApi implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("API ID")
    @TableId
    private String apiId;

    @ApiModelProperty("API分组ID")
    private String apiGroupId;

    @ApiModelProperty("API名称")
    private String apiName;

    @ApiModelProperty("API标记")
    private String apiPermission;

    @ApiModelProperty("API请求地址")
    private String apiRequestUrl;

    @ApiModelProperty("API请求方法（GET、POST、PUT、DELETE等）")
    private String apiRequestMethod;

    @ApiModelProperty("API描述")
    private String apiDescription;

    @ApiModelProperty("类名")
    private String apiClassName;

    @ApiModelProperty("方法名")
    private String apiMethodName;

    @ApiModelProperty("API顺序")
    private Integer apiOrder;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime updateTime;

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiGroupId() {
        return apiGroupId;
    }

    public void setApiGroupId(String apiGroupId) {
        this.apiGroupId = apiGroupId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiPermission() {
        return apiPermission;
    }

    public void setApiPermission(String apiPermission) {
        this.apiPermission = apiPermission;
    }

    public String getApiRequestUrl() {
        return apiRequestUrl;
    }

    public void setApiRequestUrl(String apiRequestUrl) {
        this.apiRequestUrl = apiRequestUrl;
    }

    public String getApiRequestMethod() {
        return apiRequestMethod;
    }

    public void setApiRequestMethod(String apiRequestMethod) {
        this.apiRequestMethod = apiRequestMethod;
    }

    public String getApiDescription() {
        return apiDescription;
    }

    public void setApiDescription(String apiDescription) {
        this.apiDescription = apiDescription;
    }

    public String getApiClassName() {
        return apiClassName;
    }

    public void setApiClassName(String apiClassName) {
        this.apiClassName = apiClassName;
    }

    public String getApiMethodName() {
        return apiMethodName;
    }

    public void setApiMethodName(String apiMethodName) {
        this.apiMethodName = apiMethodName;
    }

    public Integer getApiOrder() {
        return apiOrder;
    }

    public void setApiOrder(Integer apiOrder) {
        this.apiOrder = apiOrder;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ZApi{" +
                "apiId=" + apiId +
                ", apiGroupId=" + apiGroupId +
                ", apiName=" + apiName +
                ", apiPermission=" + apiPermission +
                ", apiRequestUrl=" + apiRequestUrl +
                ", apiRequestMethod=" + apiRequestMethod +
                ", apiDescription=" + apiDescription +
                ", apiClassName=" + apiClassName +
                ", apiMethodName=" + apiMethodName +
                ", apiOrder=" + apiOrder +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
