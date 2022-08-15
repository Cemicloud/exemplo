package br.com.cemicloud.exemplo.resource.impl;

import br.com.cemicloud.exemplo.mapper.ExemploMapper;
import br.com.cemicloud.exemplo.model.Exemplo;
import br.com.cemicloud.exemplo.resource.ExemploResource;
import br.com.cemicloud.exemplo.resource.dto.ExemploRequest;
import br.com.cemicloud.exemplo.resource.dto.ExemploResponse;
import br.com.cemicloud.exemplo.service.ExemploService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ExemploResourceImpl implements ExemploResource {

    private final ExemploService service;
    private final ExemploMapper mapper;

    @Override
    public List<ExemploResponse> listar() {
        return service.listar().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ExemploResponse> consultarPorId(Long id) {
        return service.consultarPorId(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> cadastrar(ExemploRequest request) {
        Exemplo exemplo = service.cadastrar(mapper.toModel(request));
        return ResponseEntity.created(URI.create("/exemplos/" + exemplo.getId())).build();
    }

    @Override
    public ResponseEntity<Void> atualizar(Long id, ExemploRequest request) {
        service.atualizar(id, mapper.toModel(request));
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> remover(Long id) {
        service.remover(id);
        return ResponseEntity.accepted().build();
    }
}
