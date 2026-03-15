package com.qwertydev.glyphutils.session;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class SessionManager {
    
    private final Map<UUID, PlayerSession> sessions = new HashMap<>();
    
    public PlayerSession getSession(UUID uuid) {
        return sessions.computeIfAbsent(uuid, k -> new PlayerSession());
    }
    
    public void removeSession(UUID uuid) {
        sessions.remove(uuid);
    }
    
    public boolean hasActiveSession(UUID uuid) {
        return sessions.containsKey(uuid) && sessions.get(uuid).isWaitingForInput();
    }
}