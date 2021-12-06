package edu.mngprj.mgprj.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotValidUserException extends Exception {
    private String message;
}
