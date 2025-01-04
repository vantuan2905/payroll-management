package org.example.payroll_management.dao;

import jakarta.transaction.Transactional;
import org.example.payroll_management.model.NghiLe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HolidayRepository extends JpaRepository<NghiLe,String> {

    @Query(value = "SELECT * FROM public.nghi_le WHERE _date LIKE CONCAT('%/', ?1) AND loai = false", nativeQuery = true)
    List<NghiLe> findHolidaysByMonth(String month);

    @Query(value = "SELECT * FROM public.nghi_le WHERE loai = true", nativeQuery = true)
    List<NghiLe> timNghiLeAm();

    @Query(value = "SELECT * FROM public.nghi_le WHERE mo_Ta = ?1 ORDER BY id", nativeQuery = true)
    List<NghiLe> timNghiLeTheoLoai(String moTa);

    @Modifying
    @Transactional
    @Query(value = "delete FROM public.nghi_le WHERE mo_Ta = ?1", nativeQuery = true)
    void xoaNghiLe(String loai);
}
