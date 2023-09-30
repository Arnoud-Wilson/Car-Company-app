package com.novi.carcompany.helpers;

import com.novi.carcompany.dtos.CarDto;
import com.novi.carcompany.dtos.CarInputDto;
import com.novi.carcompany.dtos.PartDto;
import com.novi.carcompany.dtos.PartInputDto;
import com.novi.carcompany.models.Car;
import com.novi.carcompany.models.Part;

public class DtoConverters {

    public static void carInputDtoConverter(Car car, CarInputDto dto) {
        car.setLicensePlate(dto.licensePlate.toUpperCase());
        car.setBrand(dto.brand);
        car.setModel(dto.model);
        car.setVinNumber(dto.vinNumber.toUpperCase());
        car.setColor(dto.color);
        car.setEngine(dto.engine);
        car.setWinterTyres(dto.winterTyres);
    }

    public static void carDtoConverter(Car car, CarDto dto) {
        dto.licensePlate = car.getLicensePlate();
        dto.brand = car.getBrand();
        dto.model = car.getModel();
        dto.vinNumber = car.getVinNumber();
        dto.color = car.getColor();
        dto.engine = car.getEngine();
        dto.winterTyres = car.getWinterTyres();
    }

    public static void partInputDtoConverter(Part part, PartInputDto dto) {
        part.setPartNumber(dto.partNumber.toUpperCase());
        part.setName(dto.name);
        part.setDescription(dto.description);
        part.setLocation(dto.location);
        part.setPurchasePrice(dto.purchasePrice);
        part.setSellingPrice(dto.sellingPrice);
    }

    public static void partDtoConverter(Part part, PartDto dto) {
        dto.partNumber = part.getPartNumber();
        dto.name = part.getName();
        dto.description = part.getDescription();
        dto.location = part.getLocation();
        dto.stock = part.getStock();
        dto.purchasePrice = part.getPurchasePrice();
        dto.sellingPrice = part.getSellingPrice();
    }
}
