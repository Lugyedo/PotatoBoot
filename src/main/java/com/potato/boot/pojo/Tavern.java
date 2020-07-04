package com.potato.boot.pojo;

import com.potato.boot.validator.ContactNumberConstraint;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class Tavern implements Serializable {
    @NotEmpty(message = "门店GUID不能为空")
    private String tavernGuid;

    @NotEmpty(message = "门店名称不能为空")
    @Size(min = 1, max = 10, message = "门店名称长度必须为1到10")
    private String tavernName;

    @ContactNumberConstraint(message = "手机号格式不对")
    private String phone;
}
