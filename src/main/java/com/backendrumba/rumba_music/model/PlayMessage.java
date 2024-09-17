package com.backendrumba.rumba_music.model;

public class PlayMessage {
    private Long songId;
    private String roomCode;

    public PlayMessage(Long songId, String roomCode) {
        this.songId = songId;
        this.roomCode = roomCode;
    }

    // Getters y Setters
    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
}
