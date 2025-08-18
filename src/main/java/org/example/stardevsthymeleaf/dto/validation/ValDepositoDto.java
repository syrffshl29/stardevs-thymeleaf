package org.example.stardevsthymeleaf.dto.validation;

import java.time.LocalDateTime;

public class ValDepositoDto {

    private Long id;
    private Double jumlahSetoran;
    private String metodePembayaran;
    private String sumberDana;
    private String buktiSetoran;
    private LocalDateTime tanggalSetoran;
    private String catatanSetoran;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String statusVerifikasi;
    private Long userId;
    private String username;
    private Long targetTabunganId;
    private String targetName;
    private Long transaksiTabunganId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getJumlahSetoran() {
        return jumlahSetoran;
    }

    public void setJumlahSetoran(Double jumlahSetoran) {
        this.jumlahSetoran = jumlahSetoran;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public String getSumberDana() {
        return sumberDana;
    }

    public void setSumberDana(String sumberDana) {
        this.sumberDana = sumberDana;
    }

    public String getBuktiSetoran() {
        return buktiSetoran;
    }

    public void setBuktiSetoran(String buktiSetoran) {
        this.buktiSetoran = buktiSetoran;
    }

    public LocalDateTime getTanggalSetoran() {
        return tanggalSetoran;
    }

    public void setTanggalSetoran(LocalDateTime tanggalSetoran) {
        this.tanggalSetoran = tanggalSetoran;
    }

    public String getCatatanSetoran() {
        return catatanSetoran;
    }

    public void setCatatanSetoran(String catatanSetoran) {
        this.catatanSetoran = catatanSetoran;
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

    public String getStatusVerifikasi() {
        return statusVerifikasi;
    }

    public void setStatusVerifikasi(String statusVerifikasi) {
        this.statusVerifikasi = statusVerifikasi;
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

    public Long getTransaksiTabunganId() {
        return transaksiTabunganId;
    }

    public void setTransaksiTabunganId(Long transaksiTabunganId) {
        this.transaksiTabunganId = transaksiTabunganId;
    }
}