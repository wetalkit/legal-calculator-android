package mk.wetalkit.legalcalculator.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import mk.wetalkit.legalcalculator.BuildConfig;

/**
 * Created by nikolaminoski on 10/2/17.
 */

public class ShareBitmapUtil {
    public static Uri getBitmapUri(Context context, Bitmap bitmap, Bitmap.CompressFormat imageType) throws IOException {
        File cachePath = new File(context.getCacheDir(), "images");
        cachePath.mkdirs();
        String filename = cachePath + "/" + UUID.randomUUID() + ".png";

        FileOutputStream stream = new FileOutputStream(filename);
        bitmap.compress(imageType, 100, stream);
        stream.close();

        File newFile = new File(filename);
        return FileProvider.getUriForFile(context,  BuildConfig.APPLICATION_ID + ".fileprovider", newFile);

    }
}
