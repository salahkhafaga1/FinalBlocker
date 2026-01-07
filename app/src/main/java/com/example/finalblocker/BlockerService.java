package com.example.finalblocker;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.content.SharedPreferences;
import java.util.Arrays;
import java.util.List;

public class BlockerService extends AccessibilityService {
    // قائمة الحظر
    List<String> BLOCKED = Arrays.asList(
        "com.facebook.katana", 
        "com.instagram.android", 
        "com.tiktok.android"
    );

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getPackageName() != null) {
            if (isLocked() && BLOCKED.contains(event.getPackageName().toString())) {
                performGlobalAction(GLOBAL_ACTION_HOME);
            }
        }
    }

    private boolean isLocked() {
        long unlockTime = getSharedPreferences("BlockerPrefs", MODE_PRIVATE).getLong("UNLOCK_TIME", 0);
        return System.currentTimeMillis() < unlockTime;
    }

    @Override
    public void onInterrupt() {}
}