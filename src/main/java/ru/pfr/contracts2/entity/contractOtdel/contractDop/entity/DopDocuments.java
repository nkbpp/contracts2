package ru.pfr.contracts2.entity.contractOtdel.contractDop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
@Table(name = "it_documents")
public class DopDocuments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "dokument", columnDefinition = "LONGBLOB")
    private byte[] dokument;
    @Column(name = "namefile")
    private String nameFile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractIT_id")
    private ContractDop contractIT;

    public DopDocuments(byte[] dokument, String nameFile) {
        this.dokument = dokument;
        this.nameFile = nameFile;
    }

    @JsonIgnore
    public ContractDop getContractIT() {
        return contractIT;
    }
}
