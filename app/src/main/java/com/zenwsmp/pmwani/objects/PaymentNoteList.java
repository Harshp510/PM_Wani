/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.objects;

import java.io.Serializable;

/**
 * Created by Rohit on 14-07-2017.
 */

public class PaymentNoteList implements Serializable {

    private static final long serialVersionUID = 6663408501416574227L;
    private String note;

    @Override
    public String toString() {
        return note + "";
    }
}
