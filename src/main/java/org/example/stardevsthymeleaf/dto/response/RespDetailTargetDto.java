package org.example.stardevsthymeleaf.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RespDetailTargetDto {

    private Long id;
    private String targetName;
    private Double hargaTarget;
    private LocalDate tanggalMulaiTarget;
    private LocalDate tanggalSelesaiTarget;
    private Double danaTerkumpul;
    private String catatan;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double progress;

    // JSON dari backend bernama "transaksiList", tapi kita ingin pakai transaksiTabunganList di Thymeleaf
    @JsonProperty("transaksiList")
    private List<RespTransaksiTabunganDto> transaksiList;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTargetName() { return targetName; }
    public void setTargetName(String targetName) { this.targetName = targetName; }

    public Double getHargaTarget() { return hargaTarget; }
    public void setHargaTarget(Double hargaTarget) { this.hargaTarget = hargaTarget; }

    public LocalDate getTanggalMulaiTarget() { return tanggalMulaiTarget; }
    public void setTanggalMulaiTarget(LocalDate tanggalMulaiTarget) { this.tanggalMulaiTarget = tanggalMulaiTarget; }

    public LocalDate getTanggalSelesaiTarget() { return tanggalSelesaiTarget; }
    public void setTanggalSelesaiTarget(LocalDate tanggalSelesaiTarget) { this.tanggalSelesaiTarget = tanggalSelesaiTarget; }

    public Double getDanaTerkumpul() { return danaTerkumpul; }
    public void setDanaTerkumpul(Double danaTerkumpul) { this.danaTerkumpul = danaTerkumpul; }

    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Double getProgress() { return progress; }
    public void setProgress(Double progress) { this.progress = progress; }

    public List<RespTransaksiTabunganDto> getTransaksiList() {
        return transaksiList;
    }
    public void setTransaksiList(List<RespTransaksiTabunganDto> transaksiList) {
        this.transaksiList = transaksiList;
    }
}
