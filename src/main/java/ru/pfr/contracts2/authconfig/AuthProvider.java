package ru.pfr.contracts2.authconfig;

import jodd.http.HttpUtil;
import jodd.http.HttpValuesMap;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.admin.Adminparam;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.ROLE_ENUM;
import ru.pfr.contracts2.entity.user.Rayon;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.admin.AdminparamService;
import ru.pfr.contracts2.service.log.LogiService;
import ru.pfr.contracts2.service.user.RayonService;
import ru.pfr.contracts2.service.user.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final RayonService rayonService;

    private final AdminparamService adminparamService;

    private final UserService userService;

    private final LogiService logiService;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        SecurityContext context = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) a;
        String username = String.valueOf(auth.getPrincipal());
        String password = String.valueOf(auth.getCredentials());
        Map<String, String> parameterList = new HashMap<>();
        parameterList.put("adr", "http://127.0.0.0/Autent");
        parameterList.put("kod", "171"); //код приложения в ЗИРЕ 161
        parameterList.put("login", username);
        logiService.save(new Logi(username,
                "Попытка авторизации по логину " + username + " AuthProvider authenticate()"));
        parameterList.put("pass", password);
        CloseableHttpResponse httpResponse = getHTTPResponse("http://10.41.0.247:322/ACS/AutentAll",
                parameterList);
        Header[] headers = httpResponse.getHeaders("Location");

        User logerr;
        try {
            logerr = userService.findByLoginuser(username); //пользователь существует

            if (logerr == null) {
                Rayon r = rayonService.findByKod("999"); //пользователя небыло
                logerr = new User(username, r);
            }
            //для количества попыток-----------------------------
            Adminparam adminparam = adminparamService.findByAdminparam();

            long datenow = new Date().getTime() + 10800000L; //избавление от погрешности во времени

            if (logerr.getActive() >= adminparam.getKolpopitok() &&
                    logerr.getActive() < adminparam.getBlock() &&
                    (datenow - logerr.getDate_last_entry().getTime()) <=
                            (600000L + ((adminparam.getKolpopitok() - 2) * adminparam.getKoefpopitok() * 60000L))) {
                logerr.setActive(logerr.getActive() + 1);
                logerr.setDate_last_entry(new Date(datenow));
                userService.save(logerr);
                logiService.save(new Logi(username,
                        "Попытка авторизации превышен лимит попыток. Количество попыток"
                                + logerr.getActive() + " AuthProvider authenticate()"));
                throw new BadCredentialsException("Превышен лимит попыток");
            }
            if (logerr.getActive() >= adminparam.getBlock()) {
                logiService.save(new Logi(username,
                        "Попытка авторизации пользователь заблокирован. Количество попыток"
                                + logerr.getActive() + " AuthProvider authenticate()"));
                throw new BadCredentialsException("Пользователь заблокирован");
            }

            if (headers.length == 0) {
                logerr.setActive(logerr.getActive() + 1);
                logerr.setDate_last_entry(new Date(datenow));
                userService.save(logerr);
                logiService.save(new Logi(
                        username,
                        "Попытка авторизации пароль неверен! Зир не вернул результата! AuthProvider authenticate()"));
                throw new BadCredentialsException("Пароль неверен");
            } else {
                logerr.setActive(0L);
                logerr.setDate_last_entry(new Date(datenow));
                userService.save(logerr);

                String response = headers[0].getValue();
                Matcher authQueryString = Pattern
                        .compile("^http://127\\.0\\.0\\.0/Autent\\?([^\\r\\n]++)$")
                        .matcher(response);
                if (!authQueryString.find()) {
                    throw new BadCredentialsException("Пароль неверен");
                }
                HttpValuesMap<Object> authData = HttpUtil.parseQuery(authQueryString.group(1), true);
                Collection<GrantedAuthority> roleList = new HashSet<>();
                Object[] rights = authData.get("right");
                String userId = (String) authData.get("idzir")[0];
                StringBuilder upfrCode = new StringBuilder((String) authData.get("upfr")[0]);
                String idpod = (String) authData.get("idpod")[0];
                /*byte[] decodedBytes = Base64.getUrlDecoder().decode((String) authData.get("namepod")[0]);
                String namepod = new String(decodedBytes);*/

                /*byte[] decoded = DatatypeConverter.parseBase64Binary((String) authData.get("namepod")[0]);
                String namepod = new String(decoded, StandardCharsets.UTF_8);*/

                String namepod = URLDecoder.decode((String) authData.get("namepod")[0], StandardCharsets.UTF_8);

/*              String ss2 = "";
                try{
                    ss2 = URLEncoder.encode("Отдел эксплуатации и сопровождения","UTF-8");
                }catch (Exception e){}

                String ss = "";
                try{
                    ss = URLDecoder.decode(ss2,"UTF-8");
                }catch (Exception e){}*/

                String email = (String) authData.get("email")[0];

                for (Object right : rights) {
                    int rightCode = Integer.parseInt((String) right);
                    switch (rightCode) {
                        case 14 -> roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_UPDATE.getString()));
                        case 1 -> {
                            roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_ADMIN.getString()));
                            upfrCode = new StringBuilder("999");
                        }
                        //TODO оригинал
                        case 100 -> roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_UPDATE_IT.getString()));
                        case 101 -> roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_READ_IT.getString()));
                        case 102 -> roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_UPDATE_AXO.getString()));
                        case 103 -> roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_READ_AXO.getString()));

                        /*case 100 -> roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_UPDATE_AXO.getString()));
                        case 101 -> roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_READ_AXO.getString()));*/
                    }
                }

                //roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_ADMIN.getString()));//TODO УБРАТЬ
                //roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_UPDATE_AXO.getString()));
                //roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_READ_AXO.getString()));

                while (upfrCode.length() < 3) {
                    upfrCode.insert(0, "0");
                }

                User user = userService.findByLoginuser(username);

                user.setActive(1L);

                user.setId_user_zir(Long.parseLong(userId));
                user.setId_ondel_zir(Long.parseLong(idpod));
                user.setName_ondel_zir(namepod);
                user.setEmail(email);
                user.setRayon(rayonService.findByKod(upfrCode.toString()));
                userService.save(user);

                a = new UsernamePasswordAuthenticationToken(user, "", roleList);
                context.setAuthentication(a);
                logiService.save(new Logi(
                        user.getLogin(),
                        "Пользователь " + user.getLogin() + " авторизован  AuthProvider authenticate()"));
            }
        } catch (Exception e) {
            System.out.println("MES=" + e.getMessage());
            throw new BadCredentialsException(e.getMessage());
        }


        return a;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }


    public CloseableHttpResponse getHTTPResponse(String addr, Map<String, String> parameterList) {
        try {
            BasicCookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpUriRequest req;
            CloseableHttpResponse response;
            RequestConfig requestConfig = RequestConfig
                    .copy(RequestConfig.DEFAULT)
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .build();
            RequestBuilder reqBuilder = RequestBuilder.post().setUri(new URI(addr));
            for (String key : parameterList.keySet()) {
                reqBuilder.addParameter(key, parameterList.get(key));
            }
            req = reqBuilder.setConfig(requestConfig).build();
            response = httpclient.execute(req);
            return response;
        } catch (URISyntaxException | IOException ex) {
            return null;
        }
    }
}
