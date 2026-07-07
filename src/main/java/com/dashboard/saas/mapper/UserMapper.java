package com.dashboard.saas.mapper;


import com.dashboard.saas.dtos.authentication.RegisterRequestDTO;
import com.dashboard.saas.dtos.authentication.RegisterResponseDTO;
import com.dashboard.saas.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //DTO → Entity //Yaha DTO se request aaye and entity me save hoo gaya ... register 1 step
    Users toEntity(RegisterRequestDTO request);

    //Entity->DTO //Yaha Entity me save hone ke badh Entity se get kar ke DTO se set hogaa
    // Imp --> Source target source means Entity me id field hai and dto me userId so source and target karna padega
    @Mapping(source = "id" ,target = "userId")
    RegisterResponseDTO toDTO(Users users);



}
