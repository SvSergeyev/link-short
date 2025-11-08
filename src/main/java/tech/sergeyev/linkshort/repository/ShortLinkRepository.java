package tech.sergeyev.linkshort.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tech.sergeyev.linkshort.model.ShortLink;

import java.time.LocalDateTime;
import java.util.List;

public interface ShortLinkRepository extends CrudRepository<ShortLink, Long> {
    ShortLink getShortLinkByToken(String token);

    ShortLink getShortLinkByUrl(String url);

    boolean existsShortLinkByToken(String token);

    @Query(value = "SELECT l FROM ShortLink l WHERE l.createdAt <= :expirationDate")
    List<ShortLink> findAllObsoleteShortLinks(@Param("expirationDate") LocalDateTime expirationDate);
}
