package com.ertelecom.carrental.controller;


import com.ertelecom.carrental.config.jwt.JwtProvider;
import com.ertelecom.carrental.model.entity.ProgUser;
import com.ertelecom.carrental.model.view.ProgUserView;
import com.ertelecom.carrental.request.AuthRequest;
import com.ertelecom.carrental.response.AuthResponse;
import com.ertelecom.carrental.response.DataResponse;
import com.ertelecom.carrental.response.ErrorArgsResponse;
import com.ertelecom.carrental.response.ErrorResponse;
import com.ertelecom.carrental.service.IProgUserService;
import com.ertelecom.carrental.service.impl.ProgUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class ProgUserController {
    @Autowired
    private IProgUserService progUserService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) throws SQLException {
        Optional<ProgUser> progUser = progUserService.findByLoginAndPassword(request.login, request.password);
        if (!progUser.isPresent()){
            return new ResponseEntity<>(new AuthResponse(""), HttpStatus.BAD_REQUEST);
        }
        String token = jwtProvider.generateToken(progUser.get().getUsername());

        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @RequestMapping(value = "/progUser/get", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse get() {
        DataResponse response = new DataResponse();
        response.result = progUserService.load()
                .stream()
                .map((s)-> new ProgUserView(s))
                .collect(Collectors.toList());

        return response;
    }

    @RequestMapping(value = "/progUser/edit", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgUser> getForEdit(@RequestParam("id") long id) {
        ProgUser result = progUserService.loadById(id).orElse(null);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.GONE);
    }

    @RequestMapping(value = "/progUser/edit", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProgUser edit(@Valid @RequestBody ProgUser progUser) throws SQLException {
        progUser = progUserService.save(progUser);
        return progUser;
    }

    @RequestMapping(value = "/progUser/delete", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> delete(@RequestParam("id") long id) {
        return new ResponseEntity<>(id, progUserService.deleteById(id) ? HttpStatus.OK : HttpStatus.GONE);
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
