package com.asif.urbandictionary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.asif.urbandictionary.R;
import com.asif.urbandictionary.module.DictResponse;
import com.asif.urbandictionary.utils.CommonUtils;
import com.asif.urbandictionary.viewModel.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DictionaryAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = DictionaryAdapter.class.getSimpleName();
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private List<DictResponse> dResponseList;

    public DictionaryAdapter(List<DictResponse> dictList) {
        dResponseList = dictList;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dResponseList != null && dResponseList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (dResponseList != null && dResponseList.size() > 0) {
            return dResponseList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<DictResponse> dictList) {
        dResponseList.addAll(dictList);
        notifyDataSetChanged();
    }

    public void refreshItems(List<DictResponse> dictList) {
        dResponseList = null;
        dResponseList = new ArrayList<DictResponse>();
        dResponseList.addAll(dictList);
        notifyDataSetChanged();
    }


    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.thumbDownImage)
        ImageView thumbDownImage;

        @BindView(R.id.thumbDownCount)
        TextView thumbDownCount;

        @BindView(R.id.thumbUpImage)
        ImageView thumbUpImage;

        @BindView(R.id.thumbUpCount)
        TextView thumbUpCount;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            thumbDownImage.setImageDrawable(null);
            thumbDownCount.setText("");
            thumbUpImage.setImageDrawable(null);
            thumbUpCount.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final DictResponse dicResp = dResponseList.get(position);

            thumbUpImage.setImageResource(R.drawable.thumbup);
            thumbUpCount.setText(String.valueOf(dicResp.thumbs_up));
            thumbDownImage.setImageResource(R.drawable.thumbdown);
            thumbDownCount.setText(String.valueOf(dicResp.thumbs_down));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonUtils.hideKeyBaord(view, view.getContext());
                }
            });
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_message)
        TextView messageTextView;

        EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void onBind(int position) {
            super.onBind(position);
//            messageTextView.setText(itemView.getContext().getApplicationContext().getResources().getString(R.string.empty_screen));
        }

        @Override
        protected void clear() {
            messageTextView.setText("");
        }

    }
}