package ru.pfr.contracts2.repository.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.log.entity.Logi;


public interface LogiRepository extends JpaRepository<Logi, Long>, JpaSpecificationExecutor<Logi> {
/*    Optional<Logi> findByUser(String login);

    @Query(
            value = "SELECT l.id, l.datelog, l.user, l.type, l.text FROM Logi l " +
                    "WHERE ((?1 is null and ?2 is null) " +
                    "or (l.datelog between ?1 and ?2)) and " +
                    "(?3 is null or l.user=?3) and " +
                    "(?4 is null or l.type=?4) and " +
                    "(?5 is null or l.text=?5)",
            nativeQuery = true)
    List<Logi> findByDateParam(Date d1, Date d2, String user, String l, String text);

    @Query(
            value = "SELECT l.id, l.datelog, l.user, l.type, l.text " +
                    "FROM Logi l " +
                    "WHERE " +
                    "(?1 is null or l.user=?1) and " +
                    "(?2 is null or l.type=?2) and " +
                    "(?3 is null or l.text=?3)",
            nativeQuery = true)
    List<Logi> findByUser(String user, String l, String text);*/

}

