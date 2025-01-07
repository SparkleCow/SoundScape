package com.sparklecow.soundscape.models.common;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Genre {
    // Shoegaze - Dream Pop
    SHOEGAZE,
    DREAM_POP,
    ETHEREAL_WAVE,
    NEO_PSYCHEDELIA,
    CHILLWAVE,
    LO_FI_POP,
    AMBIENT_POP,
    SADCORE,

    // PostPunk
    POST_PUNK,
    DARKWAVE,
    GOTHIC_ROCK,
    COLDWAVE,
    MINIMAL_WAVE,

    // Indie - Alternativo
    INDIE_POP,
    INDIE_ROCK,
    ALTERNATIVE_ROCK,
    ALTERNATIVE,
    NOISE_POP,
    JANGLE_POP,
    BRITPOP,

    // Electronic - Experimental
    SYNTHPOP,
    ELECTROPOP,
    DREAMTRONICA,
    TRIP_HOP,
    IDM, // Intelligent Dance Music
    DOWNTEMPO,
    GLITCH_POP,
    FUTURE_POP,

    // Ambient
    AMBIENT,
    DARK_AMBIENT,
    CINEMATIC_AMBIENT,
    SPACE_AMBIENT,
    DRONE,
    POST_ROCK,

    // Psicodelic
    PSYCHEDELIC_ROCK,
    PSYCHEDELIC_POP,
    SPACE_ROCK,
    ACID_ROCK,

    // Others subgenres
    ART_POP,
    SLOWCORE,
    DREAM_FOLK,
    BAROQUE_POP,
    CHAMBER_POP,
    EXPERIMENTAL_POP,
    ELECTROACOUSTIC,
    NEOCLASSICAL,
    INDUSTRIAL_POP,

    // Regionals
    JAPANESE_SHOEGAZE,
    SCANDINAVIAN_DREAM_POP,
    EUROPEAN_SHOEGAZE,
    LATIN_DREAM_POP,
    ASIAN_AMBIENT,

    // Rock
    POST_HARDCORE,
    EMO,
    MATH_ROCK,
    SLUDGE,
    STONER_ROCK,

    // General
    POP,
    ROCK,
    FOLK,
    ELECTRONIC,
    EXPERIMENTAL,
    CLASSICAL;

    @Override
    public String toString() {
        return capitalizeWords(this.name());
    }

    private String capitalizeWords(String genreName){
        String[] text = genreName.split("_");
        return Arrays.stream(text)
                .map(x -> x.substring(0,1).toUpperCase() + x.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}