package br.com.sonda.aeronave.repository;

import br.com.sonda.aeronave.AeronaveApplication;
import br.com.sonda.aeronave.domain.model.Aeronave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AeronaveRepository extends JpaRepository<Aeronave, Long> {



    @Query("""
   select a from Aeronave a
   where
     (
       :termo is not null and
       (
         lower(a.nome) like lower(concat('%', :termo, '%'))
         or lower(a.descricao) like lower(concat('%', :termo, '%'))
         or cast(a.fabricante as string) like concat('%', :termo, '%')
         or cast(a.anoFabricacao as string) = :termo
         or cast(a.id as string) = :termo
       )
     )
""")
    List<Aeronave> findByTermo(@Param("termo") String termo);

    List<Aeronave> findByVendidoFalse();

    List<Aeronave> findAllByOrderByAnoFabricacaoAsc();
}
