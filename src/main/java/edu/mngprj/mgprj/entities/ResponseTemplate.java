package edu.mngprj.mgprj.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseTemplate implements Serializable {
    private int code;
    private String message;
    private Object content;
}
