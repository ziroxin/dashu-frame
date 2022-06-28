package com.kg.module.canorder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 发票表
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
@TableName("can_order_bill")
@ApiModel(value = "CanOrderBill对象", description = "发票表")
public class CanOrderBill implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单发票ID")
    private String orderBillId;

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("单位名称")
    private String companyName;

    @ApiModelProperty("纳税人识别号")
    private String taxId;

    @ApiModelProperty("单位地址")
    private String companyAdress;

    @ApiModelProperty("单位联系电话")
    private String companyPhone;

    @ApiModelProperty("开户行")
    private String bank;

    @ApiModelProperty("银行账号")
    private String bankAccount;

    @ApiModelProperty("开票金额")
    private String invoiceAmount;

    @ApiModelProperty("添加时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime updateTime;

    public String getOrderBillId() {
        return orderBillId;
    }

    public void setOrderBillId(String orderBillId) {
        this.orderBillId = orderBillId;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
    public String getCompanyAdress() {
        return companyAdress;
    }

    public void setCompanyAdress(String companyAdress) {
        this.companyAdress = companyAdress;
    }
    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
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
        return "CanOrderBill{" +
            "orderBillId=" + orderBillId +
            ", orderId=" + orderId +
            ", companyName=" + companyName +
            ", taxId=" + taxId +
            ", companyAdress=" + companyAdress +
            ", companyPhone=" + companyPhone +
            ", bank=" + bank +
            ", bankAccount=" + bankAccount +
            ", invoiceAmount=" + invoiceAmount +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
