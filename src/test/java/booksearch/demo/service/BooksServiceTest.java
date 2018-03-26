package booksearch.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import booksearch.demo.model.Book;
import booksearch.demo.model.BookInfo;
import booksearch.demo.model.Isbn;
import booksearch.demo.model.Offer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BooksServiceTest {

    @MockBean
    private AmazonService amazonService;

    @MockBean
    private ApressService apressService;

    @MockBean
    private GoogleBookSearchService googleBookSearchService;

    @Autowired
    private BooksService booksService;

    @Test
    public void should_return_amazon_offer() {
        when(googleBookSearchService.getBookInfo("test_title")).thenReturn(Optional.of(prepareGoogleSearchResult()));
        when(amazonService.getOffer(anyString())).thenReturn(prepareAmazon(new BigDecimal("20.00")));
        when(apressService.getOffer(anyString())).thenReturn(prepareApress(new BigDecimal("30.00")));

        List<Offer> cheaperOffer = booksService.getCheaperBookOffer("test_title");

        assertThat(cheaperOffer).isNotEmpty();
        assertThat(cheaperOffer.get(0).getPrice()).isEqualTo(new BigDecimal("20.00"));
        assertThat(cheaperOffer.get(0).getUrl()).isEqualTo("amazon_url");
    }

    @Test
    public void should_return_apress_offer() {
        when(googleBookSearchService.getBookInfo("test_title")).thenReturn(Optional.of(prepareGoogleSearchResult()));
        when(amazonService.getOffer(anyString())).thenReturn(prepareAmazon(new BigDecimal("70.00")));
        when(apressService.getOffer(anyString())).thenReturn(prepareApress(new BigDecimal("50.00")));

        List<Offer> cheaperOffer = booksService.getCheaperBookOffer("test_title");

        assertThat(cheaperOffer).isNotEmpty();
        assertThat(cheaperOffer.get(0).getPrice()).isEqualTo(new BigDecimal("50.00"));
        assertThat(cheaperOffer.get(0).getUrl()).isEqualTo("apress_url");
    }

    @Test
    public void should_return_both_offers_given_equal_prices() {
        when(googleBookSearchService.getBookInfo("test_title")).thenReturn(Optional.of(prepareGoogleSearchResult()));
        when(amazonService.getOffer(anyString())).thenReturn(prepareAmazon(new BigDecimal("49.99")));
        when(apressService.getOffer(anyString())).thenReturn(prepareApress(new BigDecimal("49.99")));

        List<Offer> cheaperOffer = booksService.getCheaperBookOffer("test_title");

        assertThat(cheaperOffer).isNotEmpty();
        assertThat(cheaperOffer.size()).isEqualTo(2);
        assertThat(cheaperOffer.get(0).getPrice()).isEqualTo(new BigDecimal("49.99"));
        assertThat(cheaperOffer.get(0).getUrl()).isEqualTo("amazon_url");
        assertThat(cheaperOffer.get(1).getPrice()).isEqualTo(new BigDecimal("49.99"));
        assertThat(cheaperOffer.get(1).getUrl()).isEqualTo("apress_url");
    }

    @Test
    public void should_return_no_offer() {
        when(googleBookSearchService.getBookInfo("test_title")).thenReturn(Optional.of(prepareGoogleSearchResult()));
        when(amazonService.getOffer(anyString())).thenReturn(prepareAmazon(null));
        when(apressService.getOffer(anyString())).thenReturn(prepareApress(null));

        List<Offer> cheaperOffer = booksService.getCheaperBookOffer("test_title");

        assertThat(cheaperOffer).isEmpty();
    }

    private Book prepareGoogleSearchResult() {
        return Book.builder()
                .volumeInfo(prepareBookInfo())
                .build();
    }

    private BookInfo prepareBookInfo() {
        ArrayList<Isbn> isbns = new ArrayList<>();
        isbns.add(prepareIsbn());
        return BookInfo.builder().industryIdentifiers(isbns).build();
    }

    private Isbn prepareIsbn() {
        return Isbn.builder().type("ISBN_13").identifier("test_isbn").build();
    }

    private Offer prepareAmazon(BigDecimal price) {
        return Offer.builder().url("amazon_url").price(price).build();
    }

    private Offer prepareApress(BigDecimal price) {
        return Offer.builder().url("apress_url").price(price).build();
    }
}
