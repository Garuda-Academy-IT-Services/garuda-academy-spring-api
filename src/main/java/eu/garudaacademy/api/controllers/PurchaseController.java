package eu.garudaacademy.api.controllers;

import eu.garudaacademy.api.models.entity.responses.factory.PurchaseCreateResponseFactory;
import eu.garudaacademy.api.models.entity.Purchase;
import eu.garudaacademy.api.models.entity.responses.PurchaseCreateResponse;
import eu.garudaacademy.api.models.exception.ResourceNotFoundException;
import eu.garudaacademy.api.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseCreateResponseFactory purchaseCreateResponseFactory;

    @GetMapping("/get-all")
    public List<Purchase> getAll() {
        return this.purchaseRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Purchase getById(@PathVariable(value = "id") final long purchaseId) {
        return this.purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found: " + purchaseId));
    }

    @RequestMapping(
        value = "/create",
        produces = "application/json",
        method = {RequestMethod.POST})
    public PurchaseCreateResponse create(@RequestBody final Purchase purchase) {
        final Purchase dbResponse = this.purchaseRepository.save(purchase);

        return purchaseCreateResponseFactory.build(dbResponse);
    }
}
