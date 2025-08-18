package org.example.stardevsthymeleaf.dto.validation;

import java.time.LocalDateTime;

public class ValTransactionDto {
    private Long id;

    private String jenisTransaksi;
    private Double jumlahTransaksi;
    private String metodePembayaran;
    private String statusTransaksi;
    private String keterangan;
    private String referensiPembayaran;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String username;
    private Long targetTabunganId;
    private String targetName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public Double getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public void setJumlahTransaksi(Double jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public String getStatusTransaksi() {
        return statusTransaksi;
    }

    public void setStatusTransaksi(String statusTransaksi) {
        this.statusTransaksi = statusTransaksi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getReferensiPembayaran() {
        return referensiPembayaran;
    }

    public void setReferensiPembayaran(String referensiPembayaran) {
        this.referensiPembayaran = referensiPembayaran;
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

    public Long getTargetTabunganId() {
        return targetTabunganId;
    }

    public void setTargetTabunganId(Long targetTabunganId) {
        this.targetTabunganId = targetTabunganId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}