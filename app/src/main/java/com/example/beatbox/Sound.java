package com.example.beatbox;

public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;
    private String mBackgroundAssetPath;

    public String getBackgroundAssetPath() {
        return mBackgroundAssetPath;
    }

    public void setBackgroundAssetPath(String backgroundAssetPath) {
        mBackgroundAssetPath = backgroundAssetPath;
    }

    public Sound(String assetPath){
        mAssetPath=assetPath;
        String[]components=assetPath.split("/");
        String filename=components[components.length-1];
        mName=filename.replace(".wav","");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }

    public String getName() {
        return mName;
    }
}
