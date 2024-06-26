package com.liskovsoft.smartyoutubetv2.tv.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.VideoView;

import java.util.HashMap;

public class DisplayUtils {

    public interface MediaDimensions {
        double MEDIA_HEIGHT = 0.95;
        double MEDIA_WIDTH = 0.95;
        double MEDIA_TOP_MARGIN = 0.025;
        double MEDIA_RIGHT_MARGIN = 0.025;
        double MEDIA_BOTTOM_MARGIN = 0.025;
        double MEDIA_LEFT_MARGIN = 0.025;
    }
    
    private DisplayUtils() {
        // NOP
    }

    /**
     * Returns the screen/display size.
     */
    public static Point getDisplaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // You can get the height & width like such:
        // int width = size.x;
        // int height = size.y;
        return size;
    }

    public static int convertDpToPixel(Context ctx, int dp) {
        return convertDpToPixel(ctx.getResources(), dp);
    }

    public static int convertDpToPixel(Resources res, int dp) {
        float density = res.getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    /**
     * Example for handling resizing content for overscan.  Typically you won't need to resize when
     * using the Leanback support library.
     */
    public void overScan(Activity activity, VideoView videoView) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int w = (int) (metrics.widthPixels * MediaDimensions.MEDIA_WIDTH);
        int h = (int) (metrics.heightPixels * MediaDimensions.MEDIA_HEIGHT);
        int marginLeft = (int) (metrics.widthPixels * MediaDimensions.MEDIA_LEFT_MARGIN);
        int marginTop = (int) (metrics.heightPixels * MediaDimensions.MEDIA_TOP_MARGIN);
        int marginRight = (int) (metrics.widthPixels * MediaDimensions.MEDIA_RIGHT_MARGIN);
        int marginBottom = (int) (metrics.heightPixels * MediaDimensions.MEDIA_BOTTOM_MARGIN);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(w, h);
        lp.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        videoView.setLayoutParams(lp);
    }

    public static long getDuration(String videoUrl) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mmr.setDataSource(videoUrl, new HashMap<>());
        } else {
            mmr.setDataSource(videoUrl);
        }
        return Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }
}
