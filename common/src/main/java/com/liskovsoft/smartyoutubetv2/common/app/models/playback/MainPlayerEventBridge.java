package com.liskovsoft.smartyoutubetv2.common.app.models.playback;

import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv2.common.app.models.data.Video;
import com.liskovsoft.smartyoutubetv2.common.app.models.playback.listeners.HistoryUpdater;
import com.liskovsoft.smartyoutubetv2.common.app.models.playback.listeners.StateUpdater;
import com.liskovsoft.smartyoutubetv2.common.app.models.playback.listeners.MetadataLoader;
import com.liskovsoft.smartyoutubetv2.common.app.models.playback.listeners.PlayerUiManager;
import com.liskovsoft.smartyoutubetv2.common.app.models.playback.listeners.VideoLoader;

import java.util.ArrayList;

public class MainPlayerEventBridge implements PlayerEventBridge {
    private static final String TAG = MainPlayerEventBridge.class.getSimpleName();
    private final ArrayList<PlayerEventBridge> mEventListeners;
    private static MainPlayerEventBridge sInstance;

    public MainPlayerEventBridge() {
        mEventListeners = new ArrayList<>();

        StateUpdater stateUpdater = new StateUpdater();

        // NOTE: position matters!!!
        mEventListeners.add(new PlayerUiManager());
        mEventListeners.add(stateUpdater);
        mEventListeners.add(new HistoryUpdater());
        mEventListeners.add(new MetadataLoader(stateUpdater));
        mEventListeners.add(new VideoLoader());
    }

    public static MainPlayerEventBridge instance() {
        if (sInstance == null) {
            sInstance = new MainPlayerEventBridge();
        }

        return sInstance;
    }

    @Override
    public void onSuggestionItemClicked(Video item) {
        if (item == null) {
            Log.e(TAG, "onSuggestionItemClicked: item is null");
            return;
        }

        for (PlayerEventListener listener : mEventListeners) {
            listener.onSuggestionItemClicked(item);
        }
    }

    @Override
    public void onSuggestionItemLongClicked(Video item) {
        if (item == null) {
            Log.e(TAG, "onSuggestionItemLongClicked: item is null");
            return;
        }

        for (PlayerEventListener listener : mEventListeners) {
            listener.onSuggestionItemLongClicked(item);
        }
    }

    @Override
    public void onPreviousClicked() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onPreviousClicked();
        }
    }

    @Override
    public void onNextClicked() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onNextClicked();
        }
    }

    @Override
    public void onViewCreated() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onViewCreated();
        }
    }

    @Override
    public void onViewDestroyed() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onViewDestroyed();
        }
    }

    @Override
    public void onViewPaused() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onViewPaused();
        }
    }

    @Override
    public void onViewResumed() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onViewResumed();
        }
    }

    @Override
    public void onVideoLoaded(Video item) {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onVideoLoaded(item);
        }
    }

    @Override
    public void setVideo(Video item) {
        if (item == null) {
            Log.e(TAG, "load: item is null");
            return;
        }

        for (PlayerEventBridge listener : mEventListeners) {
            listener.setVideo(item);
        }
    }

    @Override
    public void setController(PlayerController controller) {
        for (PlayerEventBridge listener : mEventListeners) {
            listener.setController(controller);
        }
    }

    @Override
    public void onEngineInitialized() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onEngineInitialized();
        }
    }

    @Override
    public void onEngineReleased() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onEngineReleased();
        }
    }

    @Override
    public void onPlay() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onPlay();
        }
    }

    @Override
    public void onPause() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onPause();
        }
    }

    @Override
    public void onPlayClicked() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onPlayClicked();
        }
    }

    @Override
    public void onPauseClicked() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onPauseClicked();
        }
    }

    // Not implemented
    @Override
    public void onSeek() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onSeek();
        }
    }

    @Override
    public void onPlayEnd() {
        for (PlayerEventListener listener : mEventListeners) {
            listener.onPlayEnd();
        }
    }
}
