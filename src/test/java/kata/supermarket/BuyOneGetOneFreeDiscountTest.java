package kata.supermarket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BuyOneGetOneFreeDiscountTest {
    @DisplayName("When...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void receivedDiscount(final String description, final Discount discount, final List<Item> itemList, final String expectedTotal) {

        assertEquals(new BigDecimal(expectedTotal), discount.getDiscountAmount(itemList));
    }

    static Stream<Arguments> receivedDiscount() {
        return Stream.of(
                noItems(),
                singleItemNoDiscount(),
                discountOnTwoItemsWithSamePrice(),
                discountOnTwoItemsWithDifferentPrices(),
                discountAppliedOnlyForEvenNumberOfItemsOddItemHigherPriceNotDiscounted(),
                discountAppliedForEvenNumberOfItemsCheaperOnesBecomeFree()
        );
    }
    private static Arguments noItems() {
        return Arguments.of(
                "there are no items, no discount is added",
                new BuyOneGetOneFreeDiscount("BOGO"),
                Collections.emptyList(),
                "0.00");
    }

    private static Arguments singleItemNoDiscount() {
        return Arguments.of(
                "When there is a single item added, there is no discount",
                new BuyOneGetOneFreeDiscount("BOGO"),
                Collections.singletonList(milkPackOfOnePint()),
                "0.00");
    }

    private static Arguments discountOnTwoItemsWithSamePrice() {
        return Arguments.of(
                "there are two items, discount is applied",
                new BuyOneGetOneFreeDiscount("BOGO"),
                Arrays.asList(milkPackOfOnePint(), milkPackOfOnePint()),
                "0.49");
    }

    private static Arguments discountOnTwoItemsWithDifferentPrices() {
        return Arguments.of(
                "there are two items with different prices, cheaper one becomes free",
                new BuyOneGetOneFreeDiscount("BOGO"),
                Arrays.asList(milkPackOfOnePint(), milkPackOfTwoPints()),
                "0.49");
    }

    private static Arguments discountAppliedOnlyForEvenNumberOfItemsOddItemHigherPriceNotDiscounted() {
        return Arguments.of(
                "there are three items, cheaper one becomes free",
                new BuyOneGetOneFreeDiscount("BOGO"),
                Arrays.asList(milkPackOfOnePint(), milkPackOfTwoPints(), milkPackOfTwoPints()),
                "0.49");
    }

    private static Arguments discountAppliedForEvenNumberOfItemsCheaperOnesBecomeFree() {
        return Arguments.of(
                "there are three items, cheaper one becomes free",
                new BuyOneGetOneFreeDiscount("BOGO"),
                Arrays.asList(milkPackOfOnePint(), milkPackOfTwoPints(), milkPackOfOnePint(), milkPackOfTwoPints()),
                "0.98");
    }

    private static Item milkPackOfOnePint() {
        final Product milkPint = new Product(new BigDecimal("0.49"));
        milkPint.setDiscount(new BuyOneGetOneFreeDiscount("BOGO"));
        return milkPint.oneOf();
    }

    private static Item milkPackOfTwoPints() {
        final Product milkPint = new Product(new BigDecimal("0.99"));
        milkPint.setDiscount(new BuyOneGetOneFreeDiscount("BOGO"));
        return milkPint.oneOf();
    }

}
