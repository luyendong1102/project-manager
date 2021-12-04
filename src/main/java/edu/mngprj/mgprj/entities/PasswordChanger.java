package edu.mngprj.mgprj.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordChanger implements Serializable {
    private String oldpassword;
    private String newpassword;
}
