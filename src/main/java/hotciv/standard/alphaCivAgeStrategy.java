package hotciv.standard;
import hotciv.framework.*;
public class alphaCivAgeStrategy implements ageStrategy{
    @Override
    public int ageGame(int game_age) {
       return game_age + 100;
    }
}
