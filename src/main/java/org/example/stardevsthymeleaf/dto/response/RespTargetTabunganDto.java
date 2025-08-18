package org.example.stardevsthymeleaf.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RespTargetTabunganDto {

    private Long id;
    private String targetName;
    private Long jumlahDataTarget;
    private Long saldoTerkumpul;
    private String periode;
    private String deskripsi;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonProperty("tanggal_mulai_target")
    private LocalDate tanggalMulaiTarget;
    private LocalDate tanggalSelesaiTarget;
    private String statusTarget;
    private Long userId;
    private String username;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTargetName() { return targetName; }
    public void setTargetName(String targetName) { this.targetName = targetName; }

    public Long getJumlahDataTarget() { return jumlahDataTarget; }
    public void setJumlahDataTarget(Long jumlahDataTarget) { this.jumlahDataTarget = jumlahDataTarget; }

    public Long getSaldoTerkumpul() { return saldoTerkumpul; }
    public void setSaldoTerkumpul(Long saldoTerkumpul) { this.saldoTerkumpul = saldoTerkumpul; }

    public String getPeriode() { return periode; }
    public void setPeriode(String periode) { this.periode = periode; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public LocalDate getTanggalMulaiTarget() { return tanggalMulaiTarget; }
    public void setTanggalMulaiTarget(LocalDate tanggalMulaiTarget) { this.tanggalMulaiTarget = tanggalMulaiTarget; }

    public LocalDate getTanggalSelesaiTarget() { return tanggalSelesaiTarget; }
    public void setTanggalSelesaiTarget(LocalDate tanggalSelesaiTarget) { this.tanggalSelesaiTarget = tanggalSelesaiTarget; }

    public String getStatusTarget() { return statusTarget; }
    public void setStatusTarget(String statusTarget) { this.statusTarget = statusTarget; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
