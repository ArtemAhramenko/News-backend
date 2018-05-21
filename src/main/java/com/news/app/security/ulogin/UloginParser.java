package com.news.app.security.ulogin;

import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.entity.dto.UserRegisterDto;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import java.net.URL;
import java.net.URLConnection;

@Component
public class UloginParser {

    private String host = "http://localhost:4200/";

    public LoginRequestDto getUser(String token) {

        LoginRequestDto uLoginUser = new LoginRequestDto();

        try {
            URL uloginUrl = new URL("http://ulogin.ru/token.php?token=" + token + "&host="+host);
            URLConnection urlConnection = uloginUrl.openConnection();

            JsonReader jsonReader = Json.createReader(urlConnection.getInputStream());
            JsonObject obj = jsonReader.readObject();

            if (obj == null ) {
                throw new BadCredentialsException("ulogin did't return json object");
            }

            if(obj.getJsonString("identity")==null){
                throw new BadCredentialsException("ulogin did't return identity object");
            }

            uLoginUser.setUsername(getStringProp(obj, "uid"));
            System.out.println("Q" + getStringProp(obj, "uid") + "Q");
            uLoginUser.setPassword(getStringProp(obj, "identity"));
            System.out.println("Q" + getStringProp(obj, "identity") + "Q");

            /*uLoginUser.setEnabled(true);*/

        } catch (Exception ex) {
            System.out.println(ex);
        }
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

