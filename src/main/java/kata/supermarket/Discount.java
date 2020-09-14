package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;

public interface Discount {

    BigDecimal getDiscountAmount(List<Item> itemList);
}
