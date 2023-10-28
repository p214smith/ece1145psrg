package hotciv.standard;
import hotciv.framework.*;
public class betaCivAgeStrategy implements ageStrategy {
    @Override
    public int ageGame(int game_age) {
        if (game_age < -100)
            game_age = game_age + 100;
        else if (game_age == -100)
            game_age = game_age + 99;
        else if(game_age == 0 || game_age == -1)
            game_age = game_age + 1;
        else if(game_age == 1)
            game_age = game_age + 49;
        else if( game_age < 1750)
            game_age = game_age +50;
        else if(game_age < 1900)
            game_age = game_age +25;
        else if(game_age < 1970)
            game_age = game_age + 5;
        else
            game_age += 1;

    return game_age;
    }
}
