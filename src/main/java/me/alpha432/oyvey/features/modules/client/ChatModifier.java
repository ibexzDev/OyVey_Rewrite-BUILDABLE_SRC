package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.features.command.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.manager.CommandManager;
import me.alpha432.oyvey.util.Font;
import me.alpha432.oyvey.util.TextUtil;
import me.alpha432.oyvey.util.Timer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatModifier
        extends Module {
    private static ChatModifier INSTANCE = new ChatModifier();
    private final Timer timer = new Timer();
    public Setting<Suffix> suffix = this.register(new Setting<Suffix>("Suffix", Suffix.NONE, "Your Suffix."));
    public Setting<Boolean> clean = this.register(new Setting<Boolean>("CleanChat", Boolean.valueOf(false), "Cleans your chat"));
    public Setting<Boolean> infinite = this.register(new Setting<Boolean>("Infinite", Boolean.valueOf(false), "Makes your chat infinite."));
    public Setting<Boolean> autoQMain = this.register(new Setting<Boolean>("AutoQMain", Boolean.valueOf(false), "Spams AutoQMain"));
    public Setting<Boolean> qNotification = this.register(new Setting<Object>("QNotification", Boolean.valueOf(false), v -> this.autoQMain.getValue()));
    public Setting<Integer> qDelay = this.register(new Setting<Object>("QDelay", Integer.valueOf(9), Integer.valueOf(1), Integer.valueOf(90), v -> this.autoQMain.getValue()));
    public Setting<TextUtil.Color> timeStamps = this.register(new Setting<TextUtil.Color>("Time", TextUtil.Color.NONE));
    public Setting<Boolean> rainbowTimeStamps = this.register(new Setting<Object>("RainbowTimeStamps", Boolean.valueOf(false), v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
    public Setting<TextUtil.Color> bracket = this.register(new Setting<Object>("Bracket", TextUtil.Color.WHITE, v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
    public Setting<Boolean> space = this.register(new Setting<Object>("Space", Boolean.valueOf(true), v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
    public Setting<Boolean> all = this.register(new Setting<Object>("All", Boolean.valueOf(false), v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
    public Setting<Boolean> shrug = this.register(new Setting<Boolean>("Shrug", false));
    public Setting<Boolean> disability = this.register(new Setting<Boolean>("Disability", false));

    public ChatModifier() {
        super("ChatSuffix", "Modifies your chat", Module.Category.MISC, true, false, false);
        this.setInstance();
    }

    public static ChatModifier getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatModifier();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (this.shrug.getValue().booleanValue()) {
            ChatModifier.mc.player.sendChatMessage(TextUtil.shrug);
            this.shrug.setValue(false);
        }
        if (this.autoQMain.getValue().booleanValue()) {
            if (!this.shouldSendMessage(ChatModifier.mc.player)) {
                return;
            }
            if (this.qNotification.getValue().booleanValue()) {
                Command.sendMessage("<AutoQueueMain> Sending message: /queue main");
            }
            ChatModifier.mc.player.sendChatMessage("/queue main");
            this.timer.reset();
        }
    }

    public String convert_base(String base) {
        return Font.smoth(base);
    }


    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getStage() == 0 && event.getPacket() instanceof CPacketChatMessage) {
            CPacketChatMessage packet = event.getPacket();
            String s = packet.getMessage();
            if (s.startsWith("/")) {
                return;
            }
            if (s.startsWith("#")){
                return;
            }
            if (s.startsWith("@")){
                return;
            }
            switch (this.suffix.getValue()) {
                case onetapcc: {
                    s = s + convert_base(" | OneTap.cc");
                    break;
                }
                case onetapclub: {
                    s = s + convert_base(" OneTap.dev");
                    break;
                }
            }
            if (s.length() >= 256) {
                s = s.substring(0, 256);
            }
            packet.message = s;
        }
    }

    @SubscribeEvent
    public void onChatPacketReceive(PacketEvent.Receive event) {
        if (event.getStage() != 0 || event.getPacket() instanceof SPacketChat) {
            // empty if block
        }
    }



    private boolean shouldSendMessage(EntityPlayer player) {
        if (player.dimension != 1) {
            return false;
        }
        if (!this.timer.passedS(this.qDelay.getValue().intValue())) {
            return false;
        }
        return player.getPosition().equals(new Vec3i(0, 240, 0));
    }

    public enum Suffix {
        NONE,
        onetapcc,
        onetapclub

    }
}

