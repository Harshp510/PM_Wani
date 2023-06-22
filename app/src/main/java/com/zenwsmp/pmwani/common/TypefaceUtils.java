/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.common;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import com.zenwsmp.pmwani.R;


/**
 * TicketNinja(in.ticketninja) <br />
 * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 15/12/15.
 *
 * @author Suthar Rohit
 */
public class TypefaceUtils {

    public static Typeface HelveticaRegular(Context context) {
        return ResourcesCompat.getFont(context, R.font.helvetica_neue_regular);
        //return Typeface.createFromAsset(context.getAssets(), "Fonts/HelveticaNeue.ttf");
    }

    public static Typeface HelveticaMedium(Context context) {
        return ResourcesCompat.getFont(context, R.font.helvetica_neue_medium);
        //return Typeface.createFromAsset(context.getAssets(), "Fonts/HelveticaNeueMedium.ttf");
    }

}
