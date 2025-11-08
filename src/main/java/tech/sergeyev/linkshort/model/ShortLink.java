package tech.sergeyev.linkshort.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ShortLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    long id;
    @Column(nullable = false, length = 3000)
    String url;
    @Column(nullable = false, unique = true)
    String token;
    @CreatedDate
    LocalDateTime createdAt;

    public ShortLink(String url, String token) {
        this.url = url;
        this.token = token;
    }
}
