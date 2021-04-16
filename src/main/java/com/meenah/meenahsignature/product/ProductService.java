package com.meenah.meenahsignature.product;

import com.meenah.meenahsignature.bucket.Bucket;
import com.meenah.meenahsignature.filestore.FileStore;
import com.meenah.meenahsignature.payload.ApiResponse;
import com.meenah.meenahsignature.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Validator validator;
    private final FileStore fileStore;

    public ResponseEntity<?> addProduct(Product product) {
        productRepository.save(product);
        return ResponseEntity.ok().body(
            new ApiResponse(true,
                            "Product successfully saved to database."));
    }

    public ResponseEntity<?> updateProduct(Product product) {

        productRepository.save(product);

        return ResponseEntity.ok().body(
            new ApiResponse(true,
                            "Product successfully Updated."));

    }


    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ResponseEntity<Product> getProduct(Long productId){
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent())
            return new ResponseEntity(new ApiResponse(false, "Product not found."),
                                        HttpStatus.NOT_FOUND);

        return ResponseEntity.of(product);

    }

    public ResponseEntity<?> deleteProduct(Long productId){
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "Product not found."),
                                        HttpStatus.NOT_FOUND);
        }

        productRepository.delete(product.get());

        return ResponseEntity.ok().body(
            new ApiResponse(true,
                            "Product successfully deleted."));

    }


    public ResponseEntity<?> uploadProductImage(Long productId,MultipartFile file) {
        if(file.getOriginalFilename().isEmpty()){
            return new ResponseEntity<>(new ApiResponse(false, "No file is uploaded"),
                                        HttpStatus.BAD_REQUEST);
        }

        if (!validator.validateFile(file.getOriginalFilename())) {
            return new ResponseEntity<>(new ApiResponse(false, "Image must be in jpeg|png|bmp format."),
                                        HttpStatus.BAD_REQUEST);
        }


        Product product = productRepository.getOne(productId);
        if(product.getId() == null){
            return new ResponseEntity<>(new ApiResponse(false, "Product not found"),
                                        HttpStatus.NOT_FOUND);
        }

        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", file.getContentType());
        map.put("Size", String.valueOf(file.getSize()));
        map.put("Name", file.getOriginalFilename());

        String path = String.format("%s/%s", Bucket.PRODUCT_IMAGE.getName(), product.getId());
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, fileName, Optional.of(map), file.getInputStream());
            //Setting imageLink
            product.setImageLink(fileName);
            productRepository.save(product);
        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse(false, "Something went wrong"),
                                        HttpStatus.BAD_REQUEST);
        }


        return ResponseEntity.ok().body(new ApiResponse(true, "Product successfully saved to s3."));

    }

    public byte[] downloadProductImage(Long productId){
        Product product = productRepository.getOne(productId);
        if(product.getId() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");
        }

        String path = String.format("%s/%s", Bucket.PRODUCT_IMAGE.getName(), product.getId());
        return product.getImageLink().map(key -> fileStore.download(path,key)).orElse(new byte[0]);

    }

}

