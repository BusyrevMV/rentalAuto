package com.ertelecom.carrental.response;

import java.util.List;

public class DataResponse {
    public int getRowCount(){return result != null ? result.size() : 0;}
    public List<?> result;
}
