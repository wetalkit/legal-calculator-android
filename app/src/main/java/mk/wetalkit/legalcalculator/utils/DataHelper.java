package mk.wetalkit.legalcalculator.utils;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by nikolaminoski on 12/21/16.
 */

public class DataHelper {
    public static boolean store(Context context, Serializable data) {
        try {
            FileOutputStream fos = new FileOutputStream(getFile(context, data.getClass().getName()));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T> void load(final Context context, final Class<T> className, final OnDataLoadedListener<T> listener) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public T mData = null;

            @Override
            public void run() {
                try {
                    FileInputStream fis = new FileInputStream(getFile(context, className.getName()));
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    mData = (T) ois.readObject();
                } catch (Throwable e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (mData != null)
                                listener.onDataLoaded(mData);
                            else
                                listener.onDataLoadFailed();
                        } catch (Throwable t) {
                        }
                    }
                });
            }
        }).start();
    }

    public static <T> T load(Context context, Class<T> className) {
        try {
            FileInputStream fis = new FileInputStream(getFile(context, className.getName()));
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (T) ois.readObject();
        } catch (Exception e) {
        }
        return null;
    }

    public static <T> boolean has(Context context, Class<T> className) {
        File file = getFile(context, className.getName());
        return file.exists() && file.length() > 0;
    }

    public static <T> void delete(Context context, Class<T> className) {
        File file = getFile(context, className.getName());
        file.delete();
        return;
    }


    @NonNull
    public static File getFile(Context context, String name) {
        File dataDir = new File(context.getFilesDir(), "data");
        dataDir.mkdir();
        return new File(dataDir, name);
    }


    public static interface OnDataLoadedListener<T> {
        void onDataLoaded(T data);

        void onDataLoadFailed();
    }
}
