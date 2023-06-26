package com.team.tesbro.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class ResultData<T> {
    String resultCode;
    String msg;
    T body;

    public ResultData(String resultCode, String msg) {
    }
}
