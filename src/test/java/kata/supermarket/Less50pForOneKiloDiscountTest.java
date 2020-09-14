package kata.supermarket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Less50pForOneKiloDiscountTest {

    @DisplayName("When...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void receivedDiscount(final String description, final Discount discount, final List<Item> itemList, final String expectedTotal) {

        assertEquals(new BigDecimal(expectedTotal), discount.getDiscountAmount(itemList));
    }

    static Stream<Arguments> receivedDiscount() {
        return Stream.of(
            noItems()
        );
    }

    private static Arguments noItems() {
        return Arguments.of(
                "there are no items, no discount is added",
                new Less50pForOneKiloDiscount("50P-LESS"),
                Collections.emptyList(),
                "0.00");
    }
}
