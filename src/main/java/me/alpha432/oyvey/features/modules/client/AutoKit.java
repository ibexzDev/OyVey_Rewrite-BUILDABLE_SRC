package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.features.command.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;


public class AutoKit extends Module {

    public Setting<String> kitname = this.register(new Setting<String>("Imperial", "Imperial"));


    public AutoKit() {
        super("AutoKit", "", Category.PhobosLite, false, false, false);
    }
    @Override
    public void onEnable() {
        Command.sendMessage("Claimed kit " + kitname.getValue());
        mc.player.sendChatMessage("/kit " + kitname.getValue());
        this.disable();
    }

}
