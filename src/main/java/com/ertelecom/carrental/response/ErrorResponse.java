package com.ertelecom.carrental.response;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    public String error;
    public String message;
    public List<String> errorStackTrace = new ArrayList<>();
}
