package com.aliergul.weburlshort.controller;

import com.aliergul.weburlshort.dto.ShortUrlDto;
import com.aliergul.weburlshort.dto.converter.ShortURLtoDTOConverter;
import com.aliergul.weburlshort.model.ShortUrl;
import com.aliergul.weburlshort.request.ShortUrlRequest;
import com.aliergul.weburlshort.request.convertor.ShortUrlRequestConverter;
import com.aliergul.weburlshort.service.ShortUrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping
public class ShortUrlController {

    private final ShortURLtoDTOConverter shortURLtoDTOConverter;
    public final ShortUrlRequestConverter shortUrlRequestConverter;
    private final ShortUrlService service;

    public ShortUrlController(ShortURLtoDTOConverter shortURLtoDTOConverter, ShortUrlRequestConverter shortUrlRequestConverter, ShortUrlService service) {
        this.shortURLtoDTOConverter = shortURLtoDTOConverter;
        this.shortUrlRequestConverter = shortUrlRequestConverter;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getALlUrls(){
        return new ResponseEntity<List<ShortUrlDto>>(
                shortURLtoDTOConverter.convertToDto(service.getALLShortUrl()), HttpStatus.OK
        );
    }
    @GetMapping("/show/{code}")
    public ResponseEntity<?> getUrlByCode(@Valid @PathVariable("code") String code){
        return new ResponseEntity<ShortUrlDto>(
                shortURLtoDTOConverter.convertToDto(service.getUrlByCode(code)), HttpStatus.OK
        );
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> reDirect(@Valid @PathVariable("code") String code) throws URISyntaxException {

        ShortUrl shortUrl=service.getUrlByCode(code);
        URI uri =new URI(shortUrl.getUrl());
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setLocation(uri);

        return new ResponseEntity<>(
               httpHeaders,HttpStatus.SEE_OTHER
        );
    }

    @PostMapping
    public  ResponseEntity<?> create(@Valid @RequestBody ShortUrlRequest request){
        ShortUrl shortUrl=shortUrlRequestConverter.convertToEntity(request);
        return new ResponseEntity<ShortUrlDto>(
                shortURLtoDTOConverter.convertToDto(service.create(shortUrl)), HttpStatus.CREATED
                );
    }

}
