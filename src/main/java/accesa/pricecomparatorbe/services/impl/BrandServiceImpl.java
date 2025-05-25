package accesa.pricecomparatorbe.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import accesa.pricecomparatorbe.services.BrandService;
import accesa.pricecomparatorbe.dtos.BrandDTO;
import accesa.pricecomparatorbe.validators.ValidationException;
import accesa.pricecomparatorbe.validators.BrandValidator;
import accesa.pricecomparatorbe.model.Brand;
import accesa.pricecomparatorbe.persistence.BrandRepository;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandValidator brandValidator;

    public BrandServiceImpl(BrandRepository brandRepository, BrandValidator brandValidator) {
        this.brandRepository = brandRepository;
        this.brandValidator = brandValidator;
    }

    @Override
    public void addBrand(BrandDTO brandDTO) throws ValidationException {
        brandValidator.validateBrandDTO(brandDTO);

        Brand brand = Brand.builder()
            .name(brandDTO.getName())
            .build();

        brandRepository.save(brand);
    }

    @Override
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found!"));
    }
}
