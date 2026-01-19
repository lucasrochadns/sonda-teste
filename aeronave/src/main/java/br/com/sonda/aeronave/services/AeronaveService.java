package br.com.sonda.aeronave.services;


import br.com.sonda.aeronave.domain.model.Aeronave;
import br.com.sonda.aeronave.dto.AeronaveDTO;
import br.com.sonda.aeronave.dto.AeronavePorDecadaDTO;
import br.com.sonda.aeronave.dto.AeronavePorFabricanteDTO;
import br.com.sonda.aeronave.repository.AeronaveRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AeronaveService {


    private final AeronaveRepository aeronaveRepository;

    @Transactional(readOnly = true)
    public Page<Aeronave> findAll(Pageable pageable) {
        return aeronaveRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Aeronave> find(String termo) {
        return aeronaveRepository.findByTermo(termo);
    }

    @Transactional(readOnly = true)
    public Aeronave findById(Long id) {
        return aeronaveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aeronave Não Encontrada: " + id));
    }

    @Transactional
    public Aeronave save(Aeronave aeronave) {
        return aeronaveRepository.save(aeronave);
    }

    @Transactional
    public Aeronave updateById(Long id, Aeronave aeronave) {
        return aeronaveRepository.save(aeronave);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!aeronaveRepository.existsById(id)) {
            throw new EntityNotFoundException("Aeronave Não Encontrada: " + id);
        }
        aeronaveRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Aeronave> findByNaoVendido(){
      return aeronaveRepository.findByVendidoFalse();
    }

    @Transactional(readOnly = true)
    public List<AeronavePorDecadaDTO> findByAnoFabricacao(){
        return aeronaveRepository.findAllByOrderByAnoFabricacaoAsc()
                .stream().collect(Collectors.groupingBy(
                        a -> (a.getAnoFabricacao() / 10) * 10,
                        LinkedHashMap::new, Collectors.mapping(AeronaveDTO::from, Collectors.toList())
                )).entrySet()
                .stream()
                .map(x -> new AeronavePorDecadaDTO(x.getKey(), x.getValue())).toList();
    }

    @Transactional(readOnly = true)
    public List<AeronavePorFabricanteDTO> findByFabricante(){
        return aeronaveRepository.findAllByOrderByFabricanteAsc()
                .stream().collect(Collectors.groupingBy(
                        Aeronave::getFabricante,
                        LinkedHashMap::new, Collectors.mapping(AeronaveDTO::from, Collectors.toList())
                )).entrySet().stream()
                .map(x -> new AeronavePorFabricanteDTO(x.getKey(), x.getValue())).toList();
    }

    @Transactional(readOnly = true)
    public List<AeronaveDTO> findRecent(){
        return aeronaveRepository.findRecent(OffsetDateTime.now().minusDays(7))
                .stream().map(AeronaveDTO::from).toList();
    }

    @Transactional
    public AeronaveDTO patch(Long id, AeronaveDTO aeronaveDTO){
        Aeronave aeronave = findById(id);
        if(aeronaveDTO.fabricante() != null) aeronave.setFabricante(aeronaveDTO.fabricante());
        if(aeronaveDTO.nome() != null) aeronave.setNome(aeronaveDTO.nome());
        if(aeronaveDTO.anoFabricacao() != null) aeronave.setAnoFabricacao(aeronaveDTO.anoFabricacao());
        if(aeronaveDTO.vendido() != null) aeronave.setVendido(aeronaveDTO.vendido());
        aeronave.setUpdateAt(OffsetDateTime.now());
        return AeronaveDTO.from(aeronaveRepository.save(aeronave));
    }
}
