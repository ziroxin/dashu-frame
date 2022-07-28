package com.kg.module.canapi.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yrt
 * @date 2022/7/26 19:44
 */
@Getter
@Setter
public class CanOrderBillTextDTO {

    private String companyName;

    private String taxId;

    private String companyAdress;

    private String companyPhone;

    private String bank;

    private String bankAccount;

    private Object textData;
}
