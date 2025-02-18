package org.example.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.productservice.model.dto.ProductCreateDTO;
import org.example.productservice.model.dto.ProductDTO;
import org.example.productservice.model.entity.Product;
import org.example.productservice.model.mapper.ProductMapper;
import org.example.productservice.repository.master.MasterProductRepository;
import org.example.productservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final MasterProductRepository masterProductRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> search(String nameParam, String categoryNameParam) {
        return null;
    }

    @Override
    public ProductDTO findById(Long productId) {
        masterProductRepository.findById(productId);
        return null;
    }

    @Override
    public ProductDTO create(ProductCreateDTO productCreateDTO) {
        // validate

        // save
        Product product = productMapper.createDTOToEntity(productCreateDTO);
        product = masterProductRepository.save(product);
        ProductDTO productDTO = productMapper.entityToDTO(product);
        return productDTO;
    }

    @Override
    public void delete(Long id) {

    }
}
