package com.meenah.meenahsignature.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
//    @Modifying
//    @Query("update Product p set p.imageLink = ?2 where p.id = ?1")
//    void updateProduct(@Param(value = "id") Long id, @Param(value = "imageLink") String imageLink);
}
