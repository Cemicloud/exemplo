package br.com.cemicloud.exemplo.resource;

import br.com.cemicloud.exemplo.resource.dto.ExemploRequest;
import br.com.cemicloud.exemplo.resource.dto.ExemploResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/exemplos")
public interface ExemploResource {

    @GetMapping
    List<ExemploResponse> listar();

    @GetMapping("/{id}")
    ResponseEntity<ExemploResponse> consultarPorId(@PathVariable Long id);

    @PostMapping
    ResponseEntity<Void> cadastrar(@RequestBody ExemploRequest request);

    @PutMapping("/{id}")
    ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody ExemploRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remover(@PathVariable Long id);
}
