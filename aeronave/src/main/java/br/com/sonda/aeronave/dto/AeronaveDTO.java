package br.com.sonda.aeronave.dto;

import br.com.sonda.aeronave.domain.model.Aeronave;
import br.com.sonda.aeronave.domain.model.Fabricante;


import java.time.OffsetDateTime;

public record AeronaveDTO (
        Long id,
        String nome,
        Fabricante fabricante,
        Integer anoFabricacao,
        String descricao,
        Boolean vendido,
        OffsetDateTime createdAt,
        OffsetDateTime updateAt
) {


    public static AeronaveDTO from(Aeronave aeronave){
        return new AeronaveDTO(aeronave.getId(), aeronave.getNome(),
                aeronave.getFabricante(), aeronave.getAnoFabricacao(),
                aeronave.getDescricao(), aeronave.getVendido(),
                aeronave.getCreatedAt(), aeronave.getUpdateAt());
    }

    public static Aeronave to(AeronaveDTO aeronaveDTO){
        return new Aeronave(aeronaveDTO.id, aeronaveDTO.nome,
                aeronaveDTO.fabricante, aeronaveDTO.anoFabricacao,
                aeronaveDTO.descricao, aeronaveDTO.vendido,
                aeronaveDTO.createdAt, aeronaveDTO.updateAt);
    }
}
