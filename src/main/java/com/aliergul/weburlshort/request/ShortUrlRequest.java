package com.aliergul.weburlshort.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ShortUrlRequest {
    @NotNull
    @NotEmpty
    private String url;
    private String code;

}
