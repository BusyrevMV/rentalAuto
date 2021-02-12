package com.ertelecom.carrental.service;

import com.ertelecom.carrental.request.RentalHistoryAddRequest;
import com.ertelecom.carrental.request.RentalHistoryCloseRequest;
import com.ertelecom.carrental.model.entity.RentalHistory;
import com.ertelecom.carrental.model.view.RentalHistoryAvgReportByBrandView;
import com.ertelecom.carrental.model.view.RentalHistoryAvgReportByModelView;
import com.ertelecom.carrental.model.view.RentalHistoryView;
import com.ertelecom.carrental.model.filter.RentalFilter;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRentalHistoryService {
    List<RentalHistory> load();

    List<RentalHistoryView> loadByCarBrandAndCarModel(RentalFilter rentalFilter);

    List<RentalHistoryAvgReportByBrandView> loadReportAvgRentalByCarBrand(RentalFilter rentalFilter);

    List<RentalHistoryAvgReportByModelView> loadReportAvgRentalByCarModel(RentalFilter rentalFilter);

    Optional<RentalHistory> loadById(long id);

    RentalHistory add(RentalHistoryAddRequest rentalHistoryAddRequest) throws SQLException;

    RentalHistory close(RentalHistoryCloseRequest rentalHistoryAddDTO) throws SQLException;

    boolean deleteById(long id);
}
