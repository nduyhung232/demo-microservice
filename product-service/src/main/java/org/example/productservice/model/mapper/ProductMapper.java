package org.example.productservice.model.mapper;

import org.example.productservice.model.dto.ProductCreateDTO;
import org.example.productservice.model.dto.ProductDTO;
import org.example.productservice.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product createDTOToEntity(ProductCreateDTO userCreateDTO);

    ProductDTO entityToDTO(Product product);
}
