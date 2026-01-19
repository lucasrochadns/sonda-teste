package br.com.sonda.aeronave.services;


import br.com.sonda.aeronave.AeronaveApplication;
import br.com.sonda.aeronave.domain.model.Aeronave;
import br.com.sonda.aeronave.dto.AeronaveDTO;
import br.com.sonda.aeronave.repository.AeronaveRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AeronaveService {


    private final AeronaveRepository aeronaveRepository;

    @Transactional(readOnly = true)
    public Page<AeronaveDTO> findAll(Pageable pageable){
        return aeronaveRepository.findAll(pageable).map(AeronaveDTO::from);
    }

    @Transactional(readOnly = true)
    public List<AeronaveDTO> find(String termo){
        return aeronaveRepository.findByTermo(termo).stream().map(AeronaveDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public AeronaveDTO findById(Long id){
        return AeronaveDTO.from(aeronaveRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Aeronave Não Encontrada: " + id)));
    }

    @Transactional
    public AeronaveDTO save(Aeronave aeronave){
        return AeronaveDTO.from(aeronaveRepository.save(aeronave));
    }

    @Transactional
    public AeronaveDTO updateById(Long id, Aeronave aeronave){
        return AeronaveDTO.from(aeronaveRepository.save(aeronave));
    }
}
