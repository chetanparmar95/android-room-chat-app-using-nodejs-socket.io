package com.chetan.roomchat.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chetan.roomchat.app.R;

/**
 * Created by chetan on 21/01/17.
 */

public class ExitHolder extends RecyclerView.ViewHolder {
    TextView name;
    public ExitHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.tv_exit);
    }
}
