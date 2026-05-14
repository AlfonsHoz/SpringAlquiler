package com.alfonso.vehiculo.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String brand;
    private String model;

    @Column(name = "vehicleYear")
    private Integer year;
    private String plate;
    private String color;
    private BigDecimal pricePerDay;
    private boolean available;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    public Vehicle(
            long id,
            String brand,
            String model,
            Integer year,
            String plate,
            String color,
            BigDecimal pricePerDay,
            boolean available,
            VehicleType type
    ) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.plate = plate;
        this.color = color;
        this.pricePerDay = pricePerDay;
        this.available = available;
        this.type = type;
    }

    public Vehicle() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }
}
