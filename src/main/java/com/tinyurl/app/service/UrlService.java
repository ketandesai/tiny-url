package com.tinyurl.app.service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import com.tinyurl.app.repository.URLRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import com.tinyurl.app.entity.UrlEntity;
import com.tinyurl.app.model.LongUrlRequest;
import com.google.common.primitives.Longs;

@Service
@Slf4j
public class UrlService implements UrlServiceIF {
    URLRepository urlRepository;

    public UrlService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    /**
     * Convert the long (original url) to a short url
     * Saves the url to the database,  the converts the auto-generated
     * id to a base 62 number.
     */
    @Override
    public String convertToTinyUrl(LongUrlRequest request) {
        var urlToSave = UrlEntity.builder()
                                           .originalUrl(request.getOriginalUrl())
                                           .startDate(LocalDateTime.now())
                                           .endDate(request.getEndDate())
                                           .build();
        urlRepository.save(urlToSave);
        byte[] bytesId = Longs.toByteArray(urlToSave.getId());
        String tinyUrl = Base64Utils.encodeToUrlSafeString(bytesId);
        log.debug("converting id = {} to tinyUrl = {}", urlToSave.getId(), tinyUrl);
        return tinyUrl;
    }

    /**
     * Get the original url from the tinyUrl hash
     * @param tinyUrl, the tinyUrl hash
     * @return
     */
    @Override
    public ResponseEntity<Void> getOriginalUrl(String tinyUrl) {
        byte[] byteId = Base64Utils.decodeFromUrlSafeString(tinyUrl);
        long longId = Longs.fromByteArray(byteId);
        Optional<UrlEntity> opt = urlRepository.findById(String.valueOf(longId));
        if (opt.isPresent()) {
            UrlEntity entity = opt.get();
            if (entity.getEndDate() != null && LocalDateTime.now().isAfter(entity.getEndDate()) ) {
                log.debug("Link Expired: Current time {} is after End Date is {}", LocalDateTime.now(), entity.getEndDate());
                urlRepository.delete(entity);
                return ResponseEntity.status(HttpStatus.GONE).build();
            }
            return ResponseEntity.status(HttpStatus.FOUND)
                                 .location(URI.create(entity.getOriginalUrl()))
                                 .build();
        }
        log.debug("Link Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}