package org.example.stardevsthymeleaf.dto.validation;

import java.time.LocalDate;

public class ValTargetTabunganDto {

    private Long id;
    private String targetName;
    private Double hargaTarget;
    private Double danaTerkumpul;
    private String periode;
    private String deskripsi;
    private LocalDate tanggalMulaiTarget;
    private LocalDate tanggalSelesaiTarget;
    private String statusTarget;
    private Long userId;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Double getHargaTarget() {
        return hargaTarget;
    }

    public void setHargaTarget(Double hargaTarget) {
        this.hargaTarget = hargaTarget;
    }

    public Double getDanaTerkumpul() {
        return danaTerkumpul;
    }

    public void setDanaTerkumpul(Double danaTerkumpul) {
        this.danaTerkumpul = danaTerkumpul;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public LocalDate getTanggalMulaiTarget() {
        return tanggalMulaiTarget;
    }

    public void setTanggalMulaiTarget(LocalDate tanggalMulaiTarget) {
        this.tanggalMulaiTarget = tanggalMulaiTarget;
    }

    public LocalDate getTanggalSelesaiTarget() {
        return tanggalSelesaiTarget;
    }

    public void setTanggalSelesaiTarget(LocalDate tanggalSelesaiTarget) {
        this.tanggalSelesaiTarget = tanggalSelesaiTarget;
    }

    public String getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(String statusTarget) {
        this.statusTarget = statusTarget;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
