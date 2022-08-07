package media.cfr.redalert.player;

public enum Alarm {
    REDALERT("sound/alerts/redalert.wav"),
    TONE("sound/alerts/tone.mp3"),
    ALERT_CRITICAL("sound/alerts/missile_alert_for_your_area.mp3"),
    ALERT_FOR("sound/alerts/missile_alert_for.mp3");

    private String path;
    Alarm(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
