package br.com.ada.party.service;

import br.com.ada.party.exception.InvalidTypeException;
import br.com.ada.party.model.Document;
import br.com.ada.party.model.FinancialDocumentTypeValues;
import br.com.ada.party.model.Party;
import br.com.ada.party.model.PartyType;
import br.com.ada.party.repository.PartyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PartyService {
    private final PartyRepository partyRepository;

    public PartyService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    public Party saveParty(Party party) throws InvalidTypeException {
        String documentTypePattern = PartyType.FORNECEDOR.equals(party.getPartyType())
                ? FinancialDocumentTypeValues.CNPJ.name()
                : String.format("%s|%s", FinancialDocumentTypeValues.CPF, FinancialDocumentTypeValues.RG);
        if (validatePartyDocumentType(party, documentTypePattern))
            return partyRepository.save(party);

        throw new InvalidTypeException();
    }

    private boolean validatePartyDocumentType(Party party, String documentTypePattern) {
        Pattern p = Pattern.compile(documentTypePattern);
        boolean found = false;
        for (Document s : party.getDocuments()) {
            if (p.matcher(s.getType().name()).find()) {
                found = true;
                break;
            }
        }
        return found;
    }

    public Optional<Party> getParty(Long id) {
        return partyRepository.findById(id);
    }

    public void deleteParty(Long id) {
        partyRepository.deleteById(id);
    }

    public Party updateParty(Long id, Party p) {
        Optional<Party> party = getParty(id);
        if (party.isPresent()) {
            party.get().setDate(p.getDate());
            party.get().setName(p.getName());
            return partyRepository.save(party.get());
        } else {
            throw new EntityNotFoundException();
        }
    }
}
