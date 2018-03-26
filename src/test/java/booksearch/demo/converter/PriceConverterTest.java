package booksearch.demo.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class PriceConverterTest {

    @Test
    public void should_return_null_given_null_string() {
        String price = null;

        BigDecimal convertedPrice = PriceConverter.convert(price);

        assertNull(convertedPrice);
    }

    @Test
    public void should_return_value_given_not_empty_string() {
        String price = "$29.90";

        BigDecimal convertedPrice = PriceConverter.convert(price);

        assertNotNull(convertedPrice);
        assertTrue(convertedPrice.equals(new BigDecimal("29.90")));
    }
}
