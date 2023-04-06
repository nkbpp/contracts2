package ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.pfr.contracts2.global.MyNumbers;

import javax.persistence.*;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
public class NaturalIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "contractit_id")
    private ContractAxo contractIT;

    public NaturalIndicator(Double sum) {
        this.sum = sum;
    }

    @JsonIgnore
    public ContractAxo getContractIT() {
        return contractIT;
    }

    public String getSumOk() {
        return MyNumbers.okrug(sum);
    }
}
