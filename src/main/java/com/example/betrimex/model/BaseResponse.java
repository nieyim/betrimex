package com.example.betrimex.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {

    private int statusCode;

    private String message;

    private Object data;
}