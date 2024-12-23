package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TheNhanVien")
@Data
public class TheNhanVien {
    @Id
    @Column(name = "maThe")
    private String maThe;

    @Column(name = "maNV")
    private String maNV;

}
