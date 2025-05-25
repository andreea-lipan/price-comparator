package accesa.pricecomparatorbe.services;

import java.util.List;
import org.springframework.stereotype.Service;
import accesa.pricecomparatorbe.model.Brand;
import accesa.pricecomparatorbe.dtos.BrandDTO;
import accesa.pricecomparatorbe.validators.ValidationException;

@Service
public interface BrandService {

    void addBrand(BrandDTO brandDTO) throws ValidationException;
    
    List<Brand> getBrands();

    Brand getBrandById(Long brandId);
}
