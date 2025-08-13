package org.example.stardevsthymeleaf.dto.validation;

import java.time.LocalDateTime;

public class ValDetailTargetDto {
    private Long id;
    private Long targetId;
    private Integer targetHarian;
    private Integer targetMingguan;
    private String strategiMenabung;
    private String catatan;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getTargetHarian() {
        return targetHarian;
    }

    public void setTargetHarian(Integer targetHarian) {
        this.targetHarian = targetHarian;
    }

    public Integer getTargetMingguan() {
        return targetMingguan;
    }

    public void setTargetMingguan(Integer targetMingguan) {
        this.targetMingguan = targetMingguan;
    }

    public String getStrategiMenabung() {
        return strategiMenabung;
    }

    public void setStrategiMenabung(String strategiMenabung) {
        this.strategiMenabung = strategiMenabung;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
