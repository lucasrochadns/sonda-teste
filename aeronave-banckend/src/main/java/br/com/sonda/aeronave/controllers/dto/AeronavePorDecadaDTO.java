package br.com.sonda.aeronave.controllers.dto;

import java.util.List;

public record AeronavePorDecadaDTO(
        Integer anoFabricacao,
        List<AeronaveDTO> aeronaveDTO
) {
}
