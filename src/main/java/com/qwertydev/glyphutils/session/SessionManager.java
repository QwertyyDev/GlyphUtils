package com.qwertydev.glyphutils.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private final Map<UUID, PlayerSession> sessions = new HashMap<>();

    public PlayerSession getSession(UUID uuid) {
        return sessions.computeIfAbsent(uuid, k -> new PlayerSession());
    }

    public boolean hasActiveSession(UUID uuid) {
        PlayerSession session = sessions.get(uuid);
        return session != null && session.isWaitingForInput();
    }

    public void removeSession(UUID uuid) {
        sessions.remove(uuid);
    }
}