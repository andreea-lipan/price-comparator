package accesa.pricecomparatorbe.services.impl;

import org.springframework.stereotype.Service;

import accesa.pricecomparatorbe.services.RetailerService;
import accesa.pricecomparatorbe.dtos.RetailerDTO;
import accesa.pricecomparatorbe.validators.ValidationException;
import accesa.pricecomparatorbe.validators.RetailerValidator;
import java.util.List;
import accesa.pricecomparatorbe.model.Retailer;
import accesa.pricecomparatorbe.persistence.RetailerRepository;

@Service
public class RetailerServiceImpl implements RetailerService {

    private final RetailerRepository retailerRepository;
    private final RetailerValidator retailerValidator;

    public RetailerServiceImpl(RetailerRepository retailerRepository, RetailerValidator retailerValidator) {
        this.retailerRepository = retailerRepository;
        this.retailerValidator = retailerValidator;
    }

    @Override
    public void addRetailer(RetailerDTO retailerDTO) throws ValidationException {
        retailerValidator.validateRetailerDTO(retailerDTO);

        Retailer retailer = Retailer.builder()
            .name(retailerDTO.getName())
            .build();

        retailerRepository.save(retailer);
    }

    @Override
    public List<Retailer> getRetailers() {
        return retailerRepository.findAll();
    }
}
