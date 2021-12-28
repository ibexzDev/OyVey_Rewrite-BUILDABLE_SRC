package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.login.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;

public class AutoNigger extends Module {
    public AutoNigger() {
        super("AutoNigger", "FakeKick", Category.COMBAT, false, false, false);
    }
    //empty for now working on a fake kick
}
