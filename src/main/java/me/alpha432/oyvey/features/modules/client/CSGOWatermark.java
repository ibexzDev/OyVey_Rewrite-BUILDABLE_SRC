package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.events.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.ColorUtil;
import me.alpha432.oyvey.util.RenderUtil;
import me.alpha432.oyvey.util.Timer;


import java.awt.*;

public class CSGOWatermark extends Module {



    public CSGOWatermark() {
        super("Watermark", "Watermark", Category.PhobosLite, false, false, false);
    }

    Timer delayTimer = new Timer();
    public Setting<Integer> X = this.register(new Setting("WatermarkX", 10, 0, 300));
    public Setting<Integer> Y = this.register(new Setting("WatermarkY", 10, 0, 300));
    public Setting<Integer> delay = this.register(new Setting<Object>("Delay", 240, 0, 600));
    public Setting<Integer> saturation = this.register(new Setting<Object>("Saturation", 127, 1, 255));
    public Setting<Integer> brightness = this.register(new Setting<Object>("Brightness", 100, 0, 255));
    public float hue;
    public int red = 1;
    public int green = 1;
    public int blue = 1;

    private String message = "";


    @Override
    public void onRender2D ( Render2DEvent event ) {
        drawCsgoWatermark();
    }





    public int rainbow(int delay){
        double RS = Math.ceil((System.currentTimeMillis() + delay / 10.0));
        RS %= 360;
        return Color.getHSBColor((float)(RS/360.0f),0.5f,1f).getRGB();
    }


    public void drawCsgoWatermark () {
        final int[] counter = {1};
        int padding = 5;
        message = "PhobosLite 0.3 | " + mc.player.getName() + " | " + OyVey.serverManager.getPing() + "ms";
        Integer textWidth = mc.fontRenderer.getStringWidth(message); // taken from wurst+ 3
        Integer textHeight = mc.fontRenderer.FONT_HEIGHT; // taken from wurst+ 3

        RenderUtil.drawRectangleCorrectly(X.getValue() - 4, Y.getValue() - 4, textWidth + 16, textHeight + 12, ColorUtil.toRGBA(22, 22, 22, 255));
        RenderUtil.drawRectangleCorrectly(X.getValue(), Y.getValue(), textWidth + 4, textHeight + 4, ColorUtil.toRGBA(0, 0, 0, 255));
        RenderUtil.drawRectangleCorrectly(X.getValue(), Y.getValue(), textWidth + 8, textHeight + 4, ColorUtil.toRGBA(0, 0, 0, 255));
        RenderUtil.drawRectangleCorrectly(X.getValue(), Y.getValue(), textWidth + 8,  1, ColorUtil.toRGBA(192,192,255));
        OyVey.textManager.drawString(message, X.getValue() + 3, Y.getValue() + 3, ColorUtil.toRGBA(255, 255, 255, 255), false);
        counter[0]++;
    }
}
