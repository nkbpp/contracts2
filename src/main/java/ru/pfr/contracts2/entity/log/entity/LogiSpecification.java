package ru.pfr.contracts2.entity.log.entity;


import org.springframework.data.jpa.domain.Specification;
import ru.pfr.contracts2.entity.log.dto.ParamLog;

import java.time.LocalDate;

public class LogiSpecification {

    public static Specification<Logi> equalLogin(String login) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Logi_.USER), login);
    }

    public static Specification<Logi> equalLoginNull(String login) {
        return Specification.where(
                (login == null) ?
                        (root, query, criteriaBuilder) ->
                                criteriaBuilder.isTrue(criteriaBuilder.literal(true)) :
                        equalLogin(login)
        );
    }

    public static Specification<Logi> equalText(String text) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Logi_.TEXT), text);
    }

    public static Specification<Logi> equalType(String type) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Logi_.TYPE), type);
    }

    public static Specification<Logi> findByUser(String login, String type, String text) {
        return Specification.where(
                equalLoginNull(login)
                        .and(
                                text == null ? (root, query, criteriaBuilder) ->
                                        criteriaBuilder.isTrue(criteriaBuilder.literal(true)) :
                                        equalText(text)
                        )
                        .and(
                                type == null ? (root, query, criteriaBuilder) ->
                                        criteriaBuilder.isTrue(criteriaBuilder.literal(true)) :
                                        equalType(type)
                        )
        );
    }

    public static Specification<Logi> betweenDate(LocalDate dateBefore, LocalDate dateAfter) {
        return Specification.where(
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.between(root.get(Logi_.DATE), dateBefore, dateAfter)
        );
    }

    public static Specification<Logi> betweenDateIsNull(LocalDate dateBefore, LocalDate dateAfter) {
        return Specification.where(
                (dateBefore == null || dateAfter == null) ?
                        (root, query, criteriaBuilder) ->
                                criteriaBuilder.isTrue(criteriaBuilder.literal(true)) :
                        betweenDate(dateBefore, dateAfter)
        );
    }

    public static Specification<Logi> findByDateParam(ParamLog paramLog) {
        return Specification.where(
                betweenDateIsNull(paramLog.getDateBefore(), paramLog.getDateAfter())
                        .and(
                                findByUser(paramLog.getLogin(), paramLog.getType(), paramLog.getText())
                        )
        );
    }

}
