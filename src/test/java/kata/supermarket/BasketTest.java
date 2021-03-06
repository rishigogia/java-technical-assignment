package kata.supermarket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class BasketTest {

    private static Discount buyOneGetOneFreeDiscount = Mockito.mock(Discount.class);
    private static Discount less50pForOneKiloDiscount = Mockito.mock(Discount.class);;
    private static Discount noDiscount = Mockito.mock(Discount.class);

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items) {
        final Basket basket = new Basket();
        items.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight(),
                aSingleItemPricedPerUnitWithDiscountCode(),
                aSingleWeightItemLessWeightWithDiscountCode(),
                twoItemPricedPerUnitWithDiscountCode(),
                aSingleWeightItemFullWeightWithDiscountCode(),
                multipleUnitItemWithAndWithoutDiscountCodes(),
                multipleWeightItemWithAndWithoutDiscountCodes(),
                weightItemAndUnitItemWithAndWithoutDiscountCodes()
        );
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()));
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix())
        );
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(aPintOfMilk()));
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList());
    }

    private static Arguments aSingleItemPricedPerUnitWithDiscountCode() {
        Mockito.when(noDiscount.getDiscountAmount(Mockito.anyList())).thenReturn(BigDecimal.ZERO);
        return Arguments.of("a single item priced per unit with Buy one get one discount", "0.49", Collections.singleton(aSinglePintOfMilkWithDiscount()));
    }

    private static Arguments twoItemPricedPerUnitWithDiscountCode() {
        Mockito.when(buyOneGetOneFreeDiscount.getDiscountAmount(Mockito.anyList())).thenReturn(new BigDecimal("0.49"));
        return Arguments.of("two items priced per unit with Buy one get one discount", "0.49", Arrays.asList(aPintOfMilkWithDiscount(), aPintOfMilkWithDiscount()));
    }

    private static Arguments aSingleWeightItemLessWeightWithDiscountCode() {
        Mockito.when(noDiscount.getDiscountAmount(Mockito.anyList())).thenReturn(BigDecimal.ZERO);
        return Arguments.of("an item with less than threshold weight with discount code", "1.50", Collections.singleton(fiveHundredGramsOfAmericanSweetsWithDiscount()));
    }

    private static Arguments aSingleWeightItemFullWeightWithDiscountCode() {
        Mockito.when(less50pForOneKiloDiscount.getDiscountAmount(Mockito.anyList())).thenReturn(new BigDecimal("0.50"));
        return Arguments.of("an item equal to threshold weight with discount code", "2.49", Collections.singleton(oneKiloOfPickAndMixWithDiscount()));
    }

    private static Arguments multipleUnitItemWithAndWithoutDiscountCodes() {
        Mockito.when(buyOneGetOneFreeDiscount.getDiscountAmount(Mockito.anyList())).thenReturn(new BigDecimal("0.49"));
        return Arguments.of("multiple unit items with and without discount codes", "2.04",
                Arrays.asList(aPintOfMilkWithDiscount(), aPackOfDigestives(), aPintOfMilkWithDiscount()));
    }

    private static Arguments multipleWeightItemWithAndWithoutDiscountCodes() {
        Mockito.when(less50pForOneKiloDiscount.getDiscountAmount(Mockito.anyList())).thenReturn(new BigDecimal("0.50"));
        return Arguments.of("multiple weight items with and without discount codes", "3.74",
                Arrays.asList(oneKiloOfPickAndMixWithDiscount(), twoFiftyGramsOfAmericanSweets()));
    }

    private static Arguments weightItemAndUnitItemWithAndWithoutDiscountCodes() {
        Mockito.when(buyOneGetOneFreeDiscount.getDiscountAmount(Mockito.anyList())).thenReturn(new BigDecimal("0.49"));
        Mockito.when(less50pForOneKiloDiscount.getDiscountAmount(Mockito.anyList())).thenReturn(new BigDecimal("0.50"));
        return Arguments.of("weight items along with unit items both with/without discount codes", "5.78",
                Arrays.asList(aPintOfMilkWithDiscount(), oneKiloOfPickAndMixWithDiscount(), aPintOfMilkWithDiscount(), twoFiftyGramsOfAmericanSweets(), aPackOfDigestives()));
    }
    private static Item aPintOfMilk() {
        return new Product(new BigDecimal("0.49")).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product(new BigDecimal("1.55")).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct(new BigDecimal("4.99"));
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct(new BigDecimal("2.99"));
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }

    private static Item aPintOfMilkWithDiscount() {
        final Product milkPint = new Product(new BigDecimal("0.49"));
        milkPint.setDiscount(buyOneGetOneFreeDiscount);
        return milkPint.oneOf();
    }

    private static Item aSinglePintOfMilkWithDiscount() {
        final Product milkPint = new Product(new BigDecimal("0.49"));
        milkPint.setDiscount(noDiscount);
        return milkPint.oneOf();
    }

    private static WeighedProduct aKiloOfPickAndMixWithDiscount() {

        final WeighedProduct aKiloOfPickAndMix = new WeighedProduct(new BigDecimal("2.99"));
        aKiloOfPickAndMix.setDiscount(less50pForOneKiloDiscount);
        return aKiloOfPickAndMix;
    }

    private static WeighedProduct aKiloOfAmericanSweetsWithDiscount() {

        final WeighedProduct aKiloOfPickAndMix = new WeighedProduct(new BigDecimal("2.99"));
        aKiloOfPickAndMix.setDiscount(noDiscount);
        return aKiloOfPickAndMix;
    }

    private static Item twoHundredGramsOfPickAndMixWithDiscount() {
        return aKiloOfPickAndMixWithDiscount().weighing(new BigDecimal(".2"));
    }

    private static Item fiveHundredGramsOfAmericanSweetsWithDiscount() {
        return aKiloOfAmericanSweetsWithDiscount().weighing(new BigDecimal(".5"));
    }

    private static Item oneKiloOfPickAndMixWithDiscount() {
        return aKiloOfPickAndMixWithDiscount().weighing(new BigDecimal("1"));
    }
}
