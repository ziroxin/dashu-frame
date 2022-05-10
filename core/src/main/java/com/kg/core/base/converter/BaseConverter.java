package com.kg.core.base.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.kg.core.base.dto.BaseDTO;
import com.kg.core.base.model.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ziro
 * @date 2022/5/10 22:06
 */
public interface BaseConverter<E extends BaseEntity, DTO extends BaseDTO> {

    @InheritInverseConfiguration(name = "dtoToEntity")
    DTO entityToDto(E entity);

    E dtoToEntity(DTO dto);

    @InheritConfiguration(name = "dtoToEntity")
    E updateEntityFromDto(DTO dto, @MappingTarget E entity);

    default Page<DTO> pageableToDto(Page<E> page) {
        return page.map(this::entityToDto);
    }

    // =================== For mybatis plus page ================

    default IPage<DTO> mybatisPageToDto(IPage<E> page) {
        return page.convert(this::entityToDto);
    }

    /**
     * Convert mybatis-plus page object to spring data page domain.
     *
     * @param page mybatis-plus page info.
     * @return spring data page domain.
     */
    default Page<DTO> pageableToDto(IPage<E> page) {
        Sort sort = Sort.unsorted();

        // convert record.
        IPage<DTO> iPageDTO = page.convert(this::entityToDto);
        List<OrderItem> orderItems = page.orders();
        // when have order item.
        if (!CollectionUtils.isEmpty(orderItems)) {
            List<Order> orders = orderItems.stream()
                    .map(t -> t.isAsc() ? Order.asc(t.getColumn()) : Order.desc(t.getColumn()))
                    .collect(Collectors.toList());
            sort = Sort.by(orders);
        }

        // set pageable info
        int pageNumber = (int) (page.getCurrent() - 1);
        int pageSize = (int) page.getSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        List<DTO> content = iPageDTO.getRecords();

        return new PageImpl<>(content, pageable, iPageDTO.getTotal());
    }

    /**
     * Convert spring data Pageable to mybatis plus Page object.
     *
     * @param pageable spring data Pageable object
     * @return mybatis plus Page object.
     */
    default <T> IPage<T> pageableToMybatisPage(Pageable pageable) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        page.setCurrent(pageable.getPageNumber() + 1L);
        page.setSize(pageable.getPageSize());

        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            return page;
        }

        List<OrderItem> orderItems = sort.stream()
                .map(t -> t.isAscending() ? OrderItem.asc(t.getProperty()) : OrderItem.desc(t.getProperty()))
                .collect(Collectors.toList());

        page.addOrder(orderItems);

        return page;
    }

}
