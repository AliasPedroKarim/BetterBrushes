package eu.enderium.betterbrushes;

import com.thevoxelbox.voxelsniper.VoxelBrushManager;
import com.thevoxelbox.voxelsniper.VoxelCommandManager;
import com.thevoxelbox.voxelsniper.command.VoxelCommand;
import eu.enderium.betterbrushes.brush.ErodeBlendBall;
import eu.enderium.betterbrushes.brush.RepopulateChunksBrush;
import eu.enderium.betterbrushes.brush.WallSider;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class BetterBrushes extends JavaPlugin {
    private static BetterBrushes INSTANCE;

    private VoxelBrushManager voxelBrushManager;
    private VoxelCommandManager voxelCommandManager;

    @Override
    public final void onEnable() {
        this.voxelBrushManager = VoxelBrushManager.getInstance();

        this.voxelBrushManager.registerSniperBrush(ErodeBlendBall.class, "eb", "erodeblend");
        this.voxelBrushManager.registerSniperBrush(RepopulateChunksBrush.class, "repop", "repopulate");
        this.voxelBrushManager.registerSniperBrush(WallSider.class, "sider", "wallsider");

        this.voxelCommandManager = VoxelCommandManager.getInstance();
        try {
            getLogger().info("I use reflection class to empty the list of commands and the list of command arguments to be able to reload them.");
            getLogger().warning("I know it's a bad idea but I can't do otherwise, then what the creators of VoxelSniper Rebooted have put all the access fields in private.");
            Field commands = this.voxelCommandManager.getClass().getDeclaredField("commands");
            Field argumentsMap = this.voxelCommandManager.getClass().getDeclaredField("argumentsMap");
            commands.setAccessible(true);
            argumentsMap.setAccessible(true);
            commands.set(this.voxelCommandManager, new ArrayList<VoxelCommand>());
            argumentsMap.set(this.voxelCommandManager, new HashMap<String, List<String>>());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            VoxelCommandManager.initialize();
        }

        INSTANCE = this;
    }
}
