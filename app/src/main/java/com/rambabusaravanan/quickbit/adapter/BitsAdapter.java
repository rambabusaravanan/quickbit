package com.rambabusaravanan.quickbit.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rambabusaravanan.quickbit.R;
import com.rambabusaravanan.quickbit.databinding.ItemBitBinding;
import com.rambabusaravanan.quickbit.model.Bit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andro on 10/7/17.
 */

public class BitsAdapter extends RecyclerView.Adapter<BitsAdapter.BindingHolder> {

    private List<Bit> dataSet;

    public BitsAdapter() {
        this.dataSet = new ArrayList<>();
    }

    public BitsAdapter(List<Bit> dataSet) {
        this.dataSet = dataSet;
    }

    public void update(List<Bit> list, boolean clear) {
        if (clear) dataSet.clear();
        dataSet.addAll(list);
        notifyDataSetChanged();
    }

    public void update(Bit bit) {
        dataSet.add(bit);
        notifyDataSetChanged();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemBitBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_bit, parent, false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        Bit bit = dataSet.get(position);
        holder.bind(bit);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class BindingHolder extends RecyclerView.ViewHolder {

        private final ItemBitBinding binding;

        BindingHolder(ItemBitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Bit bit) {
            if (!TextUtils.isEmpty(bit.icon))
                Picasso.with(binding.getRoot().getContext()).load(bit.icon).into(binding.image);

            binding.setBit(bit);
            binding.executePendingBindings();
        }
    }
}
