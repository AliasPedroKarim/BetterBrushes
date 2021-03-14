package eu.enderium.betterbrushes.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.brush.Brush;

import java.util.Random;

import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.generator.BlockPopulator;

public class RepopulateChunksBrush extends Brush {
    private int a = 1;

    public RepopulateChunksBrush() {
        setName("Repopulate Chunks Brush");
    }

    @Override
    public void info(VoxelMessage voxelMessage) {
        voxelMessage.brushName(getName());
        voxelMessage.custom(ChatColor.GREEN + "Brush Size: " + ChatColor.DARK_RED + this.a);
        if (this.a < 4)
            return;
        voxelMessage.custom(ChatColor.RED + "WARNING: Large brush size selected!");
    }

    public final void arrow(SnipeData paramSnipeData) {
        a();
    }

    public final void powder(SnipeData paramSnipeData) {
        a();
    }

    private void a() {
        double d = (this.a + 0.5D) * (this.a + 0.5D);
        World world = getWorld();
        Random random;
        (random = new Random()).setSeed(world.getSeed());
        org.bukkit.Chunk chunk = getTargetBlock().getChunk();

        int j = chunk.getZ();
        int i = chunk.getX();
        for (int k = this.a; k >= -this.a; k--) {
            for (int m = this.a; m >= -this.a; m--) {
                int n = j - k;
                int i1 = i - m;
                if (((n - j) * (n - j) + (i1 - i) * (i1 - i)) <= d)
                    a(world.getChunkAt(i - m, j - k));
            }
        }
    }

    private static void a(Chunk paramChunk) {
        int x = paramChunk.getX();
        int z = paramChunk.getZ();

        paramChunk.getWorld().getChunkAt(x, z);
        World world = paramChunk.getWorld();

        // BlockSand.instaFall = true;
        Random random;
        (random = new Random()).setSeed(world.getSeed());
        long l1 = (random.nextLong() / 2L << 1L) + 1L;
        long l2 = (random.nextLong() / 2L << 1L) + 1L;
        random.setSeed(x * l1 + z * l2 ^ world.getSeed());
        // world.populating = true;
        for (BlockPopulator populator : world.getPopulators()) {
            BlockPopulator blockPopulator;
            (blockPopulator = populator).populate(world, random, world.getChunkAt(x, z));
        }
        // BlockSand.instaFall = false;
        Bukkit.getPluginManager().callEvent((Event) new ChunkPopulateEvent(paramChunk));
    }

    @Override
    public void parseParameters(String triggerHandle, String[] params, SnipeData paramSnipeData) {
        for (byte b1 = 1; b1 < params.length; b1++) {
            String str;
            if ((str = params[b1].toLowerCase()).startsWith("r")) {
                this.a = Integer.parseInt(str.replace("r", ""));
                if (this.a <= 0)
                    this.a = 0;
                paramSnipeData.sendMessage(ChatColor.AQUA + "Radius set to " + this.a + " chunks");
            }
        }
    }

    public String getPermissionNode() {
        return "voxelsniper.brush.*";
    }

}
