package com.news.app.security.ulogin;

import com.news.app.entity.dto.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import java.net.URL;
import java.net.URLConnection;

@Component
public class UloginParser {

    @Autowired
    private UloginAuthentication uloginAuthentication;

    public LoginRequestDto getUser(String token) {

        LoginRequestDto uLoginUser = new LoginRequestDto();

        try {
            String host = "http://localhost:4200/";
            URL uloginUrl = new URL("http://ulogin.ru/token.php?token=" + token + "&host="+ host);
            URLConnection urlConnection = uloginUrl.openConnection();

            JsonReader jsonReader = Json.createReader(urlConnection.getInputStream());
            JsonObject obj = jsonReader.readObject();

            if (obj == null ) {
                throw new BadCredentialsException("ulogin did't return json object");
            }

            if(obj.getJsonString("identity") == null){
                throw new BadCredentialsException("ulogin did't return identity object");
            }

            uLoginUser.setUsername(getStringProp(obj, "uid"));
            System.out.println(uLoginUser.getUsername());
            System.out.println("-----------------");
            uLoginUser.setPassword(getStringProp(obj, "identity"));
            System.out.println(uLoginUser.getPassword());
            System.out.println();

            uloginAuthentication.attemptAuthentication(uLoginUser);
        } catch (Exception ignored) { }
        return uLoginUser;
    }

    private String getStringProp(JsonObject obj, String prop) {
        JsonString jsonString = obj.getJsonString(prop);
        if (jsonString == null) {
            return null;
        }
        return jsonString.getString();
    }
}

