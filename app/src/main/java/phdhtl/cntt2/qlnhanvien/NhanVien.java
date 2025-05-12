package phdhtl.cntt2.qlnhanvien;
import java.io.Serializable;

public class NhanVien implements Serializable {
    private String nv_id;
    private String nv_name;
    private int nv_ns;
    private float nv_hsl;
    private String nv_cv;
    private String anhnv;
    public NhanVien(){

    }

    @Override
    public String toString() {
        return "nv{" +
                "nv_id='" + nv_id + '\'' +
                ", nv_name='" + nv_name + '\'' +
                ", nv_namsinh=" + nv_ns +
                ", nv_hsl=" + nv_hsl +
                ", nv_chucvu='" + nv_cv + '\'' +
                '}';
    }

    public String getAnhnv() {
        return anhnv;
    }

    public void setAnhnv(String anhnv) {
        this.anhnv = anhnv;
    }

    public NhanVien(String nv_ma, String nv_ten, int nam_sinh, float hsl, String chuc_vu, String anhnv) {
        this.nv_id = nv_ma;
        this.nv_name = nv_ten;
        this.nv_ns = nam_sinh;
        this.nv_hsl = hsl;
        this.nv_cv = chuc_vu;
        this.anhnv = anhnv;
    }

    public NhanVien(String nv_ma, String nv_ten, int nam_sinh, float hsl, String chuc_vu) {
        this.nv_id = nv_ma;
        this.nv_name = nv_ten;
        this.nv_ns = nam_sinh;
        this.nv_hsl = hsl;
        this.nv_cv = chuc_vu;
    }

    public String getNv_id() {
        return nv_id;
    }

    public void setNv_id(String nv_ma) {
        this.nv_id = nv_ma;
    }

    public String getNv_name() {
        return nv_name;
    }

    public void setNv_name(String nv_ten) {
        this.nv_name = nv_ten;
    }

    public int getNv_ns() {
        return nv_ns;
    }

    public void setNv_ns(int nam_sinh) {
        this.nv_ns = nam_sinh;
    }

    public float getNv_hsl() {
        return nv_hsl;
    }

    public void setNv_hsl(float hsl) {
        this.nv_hsl = hsl;
    }

    public String getNv_cv() {
        return nv_cv;
    }

    public void setNv_cv(String chuc_vu) {
        this.nv_cv = chuc_vu;
    }
}
