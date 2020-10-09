package eu.garudaacademy.api.models.responses.factory;

import eu.garudaacademy.api.models.entity.Category;
import eu.garudaacademy.api.models.entity.Purchase;
import eu.garudaacademy.api.models.responses.CategoryWithPurchasesResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryWithPurchasesResponseFactory {

    public List<CategoryWithPurchasesResponse> build(final List<Category> categories, final List<Purchase> purchases) {


        List<CategoryWithPurchasesResponse> categoryWithPurchasesResponse = new ArrayList<>();


        categories.forEach((category) -> {
            List<Purchase> userPurchaseForCategory = this.getUserPurchaseForCategory(purchases, category);

            categoryWithPurchasesResponse.add(CategoryWithPurchasesResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description("")
                    .url(category.getUrl())
                    .isCommercial(category.getIsCommercial())
                    .isPurchased(userPurchaseForCategory.size() != 0)
                    .build());
        });

        return categoryWithPurchasesResponse;
    }

    private List<Purchase> getUserPurchaseForCategory(final List<Purchase> purchases, final Category category) {
        return purchases.stream()
                .filter(purchase -> purchase.getCategory().getId() == category.getId())
                .collect(Collectors.toList());
    }
}
