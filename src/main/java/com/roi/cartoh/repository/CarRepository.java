package com.roi.cartoh.repository;

import com.roi.cartoh.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByMakeContainingIgnoreCaseOrLicensePlateNumberContainingIgnoreCase(String makeKeyword, String licenseKeyword);

}
