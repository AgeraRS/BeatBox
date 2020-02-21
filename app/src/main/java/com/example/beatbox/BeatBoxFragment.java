package com.example.beatbox;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beatbox.databinding.FragmentBeatBoxBinding;
import com.example.beatbox.databinding.ListItemSoundBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BeatBoxFragment extends Fragment {
    private BeatBox mBeatBox;
    private AssetManager mAssets;
    public static BeatBoxFragment newInstance(){
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBeatBox=new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding= androidx.databinding.DataBindingUtil.inflate(inflater,R.layout
        .fragment_beat_box,container,false);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        binding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        return binding.getRoot();
    }



    private class SoundHolder extends RecyclerView.ViewHolder{

        private ListItemSoundBinding mBinding;
        private SoundHolder(ListItemSoundBinding binding) {
                super(binding.getRoot());
            mBinding=binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        public void bind(Sound sound){
            mBinding.getViewModel().setBinding(mBinding);
            mBinding.getViewModel().setSound(sound);
          try {
                mAssets=getContext().getAssets();
                InputStream is=mAssets.open(sound.getBackgroundAssetPath());
                Bitmap bitmap= BitmapFactory.decodeStream(is);
                mBinding.getViewModel().setBackground(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
            mBinding.executePendingBindings();
        }


    }
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{
        private List<Sound>mSounds;
        public SoundAdapter(List<Sound>sounds){
            this.mSounds=sounds;
        }
        @NonNull
        @Override
        public SoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            ListItemSoundBinding binding= DataBindingUtil.inflate(inflater,R.layout.list_item_sound,parent,false);

            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundHolder holder, int position) {
            Sound sound=mSounds.get(position);
            holder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mBeatBox.release();
    }
}
