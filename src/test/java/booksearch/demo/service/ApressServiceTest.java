package booksearch.demo.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import booksearch.demo.model.Offer;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApressServiceTest {

    private static final String ROOT_URL = "https://www.apress.com";
    private static final String SEARCH_URL = ROOT_URL + "/us/search?query=";
    private static final String TEST_ISBN = "test_isbn";
    private static final String DETAILS = "/gp/book/9781484225912";

    @MockBean
    private DomParser domParser;

    @Autowired
    private ApressService apressService;

    @Test
    public void should_get_valid_offer_given_search_result_response() throws IOException {
        when(domParser.parse(SEARCH_URL + TEST_ISBN)).thenReturn(getApressDomWithResultSearch());
        when(domParser.parse(ROOT_URL + DETAILS)).thenReturn(getApressDomWithDetailsResultSearch());

        Offer apress = apressService.getOffer("test_isbn");

        assertThat(apress.getUrl()).isEqualTo(ROOT_URL + DETAILS);
        assertThat(apress.getPrice()).isEqualTo(new BigDecimal("39.99"));
    }

    @Test
    public void should_get_no_offer_given_no_search_result_response() throws IOException {
        when(domParser.parse(SEARCH_URL + TEST_ISBN)).thenReturn(getApressDomWithoutResultSearch());

        Offer apress = apressService.getOffer("test_isbn");

        assertThat(apress.getUrl()).isNull();
        assertThat(apress.getPrice()).isNull();
    }

    private Document getApressDomWithResultSearch() {
        String html = "<div id=\"result-list\">" +
                "<div class=\"result-item result-item-0 result-type-book last\">\n" +
                "       <a href=\""+ DETAILS +"\" class=\"cover\">\n" +
                "        <img class=\"lazy\" title=\"Java 9 Revealed\" alt=\"Java 9 Revealed\" data-original=\"https://images.springer.com/sgw/books/small/9781484225912.jpg\" width=\"95\" src=\"https://images.springer.com/sgw/books/small/9781484225912.jpg\" style=\"display: block;\">\n" +
                "</a>\n" +
                "<small class=\"result-type\">Book</small>\n" +
                "<h4>\n" +
                "    <a href=\"/gp/book/9781484225912\">Java 9 Revealed</a>\n" +
                "</h4>\n" +
                "<p class=\"meta contributors book-contributors\">\n" +
                "    Sharan, K. <em>(2017)</em>\n" +
                "</p>\n" +
                "<div class=\"snippet\"> \n" +
                "    Explore the new Java 9 modules, SDK, JDK, JVM, JShell and more in this comprehensive book that covers what’s new in Java 9 and how to use these new features. Java 9 Revealed is …\n" +
                "</div>\n" +
                "<p class=\"format\">\n" +
                "    <strong>Available Formats:</strong>\n" +
                "    \n" +
                "        <span data-format=\"SOFT|BO|SOFT\">Softcover</span>\n" +
                "    \n" +
                "        <span data-format=\"NA|EBO|EBO\">eBook</span>\n" +
                "    \n" +
                "</p>\n" +
                "    </div>" +
                "    </div>";

        return Jsoup.parse(html);
    }

    private Document getApressDomWithDetailsResultSearch() {
        String html = "<div class=\"product-buy\">\n" +
                "    <div class=\"box-primary\">\n" +
                "        <h3>Buy this book</h3>\n" +
                "            <dl class=\"expander\">\n" +
                "<dt class=\"buy-rendition-9781484225929\">\n" +
                " <span class=\"cover-type\">eBook</span>\n" +
                "        <span class=\"price-box\">\n" +
                "                    <span class=\"price\">\n" +
                "                        \n" +
                "                        $29.99\n" +
                "                    </span>\n" +
                "        </span>\n" +
                "</dt>\n" +
                "<dt class=\"buy-rendition-9781484225912 \">\n" +
                "        <span class=\"cover-type\">Softcover</span>\n" +
                "        <span class=\"price-box\">\n" +
                "                    <span class=\"price\">\n" +
                "                        \n" +
                "                        $39.99\n" +
                "                    </span>\n" +
                "        </span>\n" +
                "    \n" +
                "</dt>\n" +

                "            </dl>\n" +
                "    </div>\n" +
                "</div>";

        return Jsoup.parse(html);
    }

    private Document getApressDomWithoutResultSearch() {
        String html = "<div class=\"col-main columns small-12 medium-8 large-9\" role=\"main\">\n" +
                "        <div class=\"sub-search__meta\">\n" +
                "<div class=\"search-meta\" role=\"main\">\n" +
                "    <div class=\"result-meta-information\">\n" +
                "        <p class=\"result-count-message\">\n" +
                "            \n" +
                "                Showing <strong>0 results</strong>.\n" +
                "            \n" +
                "        </p>\n" +
                "        <p class=\"result-filter\">\n" +
                "            \n" +
                "        </p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "        </div>\n" +
                "        <div id=\"result-list\" class=\"sub-search__result-list\" role=\"main\" data-endpoint-prices=\"/gp/product-search/ajax/prices\" data-resource-pricefrom=\"from\">\n" +
                "        <div class=\"cms-common\">\n" +
                "            <h2>Sorry – we couldn’t find what you are looking for.</h2>\n" +
                "            <p class=\"intro--paragraph\">\n" +
                "            Make sure that all words are spelled correctly\n" +
                "            </p>\n" +
                "        </div>\n" +
                "        </div>\n" +
                "    </div>";

        return Jsoup.parse(html);
    }

}
