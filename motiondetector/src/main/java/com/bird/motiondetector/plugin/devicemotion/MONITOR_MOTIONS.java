package com.bird.motiondetector.plugin.devicemotion;

enum MONITOR_MOTIONS {
    // we can add multiple enum type in future if we are tracking for
    // multiple motion like shake, rotate, move etc
    FREE_FALL(new FreeFallMotion()),
    ROTATION(new RotateMotion());

    TypesOfMotion motion;
    MONITOR_MOTIONS(TypesOfMotion motion) {
        this.motion = motion;
    }

    TypesOfMotion getMotion() {
        return motion;
    }
}