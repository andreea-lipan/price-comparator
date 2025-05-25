package accesa.pricecomparatorbe.services;

import java.util.List;
import accesa.pricecomparatorbe.model.Retailer;
import accesa.pricecomparatorbe.dtos.RetailerDTO;
import accesa.pricecomparatorbe.validators.ValidationException;

public interface RetailerService {

    void addRetailer(RetailerDTO retailer) throws ValidationException;

    List<Retailer> getRetailers();

    Retailer getRetailerById(Long retailerId);
}