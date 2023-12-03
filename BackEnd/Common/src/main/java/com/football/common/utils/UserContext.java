package com.football.common.utils;

public class UserContext {
    private static final ThreadLocal<Long> user_tl = new ThreadLocal<>();

    public static void setUser(Long userId){
        // 保存当前用户id到ThreadLocal
        user_tl.set(userId);
    }

    public static Long getUser(){
        // 获取用户id
        return user_tl.get();
    }

    public static void removeUser(){
        user_tl.remove();
    }
}
