package com.mall.sls.common.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.luck.picture.lib.compress.Luban;
import com.mall.sls.certify.ui.MerchantCertifyActivity;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jwc on 2020/10/23.
 * 描述：
 */
public class CameraUnit {
    private static CameraUnit manager;
    private static Context appContext;
    private CompositeDisposable mDisposable;

    public CameraUnit(Context context) {
        appContext = context;
        mDisposable = new CompositeDisposable();
    }

    public static CameraUnit getInstance(Context context) {
        if (appContext == null || !appContext.equals(context) || manager == null) {
            appContext = context;
            manager = new CameraUnit(context);
        }
        return manager;
    }

    public File createTempFile(Context context,String filePath) {
        String timeStamp = new SimpleDateFormat("MMddHHmmss", Locale.CHINA).format(new Date());
        String externalStorageState = Environment.getExternalStorageState();
        File dir = new File(Environment.getExternalStorageDirectory() + filePath);
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return new File(dir, timeStamp + ".jpg");
        } else {
            File cacheDir = context.getCacheDir();
            return new File(cacheDir, timeStamp + ".jpg");
        }
    }

    private <T> void withRx(final List<T> photos) {
        mDisposable.add(Flowable.just(photos)
                .observeOn(Schedulers.io())
                .map(new Function<List<T>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<T> list) throws Exception {
                        return Luban.with(appContext)
                                .setTargetDir(getPath())
                                .load(list)
                                .get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                })
                .onErrorResumeNext(Flowable.<List<File>>empty())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> list) {
                        if(cameraUnitListener!=null){
                            cameraUnitListener.returnFiles(list);
                        }
                    }
                }));
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/hc/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    public interface CameraUnitListener {
        void returnFiles(List<File> list);
    }

    private CameraUnitListener cameraUnitListener;

    public void setOnItemClickListener(CameraUnitListener cameraUnitListener) {
        this.cameraUnitListener = cameraUnitListener;
    }


}
