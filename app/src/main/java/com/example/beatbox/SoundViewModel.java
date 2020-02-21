package com.example.beatbox;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewAnimationUtils;
import android.widget.Toast;


import com.example.beatbox.databinding.ListItemSoundBinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class SoundViewModel extends BaseObservable {
    private Sound mSound;
    private Bitmap mBackground;

    public void setBinding(ListItemSoundBinding binding) {
        mBinding = binding;
    }

    private BeatBox mBeatBox;
    private ListItemSoundBinding mBinding;
    public SoundViewModel(BeatBox beatBox){
        mBeatBox=beatBox;
    }


    public void onButtonClicked(){
        int cx=mBinding.playButton.getWidth()/2;
        int cy=mBinding.playButton.getHeight()/2;
        float radius=mBinding.playButton.getWidth();
        Animator animator= ViewAnimationUtils.createCircularReveal(mBinding.playButton,cx,cy,0,radius);
        animator.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBeatBox.play(mSound);
            }


        });
        animator.setDuration(200);
        animator.start();

    }

    public void setSound(Sound sound) {
        mSound = sound;
        notifyChange();
    }

    public Drawable getBackground() {
        return new BitmapDrawable(mBackground);
    }

    public void setBackground(Bitmap background) {
        mBackground = background;
    }

    @Bindable
    public String getTitle(){
        return mSound.getName();
    }
}
