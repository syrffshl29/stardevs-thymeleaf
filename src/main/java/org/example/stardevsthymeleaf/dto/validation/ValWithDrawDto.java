package org.example.stardevsthymeleaf.dto.validation;

public class ValWithDrawDto {

    private Long targetTabunganId; // target yang ditarik
    private Double jumlahTransaksi; // nominal yang ditarik
    private String jenisTransaksi; // otomatis "WITHDRAW"
    private String keterangan; // opsional, catatan transaksi
    private Long userId; // user yang menarik dana

    public Long getTargetTabunganId() {
        return targetTabunganId;
    }

    public void setTargetTabunganId(Long targetTabunganId) {
        this.targetTabunganId = targetTabunganId;
    }

    public Double getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public void setJumlahTransaksi(Double jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
