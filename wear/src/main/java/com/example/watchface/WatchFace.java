package com.example.watchface;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Calendar;

/**
 * Created by Namrata on 2015-07-31.
 */
public class WatchFace {
    // 1
    private static final String TIME_FORMAT = "%02d:%02d";
    private static final String DATE_FORMAT = "%02d.%02d.%d";
    private final Paint datePaint;
    private final Paint timePaint;
    private final int backgroundColor;

    // 2
    WatchFace(Paint timePaint, Paint datePaint, int backgroundColor) {
        this.timePaint = timePaint;
        this.datePaint = datePaint;
        this.backgroundColor = backgroundColor;
    }

    // 3
    public static WatchFace newInstance(Context context) {
        Resources resources = context.getResources();

        // 4
        Paint timePaint = new Paint();
        timePaint.setColor(resources.getColor(R.color.watch_face_time));
        timePaint.setTextSize(resources.getDimension(R.dimen.time_size));
        timePaint.setAntiAlias(true);

        Paint datePaint = new Paint();
        datePaint.setColor(resources.getColor(R.color.watch_face_date));
        datePaint.setTextSize(context.getResources().getDimension(R.dimen.date_size));
        datePaint.setAntiAlias(true);

        return new WatchFace(timePaint, datePaint, resources.getColor(R.color.watch_face_fill));
    }

    // 5
    public void draw(Canvas canvas, Rect bounds) {
// 1
        Calendar calendar = Calendar.getInstance();
// 2
        canvas.drawColor(backgroundColor);
// 3
        float centerX = bounds.exactCenterX();
        float centerY = bounds.exactCenterY();
        Rect boundingBox = new Rect();
// 4
        String timeText = String.format(TIME_FORMAT, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE));
        float timeCenterX = centerX - (timePaint.measureText(timeText) / 2.0f);
        timePaint.getTextBounds(timeText, 0, timeText.length(), boundingBox);
        float timeCenterY = centerY + (boundingBox.height() / 2.0f);
        canvas.drawText(timeText, timeCenterX, timeCenterY, timePaint);

        String dateText = String.format(DATE_FORMAT, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
        float dateCenterX = centerX - (datePaint.measureText(dateText) / 2.0f);
        float dateCenterY = timeCenterY - (boundingBox.height() + 10.0f);
        canvas.drawText(dateText, dateCenterX, dateCenterY, datePaint);
    }
}
