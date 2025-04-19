package it.creeper.roman.player;

import org.bukkit.Location;

public class PlayerData {
    public double lastSpeed = 0;
    public double lastYSpeed = 0;
    public boolean inAir = false;
    public int airTicks = 0;
    public boolean lastOnGround = true;
    public float lastYaw = 0;
    public Location lastLocation = null;
    public boolean allowedFastMovement = false;
    public boolean allowedHighJump = false;
    public int teleportTicks = 10;
    public long lastDamageTime = 0;
    public int speedViolations = 0;
    public int bhopViolations = 0;
    public int vhopViolations = 0;
    public int flyViolations = 0;
}
