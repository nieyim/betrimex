package com.example.betrimex.mapper;

import com.example.betrimex.model.User;
import com.example.betrimex.model.dto.request.UserRequest;
import com.example.betrimex.model.dto.response.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUserEntity(UserRequest userUpdateRequest, @MappingTarget User user);
}
