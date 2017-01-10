package com.yunfei.gank.lib.utils.router;

import android.content.Context;

/**
 * Created by yunfei on 2017/1/10.
 * email mayunfei6@gmail.com
 */

public class RouterUtil {


    //禁止实例化
    private RouterUtil() {
    }

    /**
     * 打开 SubDetail
     * @param
     */
    public static void StartSubDetail(Context context) {
        Router.from(context)
                .to("detail/subdetail")
                .putString(RoutrExtraKey.EXTRA_STRING_FROM, "from RouterUtil")
                .launch();
    }
}
