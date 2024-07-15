package com.formenshop.JWT;

import android.content.Context;
import android.content.SharedPreferences;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.formenshop.Config.TokenManager;


public class GetUserID {
    public static int getUserIdFromToken(Context context) {
        TokenManager tokenManager = new TokenManager(context);
        String token = tokenManager.getToken();

        if (token != null) {
            try {
                JWT jwt = new JWT(token);
                Claim userIdClaim = jwt.getClaim("UserId"); // Thay "userId" bằng key thực sự bạn sử dụng trong token
                return userIdClaim.asInt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }
}
