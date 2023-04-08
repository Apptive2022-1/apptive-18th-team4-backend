package com.hot6.pnureminder.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "annualplan")
public class AnnualPlan {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(name = "date")
    private String date;
    @Column(name = "context")
    private String context;

    @Column(name = "state")
    private String state;


}