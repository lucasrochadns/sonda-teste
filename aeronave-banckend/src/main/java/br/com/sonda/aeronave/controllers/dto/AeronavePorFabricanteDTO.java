package br.com.sonda.aeronave.controllers.dto;

import br.com.sonda.aeronave.domain.model.Fabricante;

import java.util.List;

public record AeronavePorFabricanteDTO(
        Fabricante fabricante,
        List<AeronaveDTO> aeronaveDTO
) {
}
