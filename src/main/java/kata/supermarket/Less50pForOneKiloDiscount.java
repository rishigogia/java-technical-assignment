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
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }
}
