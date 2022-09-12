package com.tinyurl.app.service;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tinyurl.app.model.UrlResponse;
import com.tinyurl.app.model.ValidationError;
import com.tinyurl.app.repository.URLRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import com.tinyurl.app.entity.UrlEntity;
import com.tinyurl.app.model.UrlRequest;
import com.google.common.primitives.Longs;
import org.springframework.util.StringUtils;

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
     * id to a base 64 number.
     */
    @Override
    public ResponseEntity<?> convertToTinyUrl(UrlRequest request) {
        if (request.getStartDate() == null) {
            request.setStartDate(LocalDateTime.now());
        }
        UrlEntity entity = UrlEntity.builder()
                                           .originalUrl(request.getOriginalUrl())
                                           .startDate(request.getStartDate())
                                           .endDate(request.getEndDate())
                                           .count(0)
                                           .build();
        entity = urlRepository.save(entity);
        UrlResponse response = getUrlResponse(entity);
        return ResponseEntity.ok(response);
    }

    /**
     * Get the original url from the tinyUrl hash
     * @param tinyUrl, the tinyUrl hash
     * @return
     */
    @Override
    public ResponseEntity<?> getOriginalUrl(String tinyUrl) {
        byte[] byteId = Base64Utils.decodeFromUrlSafeString(tinyUrl);
        long longId = Longs.fromByteArray(byteId);
        Optional<UrlEntity> opt = urlRepository.findById(String.valueOf(longId));

        ValidationError validationError = new ValidationError();
        validationError.setErrorMessage("An Error Occurred");
        String errorMessage = "Link Not Found";
        if (opt.isPresent()) {
            UrlEntity entity = opt.get();
            if (!StringUtils.hasText(entity.getOriginalUrl())){
                //if the url is empty
                urlRepository.delete(entity);
                log.error(errorMessage);
                validationError.getErrors().add(errorMessage);
                return ResponseEntity.status(HttpStatus.GONE).body(validationError);
            }

            if (entity.getEndDate() != null && LocalDateTime.now().isAfter(entity.getEndDate()) ) {
                log.error("Link Expired: Current time {} is after End Date is {}", LocalDateTime.now(), entity.getEndDate());
                urlRepository.delete(entity);
                validationError.getErrors().add("Link Expired");
                return ResponseEntity.status(HttpStatus.GONE).body(validationError);
            }
            entity.setCount(entity.getCount() + 1);
            urlRepository.save(entity);
            return ResponseEntity.status(HttpStatus.FOUND)
                                 .location(URI.create(entity.getOriginalUrl()))
                                 .build();
        }
        log.info(errorMessage);
        validationError.getErrors().add(errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @Override
    public ResponseEntity<?> findAll() {
        Iterable<UrlEntity> entityUrls = urlRepository.findAll();
        List<UrlResponse> responseList = new ArrayList<>();
        for (UrlEntity urlEntity: entityUrls){
            UrlResponse urlResponse = getUrlResponse(urlEntity);
            responseList.add(urlResponse);
        }
        return ResponseEntity.ok().body(responseList);
    }

    @Override
    public ResponseEntity<?> deleteAll() {
        urlRepository.deleteAll();
        return ResponseEntity.ok().body("All URL's were Deleted");
    }

    @Override
    public ResponseEntity<?> deleteById(String id) {
        urlRepository.deleteById(id);
        return ResponseEntity.ok().body("URL id "+ id +" was deleted.");
    }

    private String getHostName(){
        String hostname = "";
        try {
            InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("An error occured {}", e);
        }
        return hostname;
    }

    private UrlResponse getUrlResponse(UrlEntity entity){
        String tinyUrl = getTinyUrl(entity);
        UrlResponse response = UrlResponse.builder()
                                          .id(entity.getId())
                                          .originalUrl(entity.getOriginalUrl())
                                          .startDate(entity.getStartDate())
                                          .endDate(entity.getEndDate())
                                          .count(entity.getCount())
                                          .tinyUrl(tinyUrl)
                                          .build();
        return response;
    }

    private String getTinyUrl(UrlEntity entity){
        byte[] bytesId = Longs.toByteArray(entity.getId());
        String tinyUrl = Base64Utils.encodeToUrlSafeString(bytesId);
        log.debug("tinyUrl = {}", tinyUrl);
        log.debug("hostname = {}", getHostName());
        log.info("converting id = {} to tinyUrl = {}", entity.getId(), tinyUrl);
        return tinyUrl;
    }


}