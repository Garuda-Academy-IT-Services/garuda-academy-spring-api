package eu.garudaacademy.api.models.responses.factory;

import eu.garudaacademy.api.models.entity.Purchase;
import eu.garudaacademy.api.models.responses.PurchaseCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseCreateResponseFactory {

    @Autowired
    private IdResponseFactory idResponseFactory;

    public PurchaseCreateResponse build(final Purchase rawPurchase) {
        return PurchaseCreateResponse.builder()
                .id(rawPurchase.getId())
                .user(idResponseFactory.build(rawPurchase.getUser()))
                .category(idResponseFactory.build(rawPurchase.getCategory()))
                .build();
    }
}
