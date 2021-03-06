package ru.pfr.contracts2.entity.contracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
public class MyDocuments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "dokument", columnDefinition = "LONGBLOB", nullable = true)
    private byte[] dokument;
    @Column(name = "namefile", nullable = true/*, length = 400*/)
    private String nameFile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    public MyDocuments(byte[] dokument, String nameFile) {
        this.dokument = dokument;
        this.nameFile = nameFile;
    }

    @JsonIgnore
    public Contract getContract() {
        return contract;
    }
}
