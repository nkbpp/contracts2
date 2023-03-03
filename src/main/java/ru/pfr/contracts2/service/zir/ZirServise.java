package ru.pfr.contracts2.service.zir;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.entity.Notification;
import ru.pfr.contracts2.entity.user.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZirServise {

    public Map<String, String> getFindAllOtdel() {
        String[][] S = GetSelect("select ID_POD, NAME_POD from podrazd");
        Map<String, String> map = new HashMap<>();
        for (String[] strings : S) {
            map.put(strings[0], strings[1]);
        }
        return map;
    }

    public List<User> getFindAllOtdelByIdAddPusto(Long id_zir) {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.addAll(getFindAllOtdelById(id_zir));
        return users;
    }

    public List<String> getAllIdUserByIdOtdel(String id_otdel) {
        String[][] S = GetSelect("SELECT ID_EM " +
                "FROM EMPLOYEES " +
                "WHERE ID_POD_EM = " + id_otdel);
        List<String> ids = new ArrayList<>();
        for (String[] strings : S) {
            ids.add(String.valueOf(strings[0]));
        }
        return ids;
    }


    public List<User> getFindAllOtdelById(Long id_zir) {
        String[][] S = GetSelect("SELECT e.ID_EM, " +
                "CONCAT(CONCAT(CONCAT(e.FAM_EM, CONCAT(' ', SUBSTR(e.NAM_EM, 1, 1))),'.'), CONCAT(SUBSTR(e.OTCH_EM, 1, 1),'.')) as NAME, " +
                "e.EMAIL_EM, e.FAM_EM, e.NAM_EM, e.OTCH_EM, p.ID_POD ID_POD,  p.NAME_POD NAME_POD " +
                "FROM DB2ADMIN.EMPLOYEES e, DB2ADMIN.PODRAZD p, POSTS po " +
                "WHERE e.ID_POD_EM = p.ID_POD " +
                "AND po.ID_POST = e.ID_POST_EM  AND e.ID_POD_EM = (SELECT ID_POD_EM FROM EMPLOYEES WHERE ID_EM=" + id_zir + ")");
        List<User> users = new ArrayList<>();
        for (String[] strings : S) {
            User user = new User();
            user.setLogin(strings[1]);
            user.setId_user_zir(Long.valueOf(strings[0]));
            users.add(user);
        }
        return users;
    }

    public String getEmailUserById(int id_zir) {
        String[][] S = GetSelect("SELECT e.ID_EM, " +
                "CONCAT(CONCAT(CONCAT(e.FAM_EM, CONCAT(' ', SUBSTR(e.NAM_EM, 1, 1))),'.'), CONCAT(SUBSTR(e.OTCH_EM, 1, 1),'.')) as NAME, " +
                "e.EMAIL_EM, e.FAM_EM, e.NAM_EM, e.OTCH_EM, p.ID_POD ID_POD,  p.NAME_POD NAME_POD " +
                "FROM DB2ADMIN.EMPLOYEES e, DB2ADMIN.PODRAZD p, POSTS po " +
                "WHERE e.ID_POD_EM = p.ID_POD " +
                "AND po.ID_POST = e.ID_POST_EM  AND e.ID_EM=" + id_zir + "");
        return S[0][2];
    }

    public String getNameUserById(int id_zir) {
        String[][] S = GetSelect("SELECT e.ID_EM, " +
                "CONCAT(CONCAT(CONCAT(e.FAM_EM, CONCAT(' ', SUBSTR(e.NAM_EM, 1, 1))),'.'), CONCAT(SUBSTR(e.OTCH_EM, 1, 1),'.')) as NAME, " +
                "e.EMAIL_EM, e.FAM_EM, e.NAM_EM, e.OTCH_EM, p.ID_POD ID_POD,  p.NAME_POD NAME_POD " +
                "FROM DB2ADMIN.EMPLOYEES e, DB2ADMIN.PODRAZD p, POSTS po " +
                "WHERE e.ID_POD_EM = p.ID_POD " +
                "AND po.ID_POST = e.ID_POST_EM  AND e.ID_EM=" + id_zir + "");
        return S[0][1];
    }

    public String getIdBossByIdUser(int id_zir) {
        String[][] S = GetSelect("SELECT e.ID_EM, " +
                "CONCAT(CONCAT(CONCAT(e.FAM_EM, CONCAT(' ', SUBSTR(e.NAM_EM, 1, 1))),'.'), CONCAT(SUBSTR(e.OTCH_EM, 1, 1),'.')) as NAME, " +
                "e.EMAIL_EM, e.FAM_EM, e.NAM_EM, e.OTCH_EM, p.ID_POD ID_POD,  p.NAME_POD NAME_POD " +
                "FROM DB2ADMIN.EMPLOYEES e, DB2ADMIN.PODRAZD p, POSTS po " +
                "WHERE e.ID_POD_EM = p.ID_POD " +
                "AND po.ID_POST = e.ID_POST_EM  AND e.ID_POD_EM = (SELECT ID_POD_EM FROM EMPLOYEES WHERE ID_EM=" + id_zir + ") AND po.head=1");
        return S[0][0];
    }

    public String getEmailBossById(int id_zir) {
        String[][] S = GetSelect("SELECT e.ID_EM, " +
                "CONCAT(CONCAT(CONCAT(e.FAM_EM, CONCAT(' ', SUBSTR(e.NAM_EM, 1, 1))),'.'), CONCAT(SUBSTR(e.OTCH_EM, 1, 1),'.')) as NAME, " +
                "e.EMAIL_EM, e.FAM_EM, e.NAM_EM, e.OTCH_EM, p.ID_POD ID_POD,  p.NAME_POD NAME_POD " +
                "FROM DB2ADMIN.EMPLOYEES e, DB2ADMIN.PODRAZD p, POSTS po " +
                "WHERE e.ID_POD_EM = p.ID_POD " +
                "AND po.ID_POST = e.ID_POST_EM  AND e.ID_POD_EM = (SELECT ID_POD_EM FROM EMPLOYEES WHERE ID_EM=" + id_zir + ") AND po.head=1");
        return S[0][2];
    }

    public String getNameBossById(int id_zir) {
        String[][] S;

        S = GetSelect("SELECT e.ID_EM, " +
                "CONCAT(CONCAT(CONCAT(e.FAM_EM, CONCAT(' ', SUBSTR(e.NAM_EM, 1, 1))),'.'), CONCAT(SUBSTR(e.OTCH_EM, 1, 1),'.')) as NAME, " +
                "e.EMAIL_EM, e.FAM_EM, e.NAM_EM, e.OTCH_EM, p.ID_POD ID_POD,  p.NAME_POD NAME_POD " +
                "FROM DB2ADMIN.EMPLOYEES e, DB2ADMIN.PODRAZD p, POSTS po " +
                "WHERE e.ID_POD_EM = p.ID_POD " +
                "AND po.ID_POST = e.ID_POST_EM  " +
                "AND e.ID_POD_EM = (SELECT ID_POD_EM FROM EMPLOYEES WHERE ID_EM=" + id_zir + ") " +
                "AND po.head=1");

        return S[0][1] + " (Начальник отдела)";
    }

    private String readToString(InputStream is, String charset) throws IOException {
        InputStreamReader reader = new InputStreamReader(is, charset);
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[1024];
        int len;
        while ((len = reader.read(buffer)) > 0) {
            sb.append(buffer, 0, len);
        }
        return sb.toString();
    }

    private String[][] GetSelect(String sql) {
        int TIMEAUT_SEL = 1000;
        String SEL = "http://10.41.0.247:322/ACS/GetSelect";

        String[][] ret = new String[0][0];
        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        try {
            HttpUriRequest login;
            CloseableHttpResponse response2 = null;
            try {
                RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
                        .setSocketTimeout(TIMEAUT_SEL)
                        .setConnectTimeout(TIMEAUT_SEL)
                        .setConnectionRequestTimeout(TIMEAUT_SEL)
                        .build();
                login = RequestBuilder.post().setUri(new URI(SEL))
                        .addParameter("sql", sql)
                        .setConfig(requestConfig)
                        .build();
                response2 = httpclient.execute(login);
            } catch (Exception e) {
                System.out.println("Error GetSelect try1" + e);
            }
            try {
                HttpEntity entity = response2.getEntity();

                String S = readToString(entity.getContent(), "UTF8");
                Object O = new JSONObject(S).get("select");
                JSONArray data = (JSONArray) O;
                if (data.length() == 1) {
                    JSONObject jsonObject = new JSONObject((String) data.get(0));
                    String count = (String) jsonObject.get("count");
                    if (Integer.parseInt(count) > 0) {
                        JSONArray M = (JSONArray) jsonObject.get("rez");
                        ret = new String[M.length()][];
                        JSONArray L;
                        for (int i = 0; i < ret.length; i++) {
                            L = (JSONArray) M.get(i);
                            ret[i] = new String[L.length()];
                            for (int j = 0; j < ret[i].length; j++) {
                                ret[i][j] = (String) L.get(j);
                            }
                        }
                    }
                }
                EntityUtils.consume(entity);
            } catch (Exception e) {
                System.out.println("Error GetSelect try2" + e);
            } finally {
                try {
                    response2.close();
                } catch (Exception e) {
                    System.out.println("Error GetSelect try3" + e);
                }
            }
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                System.out.println("Error GetSelect try4" + e);
            }
        }
        return ret;
    }

    public List<Notification> getNotification(String... idZir) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification(0L, ""));
        for (var s :
                idZir) {
            for (String n :
                    getAllIdUserByIdOtdel(s)) {
                if (!n.equals("undefined") && !n.equals("")) {
                    notifications.add(new Notification(Long.valueOf(n), getNameUserById(Integer.parseInt(n))));
                }
            }
        }

        return notifications;
    }

}
