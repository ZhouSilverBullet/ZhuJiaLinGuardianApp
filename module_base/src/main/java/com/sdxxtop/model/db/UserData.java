package com.sdxxtop.model.db;

import com.sdxxtop.app.Constants;
import com.sdxxtop.utils.SpUtil;

/**
 * todo
 */
public class UserData implements IUserData {

    public static UserData getInstance() {
        return SingleHolder.sUserSave;
    }


    public static class SingleHolder {
        private static UserData sUserSave = new UserData();
    }

    private UserData() {
    }

    @Override
    public void saveInfo(String autoToken, int expireTime, int partId,
                         int userid, String mobile, int type) {
        SpUtil.putString(Constants.AUTO_TOKEN, autoToken);
        SpUtil.putInt(Constants.EXPIRE_TIME, expireTime);
        SpUtil.putInt(Constants.PART_ID, partId);
        SpUtil.putInt(Constants.USER_ID, userid);
        SpUtil.putString(Constants.MOBILE, mobile);
        SpUtil.putInt(Constants.TYPE, type);
    }

    @Override
    public void removeAutoLoginInfo() {
        SpUtil.putString(Constants.AUTO_TOKEN, "");
        SpUtil.putInt(Constants.EXPIRE_TIME, 0);
        SpUtil.putInt(Constants.TYPE, 2);
    }

    public void logout() {
        removeAutoLoginInfo();
    }
}
