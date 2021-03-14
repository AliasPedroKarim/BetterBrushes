package eu.enderium.betterbrushes.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.brush.Brush;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WallSider extends Brush {
    private static String[] a = new String[]{"north", "east", "south", "west", "relative to player"};
    private static BlockFace[] b;
    private short c = 4;
    private short d = 1;
    private double e = 0.0D;
    private boolean f;
    private boolean g;
    private boolean h;

    static {
        (new BlockFace[4])[0] = BlockFace.NORTH;
        (new BlockFace[4])[1] = BlockFace.EAST;
        (new BlockFace[4])[2] = BlockFace.SOUTH;
        (new BlockFace[4])[3] = BlockFace.WEST;
    }

    public WallSider() {
        setName("WallSider");
    }

    private void a(SnipeData paramSnipeData, Block paramBlock, boolean paramBoolean) {
        double d1 = (paramSnipeData.getBrushSize() + this.e) * (paramSnipeData.getBrushSize() + this.e);
        Vector vector1;
        Vector vector2 = (vector1 = paramBlock.getLocation().toVector()).clone();
        Player player;
        double d2;
        if ((d2 = (((player = paramSnipeData.owner().getPlayer()).getLocation().getYaw() - 90.0F) % 360.0F)) < 0.0D)
            d2 += 360.0D;
        short s = (this.c == 4) ? (short) ((0.0D >= d2 && d2 < 45.0D) ? 2 : ((45.0D >= d2 && d2 < 135.0D) ? 3 : ((135.0D >= d2 && d2 < 225.0D) ? 0 : ((225.0D >= d2 && d2 < 315.0D) ? 1 : ((315.0D >= d2 && d2 < 360.0D) ? 2 : -1))))) : this.c;
        if (paramBoolean)
            s = (short) ((s + 2) % 4);
        byte b = 98;
        if (s == 0 || s == 2)
            b = 97;
        for (int i = -paramSnipeData.getBrushSize(); i <= paramSnipeData.getBrushSize(); i++) {
            if (b == 97) {
                vector2.setX(vector1.getX() + i);
            } else {
                vector2.setZ(vector1.getZ() + i);
            }
            for (int j = -paramSnipeData.getBrushSize(); j <= paramSnipeData.getBrushSize(); j++) {
                vector2.setY(vector1.getY() + j);
                if (vector1.distanceSquared(vector2) <= d1) {
                    for (byte b1 = 0; b1 < this.d; b1++) {
                        if (b == 97) {
                            vector2.setZ(vector1.getZ() + ((s == 2) ? b1 : -b1));
                        } else {
                            vector2.setX(vector1.getX() + ((s == 1) ? b1 : -b1));
                        }
                        Block block = getWorld().getBlockAt(vector2.getBlockX(), vector2.getBlockY(), vector2.getBlockZ());
                        if ((this.f && block.getType() == paramSnipeData.getVoxelMaterial()) || (!this.f && (block.getType() != null || this.g)))
                            block.setType(paramSnipeData.getVoxelMaterial());
                    }
                    if (b == 97) {
                        vector2.setZ(vector1.getZ());
                    } else {
                        vector2.setX(vector1.getX());
                    }
                }
            }
        }
    }

    protected final void arrow(SnipeData paramSnipeData) {
        a(paramSnipeData, getTargetBlock(), false);
    }

    protected final void powder(SnipeData paramSnipeData) {
        a(paramSnipeData, getTargetBlock(), true);
    }

    @Override
    public void parseParameters(String triggerHandle, String[] params, SnipeData paramSnipeData) {
        for (byte b = 1; b < params.length; b++) {
            String str;
            if ((str = params[b].toLowerCase()).startsWith("eu.enderium.betterbrushes.BetterBrushes")) {
                this.d = (short) Integer.parseInt(str.replace("eu.enderium.betterbrushes.BetterBrushes", ""));
                paramSnipeData.sendMessage(ChatColor.AQUA + "Depth set to " + this.d + " blocks");
            } else if (str.startsWith("s")) {
                this.c = (short) Integer.parseInt(str.replace("s", ""));
                if (this.c > 4 || this.c < 0)
                    this.c = 4;
                paramSnipeData.sendMessage(ChatColor.AQUA + "Orientation set to " + a[this.c]);
            } else if (str.startsWith("true")) {
                this.e = 0.5D;
                paramSnipeData.sendMessage(ChatColor.AQUA + "True circle mode ON.");
            } else if (str.startsWith("false")) {
                this.e = 0.0D;
                paramSnipeData.sendMessage(ChatColor.AQUA + "True circle mode OFF.");
            } else if (str.startsWith("air")) {
                this.g = true;
                paramSnipeData.sendMessage(ChatColor.AQUA + "Including air.");
            } else if (str.startsWith("mm")) {
                this.f = true;
                paramSnipeData.sendMessage(ChatColor.AQUA + "Replacing selected block.");
            }
        }

    }

    private static short a(Player paramPlayer) {
        double d;
        if ((d = ((paramPlayer.getLocation().getYaw() - 90.0F) % 360.0F)) < 0.0D)
            d += 360.0D;
        return (short) ((0.0D >= d && d < 45.0D) ? 2 : ((45.0D >= d && d < 135.0D) ? 3 : ((135.0D >= d && d < 225.0D) ? 0 : ((225.0D >= d && d < 315.0D) ? 1 : ((315.0D >= d && d < 360.0D) ? 2 : -1)))));
    }

    public String getPermissionNode() {
        return "voxelsniper.brush.*";
    }

    @Override
    public void info(VoxelMessage voxelMessage) {
        voxelMessage.brushName(getName());
        voxelMessage.size();
        voxelMessage.voxel();
        voxelMessage.custom(ChatColor.BLUE + "Depth: " + this.d);
        voxelMessage.custom(ChatColor.BLUE + "Orientation: " + a[this.c]);
        voxelMessage.custom(ChatColor.BLUE + "True Circle: " + ((this.e == 0.0D) ? "false" : "true"));
        voxelMessage.custom(ChatColor.BLUE + "Air: " + (!this.g ? "exclude" : "include"));
        voxelMessage.custom(ChatColor.BLUE + "Replacing: " + (this.f ? "selected" : "all"));
    }
}
