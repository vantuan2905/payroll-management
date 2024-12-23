package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "NghiLe")
@Data
public class NghỉLe {
    @Id
    @Column(name = "_date")
    private String _date;

    @Column(name = "moTa")
    private Long moTa;
}
