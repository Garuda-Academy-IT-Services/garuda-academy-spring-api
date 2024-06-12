package eu.garudaacademy.api.controllers;

import eu.garudaacademy.api.models.constants.ApiPaths;
import eu.garudaacademy.api.models.responses.factory.PurchaseCreateResponseFactory;
import eu.garudaacademy.api.models.entity.Purchase;
import eu.garudaacademy.api.models.responses.PurchaseCreateResponse;
import eu.garudaacademy.api.models.exception.ResourceNotFoundException;
import eu.garudaacademy.api.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(ApiPaths.PURCHASES_BASE)
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseCreateResponseFactory purchaseCreateResponseFactory;

    @GetMapping(ApiPaths.GET_ALL)
    public List<Purchase> getAll() {
        return this.purchaseRepository.findAll();
    }

    @GetMapping(ApiPaths.GET_BY_ID)
    public Purchase getById(@PathVariable(value = "id") final long purchaseId) {
        return this.purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found: " + purchaseId));
    }

    @RequestMapping(
        value = ApiPaths.CREATE,
        produces = "application/json",
        method = {RequestMethod.POST})
    public PurchaseCreateResponse create(@RequestBody final Purchase purchase) {
        final Purchase dbResponse = this.purchaseRepository.save(purchase);

        return purchaseCreateResponseFactory.build(dbResponse);
    }
}
