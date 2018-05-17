package com.news.app.controller;

import com.news.app.entity.dto.registration.RegistrationErrorInfoDto;
import com.news.app.entity.dto.registration.RegistrationRequestDto;
import com.news.app.entity.dto.registration.RegistrationResponseStatus;
import com.news.app.exception.EmailSendingException;
import com.news.app.exception.registration.*;
import com.news.app.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        boolean isActivated = registrationService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found");
        }

        return "login";
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
