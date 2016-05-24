package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Associazioni;

import android.content.ContentProvider;
import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.manuelpeinado.multichoiceadapter.MultiChoiceAdapter;
import com.manuelpeinado.multichoiceadapter.MultiChoiceArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;

/**
 * Created by meddaakouri on 02/02/2016.
 */
public class MultiChoiceListAdapter extends MultiChoiceArrayAdapter<UserInfo> {
    Context mContext = null;
    ArrayList<UserInfo> mConsulentiList;

    public MultiChoiceListAdapter(Bundle savedInstanceState, Context context, int textViewResourceId, ArrayList<UserInfo> objects) {
        super(savedInstanceState, context, textViewResourceId, objects);
        mContext = context;
        mConsulentiList = objects;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    protected View getViewImpl(int position, View convertView, ViewGroup parent) {
        return super.getViewImpl(position, convertView, parent);
    }
}
