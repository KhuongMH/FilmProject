package vn.app.phims14.UIComponent;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import vn.app.phims14.R;

/**
 * Created by Minh on 3/28/2016.
 */
public class AvatarImage extends ImageView {

    private static final String TAG = AvatarImage.class.getName();

    private ImageView imageView;

    //private static final int DEFAULT_STROKE_COLOR = Color.BLUE;
    private static final int DEFAULT_STROKE_WIDTH = 0;
    private static final int DEFAULT_MODE = 0;
    private static final int DEFAULT_BORDER_MODE = 0;
    private static final int DEFAULT_BORDER = 0;

    private int stroke_width = 0;
    private ColorStateList stroke_color =
            ColorStateList.valueOf(Color.RED);
    private int isBorder = 0;
    private int isBorderType = 0;
    private int isAvatarType = 0;

    private Paint mBorderPaint;

    public AvatarImage(Context context) {
        super(context);
    }

    public AvatarImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.AvatarImage, defStyleAttr, 0);

        try {
            stroke_width = attributes.getDimensionPixelSize(R.styleable.AvatarImage_stroke_width, DEFAULT_STROKE_WIDTH);
            stroke_color = attributes.getColorStateList(R.styleable.AvatarImage_stroke_color);
            isBorder = attributes.getInt(R.styleable.AvatarImage_avatar_border, DEFAULT_BORDER);
            isBorderType = attributes.getInt(R.styleable.AvatarImage_avatar_border_style, DEFAULT_BORDER_MODE);
            isAvatarType = attributes.getInt(R.styleable.AvatarImage_avatar_mode, DEFAULT_MODE);

            initBorderPaint(stroke_width, stroke_color);
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AvatarImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initBorderPaint(float stroke_width, ColorStateList stroke_color) {
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(stroke_color.getDefaultColor());
        mBorderPaint.setStrokeWidth(stroke_width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        imageView = this;
        if (isBorder == 1) {
            if (isAvatarType == 1) {
                if (isBorderType == 0) {
                    canvas.drawRect(stroke_width / 2, stroke_width / 2, getWidth() - stroke_width / 2, getHeight() - stroke_width / 2, mBorderPaint);
                } else {
                    canvas.drawRect(-stroke_width / 2, -stroke_width / 2, getWidth() + stroke_width / 2, getHeight() + stroke_width / 2, mBorderPaint);
                }
            } else {
                drawCircle(getHeight() / 2);
                canvas.drawCircle(getWidth() / 2, getWidth() / 2, (float) getWidth() / 2 - stroke_width / 2, mBorderPaint);
            }
        } else {

        }

    }

    private void drawCircle(final int radius) {
        Picasso.with(getContext()).load(R.drawable.avatar)
                .transform(new CircleTransform()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Bitmap bitmap = getRoundedCroppedImage(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                imageView.setImageDrawable(drawable);
            }

            private Bitmap getRoundedCroppedImage(Bitmap bmp) {
                int widthLight = bmp.getWidth();
                int heightLight = bmp.getHeight();



                int round = widthLight < heightLight ? widthLight : heightLight;
                int mid=(int)Math.sqrt((double)(Math.pow(widthLight,2)+Math.pow(heightLight,2)))/2;

                Log.d(TAG,String.valueOf(radius)+" - "+String.valueOf(widthLight)+" - "+String.valueOf(heightLight)+" - "+String.valueOf(round)+" - ");

                Bitmap output = Bitmap.createBitmap(round, round, Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(output);
                Paint paint = new Paint();
                paint.setFlags(Paint.ANTI_ALIAS_FLAG);

                RectF rectF = new RectF(new Rect(0, 0, round, round));

                canvas.drawCircle(radius , radius , (float) radius / 2 - stroke_width / 2, paint);
                //canvas.drawRoundRect(rectF, widthLight / 2, widthLight / 2, paint);

                Paint paintImage = new Paint();
                paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                canvas.drawBitmap(bmp, 0, 0, paintImage);

                return output;
            }
        });
    }

    private class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            if(x>y){
                y=x;
            }else{
                x=y;
            }

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(stroke_color.getDefaultColor());
            paint.setStrokeWidth(stroke_width);
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
