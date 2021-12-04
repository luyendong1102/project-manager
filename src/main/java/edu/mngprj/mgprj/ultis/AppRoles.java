package edu.mngprj.mgprj.ultis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum AppRoles {
    ADMINTESTER(1L, "ROLE_ADMIN_TESTER"),
    ADMIN(2L, "ROLE_ADMIN"),
    QUANLY(3L, "ROLE_QUANLY"),
    NHANVIEN(4L, "ROLE_NHANVIEN");

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
