package br.com.ada.party.controller;

import br.com.ada.party.exception.InvalidTypeException;
import br.com.ada.party.model.Party;
import br.com.ada.party.service.DocumentService;
import br.com.ada.party.service.PartyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/party")
@Slf4j
public class PartyController {
    private final PartyService partyService;
    private final DocumentService documentService;

    public PartyController(PartyService partyService, DocumentService documentService) {
        this.partyService = partyService;
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<Party> createParty(@Valid @RequestBody Party party) throws InvalidTypeException {
        log.info("Create Party [POST] {}", party);
        documentService.saveDocument(party.getDocuments());
        return ResponseEntity.ok(partyService.saveParty(party));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Party>> getParty(@PathVariable Long id) {
        Optional<Party> party = partyService.getParty(id);
        if (party.isPresent()) {
            log.info("Get Party [GET] {}", party);
            return ResponseEntity.ok(party);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        Optional<Party> party = partyService.getParty(id);
        if (party.isPresent()) {
            log.info("Delete Party [DELETE] {}", party);
            partyService.deleteParty(id);
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Party> updateParty(@PathVariable Long id, @Valid @RequestBody Party party) {
        log.info("Update Party [PATCH] {}", party);
        return ResponseEntity.ok(partyService.updateParty(id, party));
    }
}
