package com.aliergul.weburlshort.service;

import com.aliergul.weburlshort.exception.CodeAlreadyExistsException;
import com.aliergul.weburlshort.exception.ShortUrlNotFoundException;
import com.aliergul.weburlshort.model.ShortUrl;
import com.aliergul.weburlshort.repository.ShortUrlRepository;
import com.aliergul.weburlshort.util.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private final RandomStringGenerator randomStringGenerator;
    public ShortUrlService(ShortUrlRepository shortUrlRepository, RandomStringGenerator randomStringGenerator) {
        this.shortUrlRepository = shortUrlRepository;
        this.randomStringGenerator = randomStringGenerator;
    }

    public List<ShortUrl> getALLShortUrl() {
        return shortUrlRepository.findAll();
    }


    public ShortUrl getUrlByCode(String code) {
        Optional<ShortUrl> inDB=shortUrlRepository.findAllByCode(code);

        return inDB.orElseThrow(()-> new ShortUrlNotFoundException("url not found!"));
    }

    public ShortUrl create(ShortUrl shortUrl) {
        if(shortUrl.getCode()==null ||shortUrl.getCode().isEmpty()){
            shortUrl.setCode(generateCode());
        }else if(shortUrlRepository.findAllByCode(shortUrl.getCode().toUpperCase()).isPresent()){
            throw new CodeAlreadyExistsException("code already exists");
        }
        shortUrl.setCode(shortUrl.getCode().toUpperCase());
        return shortUrlRepository.save(shortUrl);
    }

    private String generateCode() {
        String code="";
        do{
            code=randomStringGenerator.generateRandomString();

        }while(shortUrlRepository.findAllByCode(code).isPresent());
        return code;
    }
}
