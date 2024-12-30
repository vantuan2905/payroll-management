package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "OverTime")
@Data
public class OverTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
   public Long id;

    @Column(name = "loai")
    public String loai;

    @Column(name = "moTa")
    public String moTa;

    @Column(name = "heSo")
    public double heSo;
}
