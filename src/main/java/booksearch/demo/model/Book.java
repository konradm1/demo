package booksearch.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Book {

    private BookInfo volumeInfo;

    @JsonIgnore
    private String kind;

    @JsonIgnore
    private String id;

    @JsonIgnore
    private String etag;

    @JsonIgnore
    private String selfLink;

    @JsonIgnore
    private String saleInfo;

    @JsonIgnore
    private String accessInfo;

    @JsonIgnore
    private String searchInfo;

}