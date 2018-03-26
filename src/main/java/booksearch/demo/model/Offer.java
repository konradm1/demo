package booksearch.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class Offer {
    private String url;
    private BigDecimal price;
    private BookStoreType bookstore;

    public boolean isCheaperThan(Offer anotherOffer) {
        return price.compareTo(anotherOffer.getPrice()) < 0;
    }

    public boolean isEqualTo(Offer anotherOffer) {
        return price.compareTo(anotherOffer.getPrice()) == 0;
    }
}
