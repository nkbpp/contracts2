package ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.MonthsDto;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.Months;


@Component
@RequiredArgsConstructor
public class MonthsMapper {

    public MonthsDto toDto(Months obj) {
        return MonthsDto.builder()
                .month1(obj.getMonth1())
                .month2(obj.getMonth2())
                .month3(obj.getMonth3())
                .month4(obj.getMonth4())
                .month5(obj.getMonth5())
                .month6(obj.getMonth6())
                .month7(obj.getMonth7())
                .month8(obj.getMonth8())
                .month9(obj.getMonth9())
                .month10(obj.getMonth10())
                .month11(obj.getMonth11())
                .month12(obj.getMonth12())
                .build();
    }

    public Months fromDto(MonthsDto dto) {
        return Months.builder()
                .month1(dto.getMonth1())
                .month2(dto.getMonth2())
                .month3(dto.getMonth3())
                .month4(dto.getMonth4())
                .month5(dto.getMonth5())
                .month6(dto.getMonth6())
                .month7(dto.getMonth7())
                .month8(dto.getMonth8())
                .month9(dto.getMonth9())
                .month10(dto.getMonth10())
                .month11(dto.getMonth11())
                .month12(dto.getMonth12())
                .build();
    }

}
