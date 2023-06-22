/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * TicketNinja(in.ticketninja) <br />
 * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 15/12/15.
 *
 * @author Suthar Rohit
 */
public class Utility {

    //https://www.ticketninja.in:4430/BookingConfirmation/ADS8091133

    private static final String TAG = Utility.class.getSimpleName();
    public static final int REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 100;
    // public static final int REQUEST_PERMISSION_CALL_PHONE = 13;
    // public static final int REQUEST_PERMISSION_CALL_SMS = 14;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static final String CAMERA = "camera";
    public static final String GALLERY = "gallery";
    private static final String DATA = "data:";//for ex like data:image/jpeg;base64,
    //full ex : data:image/png;base64,/storage/emulated/0/DCIM/Screenshots/Screenshot_20190709-190748_Samsung Experience Home.jpg
    private static final String BASE64 = ";base64,";

    public static final String EVENT_DETAIL = "detail";

    public static String getImageUrlData(String imgType) {
        return DATA.concat(imgType).concat(BASE64);
    }

    private static String blockCharacterSet = "~:;^|$%&*!{}[]<>`=¥£¢€∆π®©√°¶™℅•÷×";
    public static InputFilter filter = (source, start, end, dest, dstart, dend) -> {
        StringBuilder sb = new StringBuilder(end - start);
        for (int i = start; i < end; i++) {
            int type = Character.getType(source.charAt(i));
            if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                sb.append("");
                //return "";
            } else if (Utility.blockCharacterSet.contains("" + source)) {
                sb.append("");
                //return "";
            } else {
                sb.append(source.charAt(i));
            }
        }
        return sb;
        //return null;
    };

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model) + " (OS: " + Build.VERSION.SDK_INT + ")";
        } else {
            return capitalize(manufacturer) + " - " + model + " (OS: " + Build.VERSION.SDK_INT + ")";
        }
    }

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDownloadRange(long downloadCount) {
        String result = downloadCount + "";
        if (downloadCount > 0 && downloadCount <= 50)
            result = "1-50";
        else if (downloadCount > 50 && downloadCount <= 100)
            result = "51-100";
        else if (downloadCount > 100 && downloadCount <= 500)
            result = "101-500";
        else if (downloadCount > 500 && downloadCount <= 1000)
            result = "501-1,000";
        else if (downloadCount > 1000 && downloadCount <= 5000)
            result = "1,001-5,000";
        else if (downloadCount > 5000 && downloadCount <= 10000)
            result = "5,001-10,000";
        else if (downloadCount > 10000 && downloadCount <= 50000)
            result = "10,001-50,000";
        else if (downloadCount > 50000 && downloadCount <= 100000)
            result = "50,001-1,00,000";
        else if (downloadCount > 100000)
            result = downloadCount + "";

        return result;
    }

    /**
     * This function check <u>Mobile Data</u> or <u>WiFi</u> is switched on or not..<br />
     * It will be return <b>true</b> when switched on and return <b>false</b> when switched off.<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param context {@link Context} of activity
     * @return true if <u>Mobile Data</u> or <u>WiFi</u> is switched on.
     */
    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo nInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
            return nInfo != null && nInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This function check {@link Package} is Installed or not on current device.<br />
     * It will be return <b>true</b> when package is installed
     * and
     * return <b>false</b> when package is not installed.<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param activity Current {@link Activity}
     * @param uri      Package event_name as {@link String}
     * @return true if package available.
     */
    public static boolean isInstalled(Activity activity, String uri) {
        PackageManager pm = activity.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    /***
     * This method is useful for convert pixel to DP dynamically as per device density.<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param context {@link Context}
     * @param size    in pixel
     * @return size in DP as per device density
     */
    public static int intToDP(Context context, int size) {
        try {
            float d = context.getResources().getDisplayMetrics().density;
            return (int) (size * d);
        } catch (Exception e) {
            return 0;
        }
    }


    /***
     * This function us use to get Height and Width in pixel.<br />
     * This method return array where <br />
     * int[0] is height <br />
     * int[1] is Width <br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param activity {@link Activity} for context
     * @return get device height and width as int array.
     */
    public static int[] getHeightWidth(Activity activity) {
        int[] param = new int[2];
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        param[0] = displaymetrics.heightPixels;
        param[1] = displaymetrics.widthPixels;
        return param;
    }

    private static int screenHeight = 0;
    private static int screenWidth = 0;

    public static void printFacebookHash(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", context.getPackageName() + "===>" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = Objects.requireNonNull(wm).getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = Objects.requireNonNull(wm).getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    /* -=-=-=-=-=-=-=-=-=-=-=- COLOR TINT -=-=-=-=-=-=-=-=-=-=-=- */
    public static Drawable setTint(@NonNull Context c, @NonNull Drawable d, @ColorRes int colorRes) {
        int color = ContextCompat.getColor(c, colorRes);
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    /**
     * Check Device is Tablet ?<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param context {@link Context} of activity
     * @return true if Current Device is Tablet.
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    // create image in rounded
    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
        Bitmap finalBitmap;
        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
                    false);
        else
            finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f,
                finalBitmap.getHeight() / 2 + 0.7f,
                finalBitmap.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);

        return output;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && ping("https://www.bing.com/");
    }

    private static boolean ping(final String u) {
        try {
            final boolean[] isPing = {false};
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(u);
                        HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                        urlc.setConnectTimeout(500); // event_time is in Milliseconds to wait for ping response
                        urlc.connect();
                        InputStreamReader in = new InputStreamReader((InputStream) urlc.getContent());
                        BufferedReader buff = new BufferedReader(in);
                        String line;
                        StringBuilder text = new StringBuilder();
                        do {
                            line = buff.readLine();
                            text.append(line).append("\n");
                        } while (line != null);
                        if (text.toString().length() < 400) {
                            isPing[0] = false;
                        }
                        isPing[0] = urlc.getResponseCode() == 200;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            th.start();
            th.join();
            return isPing[0];

        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (view == null) {
            view = new View(context);
        }
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (view == null) {
            view = new View(context);
        }
        Objects.requireNonNull(imm).showSoftInput(view, 0);
    }

    public static void openKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @TargetApi(Build.VERSION_CODES.Q)
    public static boolean isAppOpen(Context context) {
        try {
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);

            // get the info from the currently running task
            List<ActivityManager.RunningTaskInfo> taskInfo = Objects.requireNonNull(am).getRunningTasks(1);

            ComponentName componentInfo = taskInfo.get(0).topActivity;
            return componentInfo.getPackageName().equalsIgnoreCase("in.ticketninja.test");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    public static String fromHTML(String source) {
        if (Validate.isNotNull(source))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                return Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT).toString();
            else
                return Html.fromHtml(source).toString();
        else
            return source;
    }

    public static void setEditTextSingleLine(EditText editText) {
        editText.setSingleLine();
        editText.setMaxLines(Integer.MAX_VALUE);
        editText.setHorizontallyScrolling(false);
    }

    public static void callPhone(Context context, String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + number));
        context.startActivity(callIntent);
    }



    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    /**
     * Returns true if the string is null or 0-length.<br />
     * <br />
     * Developed by Shreya Prajapati
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isNull(String str) {
        return str == null || str.equalsIgnoreCase("null") || str.trim().length() == 0;
    }




    private static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            int permissionCAMERA = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);

            int storagePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

            List<String> listPermissionsNeeded = new ArrayList<>();
            if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, listPermissionsNeeded.get(0))) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");

                    alertBuilder.setPositiveButton(android.R.string.yes, (dialog, which) -> ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE));
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    //Convert pixel to dip
    public static int GetDipsFromPixel(Context context, float pixels) {
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    public static int getWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static String convertStringToBase64(String path) {
        Log.e(TAG, "path: " + path);
        byte[] data = path.getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.encodeToString(data, Base64.NO_WRAP);
        Log.e(TAG, "Base 64: " + base64);
        return base64;
    }

    public static String bitMapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    private static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (Objects.requireNonNull(uri.getScheme()).equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(Objects.requireNonNull(uri.getPath()))).toString());
        }

        return extension;
    }


    public static void convertToLowerCase(TextView v) {
        v.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    }

    public static void setTextViewDrawableColor(Context c, TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    drawable.setColorFilter(new PorterDuffColorFilter(c.getColor(color), PorterDuff.Mode.SRC_IN));
                }
            }
        }
    }

    public static void blinkBorder(View v) {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(500); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        v.startAnimation(animation); //to start animation
    }


    public static Drawable changeDrawableColor(Context context, int icon, int newColor) {
        // Read your drawable from somewhere
        Drawable dr = context.getResources().getDrawable(icon);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable mDrawable = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
        // Drawable mDrawable = Objects.requireNonNull(ContextCompat.getDrawable(context, icon)).mutate();
        mDrawable.setColorFilter(new PorterDuffColorFilter(newColor, PorterDuff.Mode.SRC_IN));

        //Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, icon);
        //Drawable mDrawable = DrawableCompat.wrap(Objects.requireNonNull(unwrappedDrawable));
        //DrawableCompat.setTint(mDrawable, newColor);
        return mDrawable;
    }
    public static String getYear(String s) {
        Date date;
        SimpleDateFormat inFormat = new SimpleDateFormat(DateTimeUtils.SERVER_FORMAT_DATE);
        try {
            date = inFormat.parse(s);
            Calendar cal = Calendar.getInstance();
            cal.setTime(Objects.requireNonNull(date));
            return "" + cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDayName(String s) {
        Date date;
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat(DateTimeUtils.SERVER_FORMAT_DATE);
            date = inFormat.parse(s);
            SimpleDateFormat outFormat = new SimpleDateFormat(DateTimeUtils.DAY_NAME);
            return outFormat.format(Objects.requireNonNull(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getDate(String s, String tos) {
        //Log.e(TAG,"s:"+s);
        //Log.e(TAG,"tos:"+tos);
        Date date, toDate;
        Calendar cal = Calendar.getInstance();
        Calendar toCal = Calendar.getInstance();
        SimpleDateFormat inFormat = new SimpleDateFormat(DateTimeUtils.SERVER_FORMAT_DATE);
        SimpleDateFormat outFormat = new SimpleDateFormat(DateTimeUtils.MONTH_NAME);
        try {
            date = inFormat.parse(s);
            cal.setTime(Objects.requireNonNull(date));
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String monthName = outFormat.format(cal.getTime());
            String strDate = monthName.concat(" ").concat("" + day);

            if (!TextUtils.isEmpty(tos) && !s.equals(tos)) {
                toDate = inFormat.parse(tos);
                toCal.setTime(Objects.requireNonNull(toDate));
                int toDay = toCal.get(Calendar.DAY_OF_MONTH);
                String toMonthName = outFormat.format(cal.getTime());
                String toStrDate = toMonthName.concat(" ").concat("" + toDay);
                return strDate.concat(" - ").concat(toStrDate);
            }
            return strDate;

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDate1(String s, String tos,String type) {
        //Log.e(TAG,"s:"+s);
        //Log.e(TAG,"tos:"+tos);
        Date date, toDate;
        Calendar cal = Calendar.getInstance();
        Calendar toCal = Calendar.getInstance();
        SimpleDateFormat inFormat = new SimpleDateFormat(DateTimeUtils.SERVER_FORMAT_DATE);
        SimpleDateFormat outFormat = new SimpleDateFormat(DateTimeUtils.MONTH_NAME);
        try {
            date = inFormat.parse(s);
            cal.setTime(Objects.requireNonNull(date));
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String monthName = outFormat.format(cal.getTime());
            String strDate = monthName.concat(" ").concat("" + day);

            if (!TextUtils.isEmpty(tos) && !s.equals(tos)) {
                toDate = inFormat.parse(tos);
                toCal.setTime(Objects.requireNonNull(toDate));
                int toDay = toCal.get(Calendar.DAY_OF_MONTH);
                String toMonthName = outFormat.format(toCal.getTime());
                String toStrDate = toMonthName.concat(" ").concat("" + toDay);
                if (EVENT_DETAIL.equals(type)){
                    return strDate.concat(" - ").concat(toStrDate).concat("\n").concat(getYear(tos));
                }else {
                    return strDate.concat(" - ").concat(toStrDate).concat(", ").concat(getYear(tos));
                }
            }else{
                return  strDate.concat(",").concat(getYear(s)).concat("\n").concat(getDayName(s));
            }
            // return strDate;

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
