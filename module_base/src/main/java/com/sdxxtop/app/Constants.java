package com.sdxxtop.app;

import java.io.File;

public interface Constants {
    String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    String PATH_CACHE = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "NetCache";
    String PATH_IMG = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "img";

    public static String COMPANY_JIN_WEIDU = "company_jin_weidu";

    String AUTO_TOKEN = "auto_token";
    String EXPIRE_TIME = "expire_time";
    String PART_ID = "part_id";
    String USER_ID = "user_id";
    String MOBILE = "mobile";
    String TYPE = "login_type";
    String GUIDE_IS_SHOW = "guide_is_show";
    String GUIDE_SHOW_VERSION = "guide_show_version";

    /**
     *
     * 我认为图片地址不应该存本地
     */
    String USER_IMG = "user_img";

    String WX_APP_ID = "wx305d24f7bf756b80";
}
