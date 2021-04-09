package com.meenah.meenahsignature.product;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "productmanagement/api/v1")
@AllArgsConstructor
public class ProductApi {

    private final ProductService productService;

    @GetMapping(path = "/all")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("{productId}/delete")
   public ResponseEntity<?> deleteProduct(@PathVariable(value = "productId") Long productId){
        return productService.deleteProduct(productId);
   }

    @PostMapping(path = "{productId}/image/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProductImage(@PathVariable(value = "productId") Long productId,
        @RequestParam(value = "file")MultipartFile file){

        return productService.uploadProductImage(productId,file);
    }

    @GetMapping(path = "{productId}/image/download")
    public ResponseEntity<?> downloadProductImage(@PathVariable(value = "productId") Long productId){

        return productService.downloadProductImage(productId);
    }


}
