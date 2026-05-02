package com.klef.fsad.exam;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "services")
public class Service {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int id;

    @Column(name = "service_name", nullable = false, length = 100)
    private String name;

    @Column(name = "service_date", nullable = false)
    private LocalDate date;

    @Column(name = "service_status", nullable = false, length = 50)
    private String status;

    @Column(name = "service_description", length = 255)
    private String description;

    @Column(name = "service_category", length = 80)
    private String category;

    @Column(name = "service_price")
    private double price;

    @Column(name = "service_provider", length = 100)
    private String provider;

    @Column(name = "service_location", length = 120)
    private String location;

   
    public Service() {
    }

  
    public Service(String name, LocalDate date, String status,
                   String description, String category,
                   double price, String provider, String location) {
        this.name        = name;
        this.date        = date;
        this.status      = status;
        this.description = description;
        this.category    = category;
        this.price       = price;
        this.provider    = provider;
        this.location    = location;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    

    @Override
    public String toString() {
        return "Service {"
            + "\n  ID          : " + id
            + "\n  Name        : " + name
            + "\n  Date        : " + date
            + "\n  Status      : " + status
            + "\n  Description : " + description
            + "\n  Category    : " + category
            + "\n  Price       : ₹" + price
            + "\n  Provider    : " + provider
            + "\n  Location    : " + location
            + "\n}";
    }
}
