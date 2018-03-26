package booksearch.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Isbn {

    private String type;
    private String identifier;
}
