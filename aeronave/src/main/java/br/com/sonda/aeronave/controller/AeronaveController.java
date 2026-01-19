package br.com.sonda.aeronave.controller;

import br.com.sonda.aeronave.dto.AeronaveDTO;
import br.com.sonda.aeronave.services.AeronaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aeronaves")
@RequiredArgsConstructor
public class AeronaveController {

   @Autowired
   private AeronaveService aeronaveService;

     @GetMapping
     public ResponseEntity<Page<AeronaveDTO>> findAll(@PageableDefault(size = 12) Pageable pageable){
         return ResponseEntity.ok().body(aeronaveService.findAll(pageable));
     }

     @GetMapping("/find")
     public ResponseEntity<List<AeronaveDTO>> find(@RequestParam String termo){
         return ResponseEntity.ok().body(aeronaveService.find(termo));
     }

     @GetMapping("/{id}")
    public ResponseEntity<AeronaveDTO> findById(@PathVariable Long id){
         return ResponseEntity.ok().body(aeronaveService.findById(id));
     }

}
