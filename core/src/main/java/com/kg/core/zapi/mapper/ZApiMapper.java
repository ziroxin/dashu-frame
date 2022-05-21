package com.kg.core.zapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kg.core.zapi.dto.ApiUserIdDTO;
import com.kg.core.zapi.entity.ZApi;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * API信息表 Mapper 接口
 * </p>
 *
 * @author ziro
 * @since 2022-05-05
 */
@Repository
public interface ZApiMapper extends BaseMapper<ZApi> {

    List<ApiUserIdDTO> listAllApiForUserId();
}
