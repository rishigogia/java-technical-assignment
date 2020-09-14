package kata.supermarket;

import java.math.BigDecimal;

public interface Item {
    BigDecimal price();
    Discount getDiscount();

    default BigDecimal getWeightInKilos() {
        throw new UnsupportedOperationException("Not Supported");
    }
}
