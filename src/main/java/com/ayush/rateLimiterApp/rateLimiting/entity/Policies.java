package com.ayush.rateLimiterApp.rateLimiting.entity;

import com.ayush.rateLimiterApp.tenantManagement.entity.Tenants;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Policies {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "tenantId")
    private Tenants tenant;
    private int capacity;
    private double refillRate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Tenants getTenant() {
        return tenant;
    }

    public void setTenant(Tenants tenant) {
        this.tenant = tenant;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getRefillRate() {
        return refillRate;
    }

    public void setRefillRate(double refillRate) {
        this.refillRate = refillRate;
    }
}
