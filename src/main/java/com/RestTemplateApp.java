package com;

import com.entities.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class RestTemplateApp {
    private static final String GET_USERS_ENDPOINT_URL = "http://91.241.64.178:7081/api/users";
    private static final String GET_USER_ENDPOINT_URL = "http://91.241.64.178:7081/api/users/3";
    private static RestTemplate restTemplate = new RestTemplate();
    String set_cookie = "";
    public String resultCode = "";

    public static void main(String[] args) {
        RestTemplateApp restTemplateApp = new RestTemplateApp();

        restTemplateApp.getUsers();
        restTemplateApp.createUser();
        restTemplateApp.updateUser();
        restTemplateApp.deleteUser();
        System.out.println("Итоговый код: " + restTemplateApp.resultCode);


    }

    private void getUsers() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(GET_USERS_ENDPOINT_URL, HttpMethod.GET, entity,
                String.class);
        set_cookie = result.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        System.out.println(set_cookie);
    }

    private void createUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", set_cookie);

        User user = new User(3L, " James", "Brown", (byte) 33);
        HttpEntity<User> entity = new HttpEntity<User>(user, headers);

        ResponseEntity response = restTemplate.exchange(
                GET_USERS_ENDPOINT_URL, HttpMethod.POST, entity, String.class);
        resultCode += (String) response.getBody();
    }

    private void updateUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", set_cookie);

        User user = new User(3L, " Thomas", "Shelby", (byte) 33);
        Map<String, String> param = new HashMap<String, String>();
        param.put("id", "3");

        HttpEntity<User> requestEntity = new HttpEntity<User>(user, headers);
        ResponseEntity response = restTemplate.exchange(
                GET_USERS_ENDPOINT_URL, HttpMethod.PUT, requestEntity, String.class, param);
        resultCode += (String) response.getBody();
    }

    public void deleteUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", set_cookie);

        HttpEntity<User> entity = new HttpEntity<User>(headers);

        ResponseEntity response = restTemplate.exchange(
                GET_USER_ENDPOINT_URL, HttpMethod.DELETE, entity, String.class);
        resultCode += (String) response.getBody();
    }
}
