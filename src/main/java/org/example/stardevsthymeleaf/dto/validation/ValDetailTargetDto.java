package org.example.stardevsthymeleaf.dto.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ValDetailTargetDto {
    private Long id;
    private Integer targetName;
    private Double hargaTarget;
    private LocalDate tanggalSelesaiTarget;
    private Double danaTerkumpul;
    private String catatan;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTargetName() {
        return targetName;
    }

    public void setTargetName(Integer targetName) {
        this.targetName = targetName;
    }

    public Double getHargaTarget() {
        return hargaTarget;
    }

    public void setHargaTarget(Double hargaTarget) {
        this.hargaTarget = hargaTarget;
    }

    public LocalDate getTanggalSelesaiTarget() {
        return tanggalSelesaiTarget;
    }

    public void setTanggalSelesaiTarget(LocalDate tanggalSelesaiTarget) {
        this.tanggalSelesaiTarget = tanggalSelesaiTarget;
    }

    public Double getDanaTerkumpul() {
        return danaTerkumpul;
    }

    public void setDanaTerkumpul(Double danaTerkumpul) {
        this.danaTerkumpul = danaTerkumpul;
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
