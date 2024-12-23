package org.example.payroll_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Admin")
@Data
public class Admin extends ThanhVien{
    @Id
    @Column(name = "MaAdmin")
    String maAdmin;

}
