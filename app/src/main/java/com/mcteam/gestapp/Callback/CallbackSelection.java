package com.mcteam.gestapp.Callback;

import android.os.Parcelable;


import java.util.ArrayList;

/**
 * @author Created by Riccardo Rossi on 23/11/2016.
 */

public interface CallbackSelection <U extends Parcelable> {
    void onListLoaded(ArrayList<U> list);
}
