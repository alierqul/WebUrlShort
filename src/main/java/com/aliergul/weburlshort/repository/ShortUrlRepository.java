package com.aliergul.weburlshort.repository;

import com.aliergul.weburlshort.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl,Long> {
    Optional<ShortUrl> findAllByCode(String code);
}
