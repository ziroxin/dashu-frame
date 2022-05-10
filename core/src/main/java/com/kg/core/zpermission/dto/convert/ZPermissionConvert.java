package com.kg.core.zpermission.dto.convert;

import com.kg.core.base.converter.BaseConverter;
import com.kg.core.base.converter.BaseConverterConfig;
import com.kg.core.zpermission.dto.ZPermissionDTO;
import com.kg.core.zpermission.entity.ZPermission;
import org.mapstruct.Mapper;

/**
 * @author ziro
 * @date 2022/5/10 22:05
 */
@Mapper(config = BaseConverterConfig.class)
public interface ZPermissionConvert extends BaseConverter<ZPermission, ZPermissionDTO> {
}
