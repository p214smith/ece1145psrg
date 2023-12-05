package hotciv.framework;

public interface FactoryImpl {
    public actionStrategy getActionStrategy();
    public ageStrategy getAgeStrategy();
    public attackStrategy getAttackStrategy();
    public winningStrategy getWinningStrategy();
    public worldStrategy getWorldStrategy();
    public workforcePopStrategy getWorkforceStrategy();
}
