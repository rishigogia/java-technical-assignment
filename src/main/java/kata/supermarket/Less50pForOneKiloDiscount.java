package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Less50pForOneKiloDiscount implements Discount {

    final String discountCode;

    public Less50pForOneKiloDiscount(String discountCode) {
        this.discountCode = discountCode;
    }

    @Override
    public BigDecimal getDiscountAmount(List<Item> itemList) {
        final BigDecimal netWeight = itemList.stream()
                .filter(item -> item instanceof ItemByWeight)
                .map(Item::getWeightInKilos)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);

        return netWeight.doubleValue() >= 1.0 ? new BigDecimal("0.50") : new BigDecimal("0.00");
    }
}
