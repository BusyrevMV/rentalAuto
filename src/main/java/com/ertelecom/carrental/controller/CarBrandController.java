package com.ertelecom.carrental.controller;

import com.ertelecom.carrental.model.entity.CarBrand;
import com.ertelecom.carrental.model.view.CarBrandView;
import com.ertelecom.carrental.response.DataResponse;
import com.ertelecom.carrental.response.ErrorArgsResponse;
import com.ertelecom.carrental.response.ErrorResponse;
import com.ertelecom.carrental.service.ICarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/carBrand")
public class CarBrandController {
    @Autowired
    private ICarBrandService carBrandService;

    @RequestMapping(value = "/get", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse get() {
        DataResponse response = new DataResponse();
        response.result = carBrandService.load()
                .stream()
                .map((s)-> new CarBrandView(s))
                .collect(Collectors.toList());

        return response;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarBrand> getForEdit(@RequestParam("id") long id) {
        CarBrand result = carBrandService.loadById(id).orElse(null);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.GONE);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CarBrand edit(@Valid @RequestBody CarBrand carBrand) {
        carBrand = carBrandService.save(carBrand);
        return carBrand;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> delete(@RequestParam("id") long id) {
        return new ResponseEntity<>(id, carBrandService.deleteById(id) ? HttpStatus.OK : HttpStatus.GONE);
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
