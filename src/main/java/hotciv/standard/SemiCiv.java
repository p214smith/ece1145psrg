package hotciv.standard;

import hotciv.framework.Game;
import hotciv.stub.StubGame2;

import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class SemiCiv {
    public static void main(String[] args) {
        Game game = new GameImpl(new semiFactory());

        DrawingEditor editor =
                new MiniDrawApplication( "Click and/or drag any item to see all game actions",
                        new SemiCivFactory(game) );
        editor.open();
        editor.showStatus("Click and drag any item to see Game's proper response.");

        // TODO: Replace the setting of the tool with your CompositionTool implementation.
        editor.setTool( new CompTool(editor,game) );
    }
}
