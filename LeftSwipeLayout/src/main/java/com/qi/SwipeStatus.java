package com.qi;

/**
 * Created by fengqi.sun on 2017/4/20.
 */

public enum SwipeStatus {
    /**
     * 左拉状态
     */
    LEFT_SWIPE,
    /**
     * 释放立即刷新状态
     */
    RELEASE_SWIPE ,

    /**
     * 正在刷新状态
     */
    SWIPING ,

    /**
     * 刷新完成或未刷新状态
     */
    SWIPED;
}
