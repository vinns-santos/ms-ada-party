package br.com.ada.party.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Slf4j
public class Party {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Nome não pode ser vazio")
    private String name;

    @Column(columnDefinition = "DATE")
    @NotNull(message = "Data não pode ser vazia")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @OneToMany(fetch = FetchType.LAZY)
    @NotNull(message = "Documentos não podem ser nulos")
    private List<Document> documents;

    @NotNull(message = "Tipo da parte não pode ser nulo")
    private PartyType partyType;
}
