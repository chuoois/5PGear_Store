package config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import model.GoogleAccount;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

public class GoogleLogin {

    public static final String GOOGLE_CLIENT_ID = "747621299770-5o32uddebj2520537cpb9aplufl15q1s.apps.googleusercontent.com";
    public static final String GOOGLE_CLIENT_SECRET = "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";
    public static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/SWP391Gr5/login";
    public static final String GOOGLE_GRANT_TYPE = "authorization_code";
    public static final String GOOGLE_LINK_GET_TOKEN = "https://oauth2.googleapis.com/token";
    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=";

    public static String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", GOOGLE_CLIENT_ID)
                                .add("client_secret", GOOGLE_CLIENT_SECRET)
                                .add("redirect_uri", GOOGLE_REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", GOOGLE_GRANT_TYPE)
                                .build()
                )
                .execute().returnContent().asString();

        JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class);
        return jsonResponse.get("access_token").getAsString();
    }

    public GoogleAccount getUserInfo(final String accessToken) throws ClientProtocolException, IOException {

        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;

        String response = Request.Get(link).execute().returnContent().asString();
        
        System.out.println("Google Response: " + response); 
        
        GoogleAccount googlePojo = new Gson().fromJson(response, GoogleAccount.class);

        return googlePojo;

    }
}
