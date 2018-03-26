package booksearch.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import booksearch.demo.model.Book;
import booksearch.demo.model.Isbn;
import booksearch.demo.model.Offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BooksService {

    private final GoogleBookSearchService googleBookSearchService;
    private final AmazonService amazonService;
    private final ApressService apressService;

    @Autowired
    public BooksService(GoogleBookSearchService googleBookSearchService, AmazonService amazonService, ApressService apressService) {
        this.googleBookSearchService = googleBookSearchService;
        this.amazonService = amazonService;
        this.apressService = apressService;
    }

    public List<Offer> getCheaperBookOffer(String title) {
        Optional<Book> bookInfo = googleBookSearchService.getBookInfo(title);
        if (bookInfo.isPresent()) {
            Offer amazonOffer = amazonService.getOffer(getIsbn(bookInfo.get()).get().getIdentifier());
            Offer apressOffer = apressService.getOffer(getIsbn(bookInfo.get()).get().getIdentifier());


            return compareOffers(amazonOffer, apressOffer);
        }

        log.info("No isbn was found for title {}" + title);

        return new ArrayList<>();
    }

    private List<Offer>compareOffers(Offer amazon, Offer apress) {
        List<Offer> cheaperOffer = new ArrayList<>();

        if (amazon.getPrice() == null || apress.getPrice() == null) {
            return handleEmptyOffers(amazon, apress);
        }

        if (amazon.isEqualTo(apress)) {
            cheaperOffer.add(amazon);
            cheaperOffer.add(apress);
        } else {
            cheaperOffer.add(getCheaper(amazon, apress));
        }

        return cheaperOffer;
    }

    private List<Offer> handleEmptyOffers(Offer amazon, Offer apress) {
        List<Offer> result = new ArrayList<>();

        if (amazon.getPrice() != null) {
            result.add(amazon);
        }

        if (apress.getPrice() != null) {
            result.add(apress);
        }

        return result;
    }

    private Offer getCheaper(Offer amazon, Offer apress) {
        return amazon.isCheaperThan(apress) ? amazon : apress;
    }

    private Optional<Isbn> getIsbn(Book book) {
        return book.getVolumeInfo().getIndustryIdentifiers().stream().filter(is -> is.getType().equals("ISBN_13")).findFirst();
    }
}
