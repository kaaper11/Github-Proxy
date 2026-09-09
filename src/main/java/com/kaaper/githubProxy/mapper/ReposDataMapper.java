package com.kaaper.githubProxy.mapper;

import com.kaaper.githubProxy.dto.ReposDataDto;
import com.kaaper.githubProxy.model.ReposData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReposDataMapper {

    ReposDataDto toDto(ReposData reposData);

    @Mapping(target = "id", ignore = true)
    ReposData fromDto(ReposDataDto reposDataDto, String owner, String repositoryName);
}
