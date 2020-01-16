package com.news.app.controller;

import com.news.app.entity.dto.registration.RegistrationErrorInfoDto;
import com.news.app.entity.dto.registration.RegistrationRequestDto;
import com.news.app.entity.dto.registration.RegistrationResponseStatus;
import com.news.app.exception.EmailSendingException;
import com.news.app.exception.registration.EmailAlreadyExistException;
import com.news.app.exception.registration.NewUserCreatingException;
import com.news.app.exception.registration.RegistrationDataNotFoundException;
import com.news.app.exception.registration.RegistrationDataSavingException;
import com.news.app.exception.registration.UsernameAlreadyExistException;
import com.news.app.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public RegistrationRequestDto addUser (@RequestBody RegistrationRequestDto registrationRequestDto){
        return registrationService.register(registrationRequestDto);
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        return registrationService.activateUser(code) ? "Спасибо за подтверждение" : "Произошла ошибка";
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler({UsernameAlreadyExistException.class, EmailAlreadyExistException.class})
    public @ResponseBody
    RegistrationErrorInfoDto suchUserAlreadyExist(HttpServletRequest request, Exception exception) {
        return new RegistrationErrorInfoDto(request.getRequestURL().toString(),
                exception, RegistrationResponseStatus.SUCH_USER_ALREADY_EXIST);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RegistrationDataSavingException.class})
    public @ResponseBody RegistrationErrorInfoDto badSavingData(HttpServletRequest request, Exception exception) {
        return new RegistrationErrorInfoDto(request.getRequestURL().toString(),
                exception, RegistrationResponseStatus.SENDING_EMAIL_ERROR);
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler({EmailSendingException.class})
    public @ResponseBody RegistrationErrorInfoDto badEmailSending(HttpServletRequest request, Exception exception) {
        return new RegistrationErrorInfoDto(request.getRequestURL().toString(),
                exception, RegistrationResponseStatus.SENDING_EMAIL_ERROR);
    }

    @ResponseStatus(value = HttpStatus.GONE)
    @ExceptionHandler({RegistrationDataNotFoundException.class})
    public @ResponseBody RegistrationErrorInfoDto registrationDataNotFound(HttpServletRequest request, Exception exception) {
        return new RegistrationErrorInfoDto(request.getRequestURL().toString(),
                exception, RegistrationResponseStatus.REGISTRATION_DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler({NewUserCreatingException.class})
    public @ResponseBody RegistrationErrorInfoDto badUserCreating(HttpServletRequest request, Exception exception) {
        return new RegistrationErrorInfoDto(request.getRequestURL().toString(),
                exception, RegistrationResponseStatus.USER_CREATING_ERROR);
    }

}
