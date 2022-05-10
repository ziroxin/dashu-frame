package com.kg.core.base.converter;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

/**
 * @author ziro
 * @date 2022/5/10 22:06
 */
@MapperConfig(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BaseConverterConfig {

}
