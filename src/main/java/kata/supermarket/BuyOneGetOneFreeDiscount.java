package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public class BuyOneGetOneFreeDiscount implements Discount {

    final String discountCode;
    public BuyOneGetOneFreeDiscount(final String discountCode) {
        this.discountCode = discountCode;
    }

    @Override
    public BigDecimal getDiscountAmount(List<Item> itemList) {
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BuyOneGetOneFreeDiscount that = (BuyOneGetOneFreeDiscount) o;
        return Objects.equals(discountCode, that.discountCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountCode);
    }
}
