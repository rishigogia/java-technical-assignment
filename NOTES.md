# Notes

The missing functionality of Discounts has been added and a couple of Discount classes have been provided to make sure the functionality is working.

- BuyOneGetOneFreeDiscount - Buy One Get One Free
- Less50pForOneKiloDiscount 50p less for One Kilo of vegetables

The functionality is scalable, so if more discounts need to be added, adding a class for that Discount implementation simply needs to be added.

However, I've tried to make sure the existing functionality is not impacted, (all previous test cases passing without any changes), there have been a few modifications made in the existing classes.

For e.g.
- Discount is associated with a product, therefore Discount has been added to both the product classes.
- Discount must be returned by the item, therefore discount (from the product the item is created from) is also returning the discount.
- Another functionality is added to Item interface to fetch the weight (because this was required to get the discount amount). But this is made default, so no classes would have to implement it, that don't need it. At the moment the name is `getWeightInKilos()`, however it can simply be renamed to `getQuantity()`
 
