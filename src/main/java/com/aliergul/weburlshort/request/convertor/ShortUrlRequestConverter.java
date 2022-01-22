package com.aliergul.weburlshort.request.convertor;

import com.aliergul.weburlshort.model.ShortUrl;
import com.aliergul.weburlshort.request.ShortUrlRequest;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlRequestConverter {

    public ShortUrl convertToEntity(ShortUrlRequest shortUrlRequest){
        return ShortUrl.builder()
                .url(shortUrlRequest.getUrl())
                .code(shortUrlRequest.getCode())
                .build();
    }

}
