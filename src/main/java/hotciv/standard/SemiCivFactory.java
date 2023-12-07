package hotciv.standard;
import hotciv.standard.gameDrawing;
import hotciv.framework.FactoryImpl;
import hotciv.framework.Game;
import hotciv.view.CivDrawing;
import hotciv.view.MapView;
import minidraw.framework.*;

import javax.swing.*;
public class SemiCivFactory implements Factory {
    private Game game;
    public SemiCivFactory(Game g) { game = g; }

    public DrawingView createDrawingView( DrawingEditor editor ) {
        DrawingView view =
                new MapView(editor, game);
        return view;
    }

    public Drawing createDrawing( DrawingEditor editor ) {
        return new gameDrawing( editor, game );
    }

    public JTextField createStatusField( DrawingEditor editor ) {
        JTextField f = new JTextField("SWEA template code");
        f.setEditable(false);
        return f;
    }
}
