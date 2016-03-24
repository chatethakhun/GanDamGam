package sut.game01.core;

import playn.core.Font;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;



import static playn.core.PlayN.graphics;

/**
 * Created by Administrator on 23/3/2559.
 */
public class HomeScreen extends UIScreen{

    public static final Font TITLE_FONT = graphics().createFont("Helvetica", Font.Style.PLAIN, 24);

    private final ScreenStack ss;
    private final TestScreen testScreen;

    private Root root;

    public HomeScreen(ScreenStack ss){
        this.ss = ss;
        this.testScreen = new TestScreen(ss);
    }

    @Override
    
    public void wasShown() {
        super.wasShown();

        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), this.layer);

        
        root
                .addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).inset(5, 10)));
        root.setSize(width(),height());

        root.add(new Label("GanDam Game")
        .addStyles(Style.FONT.is(HomeScreen.TITLE_FONT)));

        root.add(new Button("Play Game").onClick(new UnitSlot() {
            @Override
            public void onEmit() {
                ss.push(testScreen);
            }
        }));
    }
}
