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

class Less50pForOneKiloDiscountTest {

    @DisplayName("When...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void receivedDiscount(final String description, final Discount discount, final List<Item> itemList, final String expectedTotal) {

        assertEquals(new BigDecimal(expectedTotal), discount.getDiscountAmount(itemList));
    }

    static Stream<Arguments> receivedDiscount() {
        return Stream.of(
            noItems(),
            itemsUnderTheWeightAreAdded(),
            itemsUnderTheWeightAreAddedMultipleItems(),
            singleItemWithRequiredWeightGivesDiscount(),
            multipleItemWithRequiredWeightGivesDiscount(),
            multipleItemWithMoreThanRequiredWeightGivesDiscount(),
            multipleItemWithMoreThanDoubleRequiredWeightGivesDiscountOnlyOnce()
        );
    }

    private static Arguments noItems() {
        return Arguments.of(
                "there are no items, no discount is added",
                new Less50pForOneKiloDiscount("50P-LESS"),
                Collections.emptyList(),
                "0.00");
    }

    private static Arguments itemsUnderTheWeightAreAdded() {
        return Arguments.of(
                "there is one item but net weight is less than required, no discount is added",
                new Less50pForOneKiloDiscount("50P-LESS"),
                Collections.singletonList(twoFiftyGramsOfAmericanSweets()),
                "0.00");
    }

    private static Arguments itemsUnderTheWeightAreAddedMultipleItems() {
        return Arguments.of(
                "there are multiple items but net weight is less than required, no discount is added",
                new Less50pForOneKiloDiscount("50P-LESS"),
                Arrays.asList(fiveHundredGramsOfAmericanSweets(), twoFiftyGramsOfAmericanSweets()),
                "0.00");
    }

    private static Arguments singleItemWithRequiredWeightGivesDiscount() {
        return Arguments.of(
                "there is single item with weight equal to 1 Kilo, discount is added",
                new Less50pForOneKiloDiscount("50P-LESS"),
                Collections.singletonList(oneKiloOfAmericanSweets()),
                "0.50");
    }

    private static Arguments multipleItemWithRequiredWeightGivesDiscount() {
        return Arguments.of(
                "there are multiple item with weight equal to 1 Kilo, discount is added",
                new Less50pForOneKiloDiscount("50P-LESS"),
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), fiveHundredGramsOfAmericanSweets(), fiveHundredGramsOfAmericanSweets()),
                "0.50");
    }

    private static Arguments multipleItemWithMoreThanRequiredWeightGivesDiscount() {
        return Arguments.of(
                "there are multiple item with weight more than 1 Kilo, discount is added",
                new Less50pForOneKiloDiscount("50P-LESS"),
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), oneKiloOfAmericanSweets()),
                "0.50");
    }

    private static Arguments multipleItemWithMoreThanDoubleRequiredWeightGivesDiscountOnlyOnce() {
        return Arguments.of(
                "there are multiple item with weight more than 2 Kilo, discount is added only once",
                new Less50pForOneKiloDiscount("50P-LESS"),
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), oneKiloOfAmericanSweets(), oneKiloOfAmericanSweets()),
                "0.50");
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        final WeighedProduct aKiloOfAmericanSweets = new WeighedProduct(new BigDecimal("4.99"));
        aKiloOfAmericanSweets.setDiscount(new Less50pForOneKiloDiscount("50P-LESS"));
        return aKiloOfAmericanSweets;
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal("0.25"));
    }

    private static Item fiveHundredGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal("0.50"));
    }

    private static Item oneKiloOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal("1.00"));
    }

}
