package com.sparklecow.soundscape.exceptions;

public class SongNotInPlaylistException extends RuntimeException {
    public SongNotInPlaylistException(String message) {
        super(message);
    }
}
