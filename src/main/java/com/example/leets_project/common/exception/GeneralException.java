package com.example.leets_project.common.exception;

import com.example.leets_project.common.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{

    private ErrorCode errorCode;
}
