package br.com.cemicloud.exemplo.mapper;

import br.com.cemicloud.exemplo.model.Exemplo;
import br.com.cemicloud.exemplo.resource.dto.ExemploRequest;
import br.com.cemicloud.exemplo.resource.dto.ExemploResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExemploMapper {

    @Mapping(source = "id", target = "id", ignore = true)
    void atualizar(@MappingTarget Exemplo atualizar, Exemplo visita);

    ExemploResponse toResponse(Exemplo exemplo);

    Exemplo toModel(ExemploRequest request);
}
