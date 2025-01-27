package com.sparklecow.soundscape.exceptions;

public class SongAlreadyInPlaylistException extends RuntimeException {
    public SongAlreadyInPlaylistException(String message) {
        super(message);
    }
}
