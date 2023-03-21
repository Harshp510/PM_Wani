package com.zenwsmp.pmwani;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;


/**
 * Created by dodo on 15/12/15.
 */
public class ConfigAPI {
    // obtain your licence key at http://microblink.com/login or
    // contact us at http://help.microblink.com
    // public static final String LICENSE_KEY = "MSHLU4AX-CBMEPGOI-RN7IJWQZ-UEOLNT4Y-GD4K5X42-OJL66HTK-CYGA2P3E-O4CIHSF2";
    public static final String LICENSE_KEY = "75IYFNSG-ZYFURZAG-A6CX6OWR-IJ63EFRE-FDJK76ZO-GDF33L73-FYYMWHLM-RPBDJTOI";
    public static  String MAIN_URL= "http://192.168.7.43:8080/api/v1/";
    // public static final String MAIN_URL="https://developer.qntmnet.com/webservice/";
    public static final String Developer="de^el0per";
    public static final String flag_checkssid="SSIDCheck";
    public static final String flag_val_checkssid="0";
    //  flag=SSIDCheck&flag_val=0

    public void ShowToastMessage(Context context, String message)
    {
     /*   View toastLayout = LayoutInflater.from(context).inflate(R.layout.custome_toast, ((Activity) context).findViewById(R.id.custom_toast_layout));
        TextView toast_message= toastLayout.findViewById(R.id.toast_message);
        toast_message.setText(message);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);

        toast.setView(toastLayout);
        toast.show();*/
       /* DynamicToast.make(context, message,
                Color.parseColor("#FFFFFF"), Color.parseColor("#EC6D2D"),
                Toast.LENGTH_SHORT).show();*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            // Do something for lollipop and above versions
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        } else{
            // do something for phones running an SDK before lollipop

            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }

    }


}
