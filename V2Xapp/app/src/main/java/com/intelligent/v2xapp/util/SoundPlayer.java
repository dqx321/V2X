package com.intelligent.v2xapp.util;

/**
 * Created by 长沙智能驾驶研究院cidi daiqinxue on 2018/3/29.
 */


import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * 播放声音工具类
 *
 * @desc
 */
public class SoundPlayer {

    private Context context;
    private SoundPool soundPool;
    private int soundID;

    public SoundPlayer(Context context) {
        this.context = context;
        this.soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
    }

    /**
     * 加载声音资源
     *
     * @param resId
     * @return soundID
     */
    public int loadRes(int resId) {
        try {
            return this.soundPool.load(this.context, resId, 1);

        } catch (Resources.NotFoundException E) {
            return 0;
        }
    }

    /**
     * 播放声音
     *
     * @param soundID
     * @param priority
     * @param isLoop
     */
    long  a=0;
    long  b=0;
    public void play(int soundID, int priority, boolean isLoop) {
        b=System.currentTimeMillis();
        if ((b-a)>2000){
            a=b;
            this.soundPool.play(soundID, 1, 1, priority, isLoop ? -1 : 0, 1);
        }


    }

    /**
     * 停止
     *
     * @param soundID
     */
    public void stop(int soundID) {
        this.soundPool.stop(soundID);
        this.soundPool.release();
    }

    /**
     * 播放声音
     *
     * @param resId
     * @param isLoop
     */
    public void play(int resId, final boolean isLoop) {
        this.soundID = loadRes(resId);
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                play(soundID, 0, isLoop);
            }
        });
    }

    /**
     * 停止
     */
    public void stop() {
        stop(this.soundID);
    }

}
