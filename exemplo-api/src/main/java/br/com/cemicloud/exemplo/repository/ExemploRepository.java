package br.com.cemicloud.exemplo.repository;

import br.com.cemicloud.exemplo.model.Exemplo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemploRepository extends JpaRepository<Exemplo, Long> {
}
