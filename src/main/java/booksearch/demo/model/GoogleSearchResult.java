package booksearch.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoogleSearchResult {

    private List<Book> items;
    @JsonIgnore
    private String kind;
    @JsonIgnore
    private Long totalItems;
}
