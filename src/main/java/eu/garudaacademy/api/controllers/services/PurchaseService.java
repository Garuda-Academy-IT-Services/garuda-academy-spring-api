package eu.garudaacademy.api.controllers.services;

import eu.garudaacademy.api.models.authentication.LoggedInUser;
import eu.garudaacademy.api.models.entity.Category;
import eu.garudaacademy.api.models.entity.Purchase;
import eu.garudaacademy.api.models.entity.User;
import eu.garudaacademy.api.models.responses.IdResponse;
import eu.garudaacademy.api.repository.CategoryRepository;
import eu.garudaacademy.api.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {

    public static String ONE_DAY_PURCHASE = "a4eb72f2-b5b3-4dfd-a942-152e979ae7b8";
    public static String ONE_MONTH_PURCHASE = "41573542-1b1a-4d9c-bd5c-500475ad499b";

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void makePurchase(String purchaseId) {
        final LoggedInUser loggedInUser = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final List<Category> categories = categoryRepository.findAllByIsCommercialTrue();

        if (purchaseId.equals(ONE_DAY_PURCHASE)) {
            makeOneDayPurchase(categories, loggedInUser);

        } else if (purchaseId.equals(ONE_MONTH_PURCHASE)) {
            makeOneMonthPurchase(categories, loggedInUser);
        } else {
            throw new RuntimeException("Invalid purchase id!");

        }
    }

    public void makeOneDayPurchase(final List<Category> categories, final LoggedInUser loggedInUser) {
        categories.forEach(c -> {
            Purchase purchase = new Purchase();

            purchase.setCategory(c);
            purchase.setUser(loggedInUser.getUserEntity());
            purchase.setValidUntil(LocalDate.now().plusDays(1));

            purchaseRepository.save(purchase);
        });
    }

    public void makeOneMonthPurchase(final List<Category> categories, final LoggedInUser loggedInUser) {
        categories.forEach(c -> {
            Purchase purchase = new Purchase();

            purchase.setCategory(c);
            purchase.setUser(loggedInUser.getUserEntity());
            purchase.setValidUntil(LocalDate.now().plusMonths(1));

            purchaseRepository.save(purchase);
        });
    }
}
