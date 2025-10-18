package com.webapp.websiteportal.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.webapp.websiteportal.entity.Users;

//@Mapper(componentModel = "spring")
//public interface UserMapper {
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    public void updateUser(Users source, @MappingTarget Users target);
//}

@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "authorities", ignore = true)  // skip authorities field
    void updateUser(Users source, @MappingTarget Users target);
}
