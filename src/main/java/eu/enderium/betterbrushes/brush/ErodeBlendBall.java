package eu.enderium.betterbrushes.brush;

import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.brush.BlendBallBrush;
import com.thevoxelbox.voxelsniper.brush.BlendBrushBase;
import com.thevoxelbox.voxelsniper.brush.Brush;
import com.thevoxelbox.voxelsniper.brush.ErodeBrush;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import eu.enderium.betterbrushes.utils.ReflectorManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ErodeBlendBall extends Brush {
    private final ErodeBrush erodeBrush = new ErodeBrush();
    private final BlendBallBrush blendBallBrush = new BlendBallBrush();
    private static Method c;
    private static Method d;
    private static Method e;
    private static Field f;

    public List<String> ERODE_ARGS = Arrays.asList("NONE", "MELT", "FILL", "SMOOTH", "LIFT", "FLOATCLEAN", "INFO");

    public ErodeBlendBall() {
        setName("Erode BlendBall");
        if (c == null || d == null)
            try {
                Method[] arrayOfMethod;
                c = (arrayOfMethod = ReflectorManager.a(Brush.class.getDeclaredMethods(), new String[]{"arrow", "powder", "setTargetBlock"}))[0];
                d = arrayOfMethod[1];
                e = arrayOfMethod[2];
                f = ReflectorManager.a(BlendBrushBase.class.getDeclaredFields(), new String[]{"excludeAir"})[0];
            } catch (SecurityException securityException2) {
                SecurityException securityException1;
                (securityException1 = null).printStackTrace();
            }
    }

    @Override
    public void info(VoxelMessage voxelMessage) {
        this.erodeBrush.info(voxelMessage);
        this.blendBallBrush.info(voxelMessage);
    }

    @Override
    public final void arrow(SnipeData paramSnipeData) {
        ReflectorManager.a(f, this.blendBallBrush, Boolean.FALSE);
        ReflectorManager.a(e, this.erodeBrush, getTargetBlock());
        ReflectorManager.a(e, this.blendBallBrush, getTargetBlock());
        ReflectorManager.a(c, this.erodeBrush, paramSnipeData);
        ReflectorManager.a(c, this.blendBallBrush, paramSnipeData);
    }

    @Override
    public final void powder(SnipeData paramSnipeData) {
        ReflectorManager.a(f, this.blendBallBrush, Boolean.FALSE);
        ReflectorManager.a(e, this.erodeBrush, getTargetBlock());
        ReflectorManager.a(e, this.blendBallBrush, getTargetBlock());
        ReflectorManager.a(d, this.erodeBrush, paramSnipeData);
        ReflectorManager.a(c, this.blendBallBrush, paramSnipeData);
    }

    @Override
    public void parseParameters(String triggerHandle, String[] params, SnipeData paramSnipeData) {
        if(ERODE_ARGS.contains(params[0].toUpperCase())) {
            this.erodeBrush.parseParameters(triggerHandle, params, paramSnipeData);
        }

        if(params[0].equalsIgnoreCase("water") || params[0].equalsIgnoreCase("info")) {
            this.blendBallBrush.parseParameters(triggerHandle, params, paramSnipeData);
        }
    }

    @Override
    public List<String> registerArguments() {
        List<String> arguments = ERODE_ARGS.stream().map(String::toLowerCase).collect(Collectors.toList());
        arguments.add("info");

        System.out.println(">>>>>>>>>>>>>>>>>>>>>> " + String.join(", ", arguments));

        return arguments;
    }

    public String getPermissionNode() {
        return "voxelsniper.brush.erode";
    }
}
