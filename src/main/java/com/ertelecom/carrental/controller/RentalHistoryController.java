package com.ertelecom.carrental.controller;

import com.ertelecom.carrental.model.entity.ProgUser;
import com.ertelecom.carrental.request.RentalHistoryAddRequest;
import com.ertelecom.carrental.request.RentalHistoryCloseRequest;
import com.ertelecom.carrental.model.entity.RentalHistory;
import com.ertelecom.carrental.model.view.RentalHistoryView;
import com.ertelecom.carrental.model.filter.RentalFilter;
import com.ertelecom.carrental.response.DataResponse;
import com.ertelecom.carrental.response.ErrorArgsResponse;
import com.ertelecom.carrental.response.ErrorResponse;
import com.ertelecom.carrental.service.IRentalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/rental")
public class RentalHistoryController {
    @Autowired
    private IRentalHistoryService rentalHistoryService;

    @RequestMapping(value = "/get", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse getRental(@RequestParam(value = "id") Long id) {
        DataResponse response = new DataResponse();
        if (id != null) {
            List<RentalHistoryView> result = new ArrayList<>();
            rentalHistoryService.loadById(id).map((s)->result.add(new RentalHistoryView(s)));
            response.result = result;
            return response;
        }

        response.result = rentalHistoryService.load()
                .stream()
                .map((s)-> new RentalHistoryView(s))
                .collect(Collectors.toList());

        return response;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse getRental(@RequestBody RentalFilter rentalFilter) {
        DataResponse response = new DataResponse();
        response.result = rentalHistoryService.loadByCarBrandAndCarModel(
                rentalFilter != null ? rentalFilter : new RentalFilter());

        return response;
    }

    @RequestMapping(value = "/getReport", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse getReport(@RequestParam(value = "reportId") int reportId,
                                  @RequestBody(required = false) RentalFilter rentalFilter) {
        DataResponse response = new DataResponse();
        switch (reportId){
            case 1: {
                response.result = rentalHistoryService.loadReportAvgRentalByCarBrand(
                        rentalFilter != null ? rentalFilter : new RentalFilter());
                break;
            }
            case 2: {
                response.result = rentalHistoryService.loadReportAvgRentalByCarModel(
                        rentalFilter != null ? rentalFilter : new RentalFilter());
                break;
            }
        }


        return response;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalHistory> getRentalForEdit(@RequestParam("id") long id) {
        RentalHistory result = rentalHistoryService.loadById(id).orElse(null);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.GONE);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalHistory addRental(@AuthenticationPrincipal ProgUser progUser,
            @Valid @RequestBody RentalHistoryAddRequest rentalHistoryAddRequest) throws SQLException {
        rentalHistoryAddRequest.setProgUserIdBeg(progUser.getId());
        return rentalHistoryService.add(rentalHistoryAddRequest);
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalHistory closeRental(@AuthenticationPrincipal ProgUser progUser,
            @Valid @RequestBody RentalHistoryCloseRequest rentalHistoryCloseRequest) throws SQLException {
        rentalHistoryCloseRequest.setProgUserIdEnd(progUser.getId());
        return rentalHistoryService.close(rentalHistoryCloseRequest);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> deleteRental(@RequestParam("id") long id) {
        return new ResponseEntity<>(id, rentalHistoryService.deleteById(id) ? HttpStatus.OK : HttpStatus.GONE);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleSQLException(SQLException e) {
        ErrorResponse response = new ErrorResponse();
        response.error = e.getClass().getName();
        response.message = e.getMessage();

        return response;
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException e) {
        ErrorResponse response = new ErrorResponse();
        response.error = e.getClass().getName();
        response.message = String.format("Запись с идентификатором \"%s\" была изменена или удалена другим пользователем", e.getIdentifier());

        return response;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ErrorResponse response = new ErrorResponse();
        response.error = e.getRootCause().getClass().getName();
        response.message = e.getRootCause().getMessage();

        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorArgsResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorArgsResponse response = new ErrorArgsResponse();
        response.error = e.getClass().getName();
        for (FieldError fieldError: e.getBindingResult().getFieldErrors()) {
            response.message.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        ErrorResponse response = new ErrorResponse();
        response.error = e.getClass().getName();
        response.message = e.getMessage();
        Iterator<StackTraceElement> iterator = Arrays.stream(e.getStackTrace()).iterator();
        while (iterator.hasNext()){
            response.errorStackTrace.add(iterator.next().toString());
        }

        return response;
    }
}
