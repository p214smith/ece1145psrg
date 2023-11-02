package hotciv.framework;

public interface Factory {
    public actionStrategy getActionStrategy();
    public ageStrategy getAgeStrategy();
    public attackStrategy getAttackStrategy();
    public winningStrategy getWinningStrategy();
    public worldStrategy getWorldStrategy();
    public workforcePopStrategy getWorkforceStrategy();
}
