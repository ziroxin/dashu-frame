package com.kg.core.zpermission.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.kg.core.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 资源权限表
 * </p>
 *
 * @author ziro
 * @since 2022-05-09
 */
@TableName("z_permission")
@ApiModel(value = "ZPermission对象", description = "资源权限表")
public class ZPermission implements BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("资源ID")
    @TableId(value = "permission_id", type = IdType.ASSIGN_UUID)
    private String permissionId;

    @ApiModelProperty("父级ID")
    private String parentId;

    @ApiModelProperty("资源名称")
    private String permissionName;

    @ApiModelProperty("资源描述")
    private String permissionDescription;

    @ApiModelProperty("资源类型（0路由1页面资源2外链3其他）")
    private String permissionType;

    @ApiModelProperty("资源标题")
    private String permissionTitle;

    @ApiModelProperty("资源图标")
    private String permissionIcon;

    @ApiModelProperty("资源标记（路由/外链/标记）")
    private String permissionRouter;

    @ApiModelProperty("组件地址")
    private String permissionComponent;

    @ApiModelProperty("自定义配置（JSON）")
    private String permissionConfig;

    @ApiModelProperty("是否可见（0隐藏1显示）")
    private String permissionIsShow;

    @ApiModelProperty("是否禁用（0禁用1启用）")
    private String permissionIsEnabled;

    @ApiModelProperty("资源顺序")
    private Integer permissionOrder;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime updateTime;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionTitle() {
        return permissionTitle;
    }

    public void setPermissionTitle(String permissionTitle) {
        this.permissionTitle = permissionTitle;
    }

    public String getPermissionIcon() {
        return permissionIcon;
    }

    public void setPermissionIcon(String permissionIcon) {
        this.permissionIcon = permissionIcon;
    }

    public String getPermissionRouter() {
        return permissionRouter;
    }

    public void setPermissionRouter(String permissionRouter) {
        this.permissionRouter = permissionRouter;
    }

    public String getPermissionConfig() {
        return permissionConfig;
    }

    public void setPermissionConfig(String permissionConfig) {
        this.permissionConfig = permissionConfig;
    }

    public String getPermissionIsShow() {
        return permissionIsShow;
    }

    public void setPermissionIsShow(String permissionIsShow) {
        this.permissionIsShow = permissionIsShow;
    }

    public String getPermissionIsEnabled() {
        return permissionIsEnabled;
    }

    public void setPermissionIsEnabled(String permissionIsEnabled) {
        this.permissionIsEnabled = permissionIsEnabled;
    }

    public Integer getPermissionOrder() {
        return permissionOrder;
    }

    public void setPermissionOrder(Integer permissionOrder) {
        this.permissionOrder = permissionOrder;
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

    public String getPermissionComponent() {
        return permissionComponent;
    }

    public void setPermissionComponent(String permissionComponent) {
        this.permissionComponent = permissionComponent;
    }

    @Override
    public String toString() {
        return "ZPermission{" +
                "permissionId=" + permissionId +
                ", parentId=" + parentId +
                ", permissionName=" + permissionName +
                ", permissionDescription=" + permissionDescription +
                ", permissionType=" + permissionType +
                ", permissionTitle=" + permissionTitle +
                ", permissionIcon=" + permissionIcon +
                ", permissionRouter=" + permissionRouter +
                ", permissionComponent=" + permissionComponent +
                ", permissionConfig=" + permissionConfig +
                ", permissionIsShow=" + permissionIsShow +
                ", permissionIsEnabled=" + permissionIsEnabled +
                ", permissionOrder=" + permissionOrder +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
