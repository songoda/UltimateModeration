package com.songoda.ultimatemoderation.punish;

import java.util.UUID;

public class AppliedPunishment extends Punishment {

    private final UUID victim;
    private final UUID punisher;
    private long expiration;

    public AppliedPunishment(PunishmentType punishmentType, long duration, String reason, UUID victim, UUID punisher, long expiration, UUID uuid) {
        super(punishmentType, duration, reason, uuid);
        this.victim = victim;
        this.punisher = punisher;
        this.expiration = expiration;
    }

    public AppliedPunishment(PunishmentType punishmentType, long duration, String reason, UUID victim, UUID punisher, long expiration) {
        super(punishmentType, duration, reason);
        this.victim = victim;
        this.punisher = punisher;
        this.expiration = expiration;
    }

    public AppliedPunishment(Punishment punishment, UUID victim, UUID punisher, long expiration) {
        super(punishment);
        this.victim = victim;
        this.punisher = punisher;
        this.expiration = expiration;
    }

    public UUID getVictim() {
        return victim;
    }

    public UUID getPunisher() {
        return punisher;
    }

    public long getExpiration() {
        return expiration;
    }

    public void expire() {
        this.expiration = -1;
    }

    public long getTimeRemaining() {
        return expiration - System.currentTimeMillis();
    }
}
