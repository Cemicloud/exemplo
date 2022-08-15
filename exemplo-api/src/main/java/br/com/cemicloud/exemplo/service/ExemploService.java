package br.com.cemicloud.exemplo.service;

import br.com.cemicloud.exemplo.exception.BusinessException;
import br.com.cemicloud.exemplo.mapper.ExemploMapper;
import br.com.cemicloud.exemplo.model.Exemplo;
import br.com.cemicloud.exemplo.repository.ExemploRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExemploService {

    private final ExemploRepository repository;
    private final ExemploMapper mapper;

    public List<Exemplo> listar() {
        return repository.findAll();
    }

    public Optional<Exemplo> consultarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Exemplo cadastrar(Exemplo exemplo) {
        return repository.save(exemplo);
    }

    @Transactional
    public void atualizar(Long id, Exemplo exemplo) {
        Exemplo exemploAtualizar = consultarPorId(id)
                .orElseThrow(() -> new BusinessException("Exemplo não encontrado"));
        mapper.atualizar(exemploAtualizar, exemplo);
        repository.save(exemploAtualizar);
    }

    @Transactional
    public void remover(Long id) {
        Exemplo exemplo = consultarPorId(id)
                .orElseThrow(() -> new BusinessException("Exemplo não encontrado"));
        repository.delete(exemplo);
    }
}
