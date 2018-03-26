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
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmazonServiceTest {

    private static final String GET_URL = "https://www.amazon.com/s/ref=nb_sb_noss?field-keywords=";
    private static String TEST_ISBN = "test_isbn";

    @MockBean
    private DomParser domParser;

    @Autowired
    private AmazonService amazonService;

    @Test
    public void should_get_valid_offer_given_search_result_response() throws IOException {
        when(domParser.parse(GET_URL + TEST_ISBN)).thenReturn(getAmazonDomWithResultSearch());

        Offer amazon = amazonService.getOffer("test_isbn");

        assertThat(amazon.getUrl()).isEqualTo("https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882/ref=sr_1_1?ie=UTF8&qid=1522083760&sr=8-1&keywords=9780132350884");
        assertThat(amazon.getPrice()).isEqualTo(new BigDecimal("38.39"));
    }

    @Test
    public void should_get_no_offer_given_no_search_result_response() throws IOException {
        when(domParser.parse(GET_URL + TEST_ISBN)).thenReturn(getAmazonDomWithoutResultSearch());

        Offer amazon = amazonService.getOffer("test_isbn");

        assertThat(amazon.getUrl()).isNull();
        assertThat(amazon.getPrice()).isNull();
    }

    private Document getAmazonDomWithResultSearch() {
        String html = "<li id=\"result_0\" data-asin=\"0132350882\" class=\"s-result-item celwidget  \"><div class=\"s-item-container\"><div class=\"a-row a-spacing-micro\"><div class=\"a-row sx-badge-region sx-pinned-top-badge\"><div class=\"a-row a-badge-region\"><a id=\"BESTSELLER_0132350882\" href=\"/gp/bestsellers/books/4133/ref=sr_bs_0_4133_1\" class=\"a-badge\" aria-labelledby=\"BESTSELLER_0132350882-label BESTSELLER_0132350882-supplementary\" data-a-badge-supplementary-position=\"right\" data-a-badge-type=\"status\"><span id=\"BESTSELLER_0132350882-label\" class=\"a-badge-label\" data-a-badge-color=\"sx-orange\" aria-hidden=\"true\"><span class=\"a-badge-label-inner a-text-ellipsis\"><span class=\"a-badge-text\" data-a-badge-color=\"sx-cloud\">Best Seller</span></span></span><span id=\"BESTSELLER_0132350882-supplementary\" class=\"a-badge-supplementary-text a-text-ellipsis\" aria-hidden=\"true\">in Software Testing</span></a></div></div></div><div class=\"a-fixed-left-grid\"><div class=\"a-fixed-left-grid-inner\" style=\"padding-left:218px\"><div class=\"a-fixed-left-grid-col a-col-left\" style=\"width:218px;margin-left:-218px;_margin-left:-109px;float:left;\"><div class=\"a-row\"><div aria-hidden=\"true\" class=\"a-column a-span12 a-text-center\"><a class=\"a-link-normal a-text-normal\" href=\"https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882/ref=sr_1_1?ie=UTF8&amp;qid=1522083760&amp;sr=8-1&amp;keywords=9780132350884\"><img src=\"https://images-na.ssl-images-amazon.com/images/I/51bPR2V9fBL._AC_US218_.jpg\" srcset=\"https://images-na.ssl-images-amazon.com/images/I/51bPR2V9fBL._AC_US218_.jpg 1x, https://images-na.ssl-images-amazon.com/images/I/51bPR2V9fBL._AC_US327_FMwebp_QL65_.jpg 1.5x, https://images-na.ssl-images-amazon.com/images/I/51bPR2V9fBL._AC_US436_FMwebp_QL65_.jpg 2x, https://images-na.ssl-images-amazon.com/images/I/51bPR2V9fBL._AC_US500_FMwebp_QL65_.jpg 2.2935x\" width=\"218\" height=\"218\" alt=\"Clean Code: A Handbook of Agile Software Craftsmanship\" class=\"s-access-image cfMarker\" data-search-image-load=\"\"></a><div class=\"a-section a-spacing-none a-text-center\"></div></div></div></div><div class=\"a-fixed-left-grid-col a-col-right\" style=\"padding-left:2%;*width:97.6%;float:left;\"><div class=\"a-row a-spacing-small\"><div class=\"a-row a-spacing-none\"><a class=\"a-link-normal s-access-detail-page  s-color-twister-title-link a-text-normal\" title=\"Clean Code: A Handbook of Agile Software Craftsmanship\" href=\"https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882/ref=sr_1_1?ie=UTF8&amp;qid=1522083760&amp;sr=8-1&amp;keywords=9780132350884\"><h2 data-attribute=\"Clean Code: A Handbook of Agile Software Craftsmanship\" data-max-rows=\"0\" class=\"a-size-medium s-inline  s-access-title  a-text-normal\">Clean Code: A Handbook of Agile Software Craftsmanship</h2></a><span class=\"a-letter-space\"></span><span class=\"a-letter-space\"></span><span class=\"a-size-small a-color-secondary\">Aug 11, 2008</span></div><div class=\"a-row a-spacing-none\"><span class=\"a-size-small a-color-secondary\">by </span><span class=\"a-size-small a-color-secondary\"><a class=\"a-link-normal a-text-normal\" href=\"/Robert-C.-Martin/e/B000APG87E/ref=sr_ntt_srch_lnk_1?qid=1522083760&amp;sr=8-1\">Robert C. Martin</a></span></div></div><div class=\"a-row\"><div class=\"a-column a-span7\"><div class=\"a-row a-spacing-none\"><a class=\"a-link-normal a-text-normal\" title=\"Paperback\" href=\"https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882/ref=sr_1_1_twi_pap_1?ie=UTF8&amp;qid=1522083760&amp;sr=8-1&amp;keywords=9780132350884\"><h3 data-attribute=\"Paperback\" data-max-rows=\"0\" data-truncate-by-character=\"false\" class=\"a-size-small s-inline  a-text-normal\">Paperback</h3></a></div><div class=\"a-row a-spacing-none\"><a class=\"a-link-normal a-text-normal\" href=\"https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882/ref=sr_1_1?ie=UTF8&amp;qid=1522083760&amp;sr=8-1&amp;keywords=9780132350884\"><span class=\"a-offscreen\">$38.39</span><span aria-hidden=\"true\" class=\"a-color-base sx-zero-spacing\"><span class=\"sx-price sx-price-large\">\n" +
                "                            <sup class=\"sx-price-currency\">$</sup>\n" +
                "                            <span class=\"sx-price-whole\">38</span>\n" +
                "                            <sup class=\"sx-price-fractional\">39</sup>\n" +
                "                        </span>\n" +
                "                    </span></a><span class=\"a-letter-space\"></span><span aria-label=\"Suggested Retail Price: $49.99\" class=\"a-size-base-plus a-color-secondary a-text-strike\">$49.99</span><span class=\"a-letter-space\"></span><i class=\"a-icon a-icon-prime a-icon-small s-align-text-bottom\" aria-label=\"Prime\"><span class=\"a-icon-alt\">Prime</span></i></div><div class=\"a-row a-spacing-none\"><span class=\"a-size-small a-color-secondary\">Get it by <span class=\"a-color-success a-text-bold\">Thursday, Mar 29</span></span></div><div class=\"a-row a-spacing-none\"><span class=\"a-size-small a-color-secondary\">FREE Shipping on eligible orders</span></div><div class=\"a-row a-spacing-top-mini a-spacing-mini\"><div class=\"a-row a-spacing-none\"><span class=\"a-size-small a-color-secondary\">More Buying Choices</span></div><div class=\"a-row a-spacing-none\"><a class=\"a-size-small a-link-normal a-text-normal\" href=\"https://www.amazon.com/gp/offer-listing/0132350882/ref=sr_1_1_olp?ie=UTF8&amp;qid=1522083760&amp;sr=8-1&amp;keywords=9780132350884\"><span class=\"a-color-secondary a-text-strike\"></span><span class=\"a-size-base a-color-base\">$26.00</span><span class=\"a-letter-space\"></span>(48 used &amp; new offers)</a></div></div><hr class=\"a-spacing-mini a-spacing-top-mini a-divider-normal\"><span class=\"a-size-small a-color-secondary\">Other Formats:<span class=\"a-letter-space\"></span></span><a class=\"a-size-small a-link-normal a-text-normal\" href=\"https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship-ebook/dp/B001GSTOAM/ref=sr_1_1_twi_kin_2?ie=UTF8&amp;qid=1522083760&amp;sr=8-1&amp;keywords=9780132350884\">Kindle Edition</a><span class=\"a-size-small a-color-secondary\"></span></div><div class=\"a-column a-span5 a-span-last\"><div class=\"a-row a-spacing-mini\"><span name=\"0132350882\">\n" +
                "    <span class=\"a-declarative\" data-action=\"a-popover\" data-a-popover=\"{&quot;max-width&quot;:&quot;700&quot;,&quot;closeButton&quot;:&quot;false&quot;,&quot;position&quot;:&quot;triggerBottom&quot;,&quot;url&quot;:&quot;/review/widgets/average-customer-review/popover/ref=acr_search__popover?ie=UTF8&amp;asin=0132350882&amp;contextId=search&amp;ref=acr_search__popover&quot;}\"><a href=\"javascript:void(0)\" class=\"a-popover-trigger a-declarative\"><i class=\"a-icon a-icon-star a-star-4-5\"><span class=\"a-icon-alt\">4.4 out of 5 stars</span></i><i class=\"a-icon a-icon-popover\"></i></a></span></span>\n" +
                "\n" +
                "<a class=\"a-size-small a-link-normal a-text-normal\" href=\"https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882/ref=sr_1_1?ie=UTF8&amp;qid=1522083760&amp;sr=8-1&amp;keywords=9780132350884#customerReviews\">412</a></div><div class=\"a-row a-spacing-mini\"><div class=\"a-row a-spacing-micro\"><span class=\"a-size-small a-color-secondary\">Save an extra $0.69 at checkout</span></div></div></div></div></div></div></div></div></li>";

        return Jsoup.parse(html);
    }

    private Document getAmazonDomWithoutResultSearch() {
        String html = "<div id=\"centerPlus\">\n" +
                "        <br><h1 id=\"noResultsTitle\" class=\"a-size-medium a-spacing-none a-spacing-top-mini a-color-base a-text-normal\">Your search <span class=\"a-color-base a-text-bold\">\"9780132352884\"</span> did not match any products.</h1><h1 class=\"a-size-base a-spacing-top-medium a-text-bold\">Try something like</h1><ul class=\"a-unordered-list a-vertical\"><li class=\"a-spacing-small a-spacing-top-small\"><span class=\"a-list-item\"><span class=\"a-size-base\">Using more general terms</span></span></li><li class=\"a-spacing-small a-spacing-top-small\"><span class=\"a-list-item\"><span class=\"a-size-base\">Checking your spelling</span></span></li></ul><br><div id=\"sx-top-slot\"></div><div id=\"navlayout-top-1\" class=\"sx-nav-top\"></div><div id=\"navlayout-top-2\" class=\"sx-nav-top\"></div><div id=\"navlayout-top-3\" class=\"sx-nav-top\"></div></div>";

        return Jsoup.parse(html);
    }
}
