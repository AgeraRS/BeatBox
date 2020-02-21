package com.example.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {
    private static final String TAG="BeatBox";
    private static final String SOUNDS_FOLDER="sample_sounds";
    private static final String BACKGROUNDS_FOLDER="sample_backgrounds";
    private static final int MAX_SOUNDS=5;
    private AssetManager mAssets;
    private List<Sound>mSounds=new ArrayList<>();
    private SoundPool mSoundPool;
    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public BeatBox(Context context){
        this.mAssets=context.getAssets();
        mContext=context;
        mSoundPool=new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC,0);
        loadSounds();
        loadBackgrounds();
    }

    public void play(Sound sound){
        Integer soundId=sound.getSoundId();
        if (soundId==null){
            return;
        }
        mSoundPool.play(soundId,1.0f,1.0f,1,0,1.0f);
    }

    private void loadSounds(){
        String[]soundNames;
        try {
            soundNames=mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG,"Found "+soundNames.length+" sounds");
        }catch (IOException ioe){
            Log.e(TAG,"Could not list assets",ioe);
            return;
        }
        for (String fileName:soundNames){
            try {
                String assetPath=SOUNDS_FOLDER+"/"+fileName;
                Sound sound=new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            }catch (IOException ioe){
                Log.e(TAG,"Could not load sound"+fileName,ioe);
            }

        }
    }
    private void load(Sound sound)throws IOException{
        AssetFileDescriptor afd=mAssets.openFd(sound.getAssetPath());
        int soundId=mSoundPool.load(afd,1);
        sound.setSoundId(soundId);
    }
    private void loadBackgrounds(){
        try {
            String[]pictureNames=mAssets.list(BACKGROUNDS_FOLDER);
            for (int i=0;i<pictureNames.length;i++){
                String assetPath=BACKGROUNDS_FOLDER+"/"+pictureNames[i];
                Log.i(TAG,"Found "+pictureNames.length+" pictures");
                mSounds.get(i).setBackgroundAssetPath(assetPath);
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    public void release(){
        mSoundPool.release();
    }
}
