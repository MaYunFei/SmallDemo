package com.yunfei.gank.lib.utils.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import net.wequick.small.Small;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yunfei on 2017/1/10.
 * email mayunfei6@gmail.com
 * 统一 Activity 跳转
 */

public class Router {

    private int enterAnim = -1;
    private int exitAnim = -1;

    private Intent intent;

    private Bundle data;

    private ActivityOptionsCompat options;

    private int requestCode = -1;

    private Context from;

    private Router() {
    }

    public static Router from(Context from) {
        Router router = new Router();
        router.from = from;
        return router;
    }

    public Router to(Uri uri) {
        intent = Small.getIntentOfUri(uri, from);
        return this;
    }

    public Router to(String uri) {
        intent = Small.getIntentOfUri(uri, from);
        return this;
    }

    public Router addFlags(int flags) {
        if (intent != null) {
            intent.addFlags(flags);
        }
        return this;
    }

    public Router data(Bundle data) {
        this.data = data;
        return this;
    }

    public Router putByte(@Nullable String key, byte value) {
        getBundleData().putByte(key, value);
        return this;
    }

    public Router putChar(@Nullable String key, char value) {
        getBundleData().putChar(key, value);
        return this;
    }

    public Router putString(@Nullable String key, String value) {
        getBundleData().putString(key, value);
        return this;
    }

    public Router putShort(@Nullable String key, short value) {
        getBundleData().putShort(key, value);
        return this;
    }

    public Router putFloat(@Nullable String key, float value) {
        getBundleData().putFloat(key, value);
        return this;
    }

    public Router putCharSequence(@Nullable String key, @Nullable CharSequence value) {
        getBundleData().putCharSequence(key, value);
        return this;
    }

    public Router putParcelable(@Nullable String key, @Nullable Parcelable value) {
        getBundleData().putParcelable(key, value);
        return this;
    }

    public Router putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        getBundleData().putParcelableArray(key, value);
        return this;
    }

    public Router putParcelableArrayList(@Nullable String key,
                                         @Nullable ArrayList<? extends Parcelable> value) {
        getBundleData().putParcelableArrayList(key, value);
        return this;
    }


    public Router putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        getBundleData().putIntegerArrayList(key, value);
        return this;
    }

    public Router putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        getBundleData().putStringArrayList(key, value);
        return this;
    }

    public Router putCharSequenceArrayList(@Nullable String key,
                                           @Nullable ArrayList<CharSequence> value) {
        getBundleData().putCharSequenceArrayList(key, value);
        return this;
    }

    public Router putSerializable(@Nullable String key, @Nullable Serializable value) {
        getBundleData().putSerializable(key, value);
        return this;
    }


    public Router options(ActivityOptionsCompat options) {
        this.options = options;
        return this;
    }

    public Router requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public Router anim(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    public void launch() {
        if (intent != null && from != null) {

            intent.putExtras(getBundleData());

            if (options == null) {
                if (requestCode < 0) {
                    from.startActivity(intent);
                } else {
                    if (from instanceof Activity) {
                        ((Activity) from).startActivityForResult(intent, requestCode);
                    } else {
                        throw new IllegalArgumentException(" Router context must is Activity");
                    }
                }

                if (enterAnim > 0 && exitAnim > 0) {
                    if (from instanceof Activity) {
                        ((Activity) from).overridePendingTransition(enterAnim, exitAnim);
                    } else {
                        throw new IllegalArgumentException(" Router context must is Activity");
                    }
                }
            } else {
                if (requestCode < 0) {
                    ActivityCompat.startActivity(from, intent, options.toBundle());
                } else {
                    if (from instanceof Activity) {
                        ActivityCompat.startActivityForResult((Activity) from, intent, requestCode, options.toBundle());
                    } else {
                        throw new IllegalArgumentException(" Router context must is Activity");
                    }

                }
            }

        }
    }

    public static void pop(Activity activity) {
        activity.finish();
    }

    private Bundle getBundleData() {
        if (data == null) {
            data = new Bundle();
        }
        return data;
    }
}
