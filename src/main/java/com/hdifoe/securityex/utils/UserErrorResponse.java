package com.hdifoe.securityex.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserErrorResponse {
    private String message;
    private long timeStamp;
}
