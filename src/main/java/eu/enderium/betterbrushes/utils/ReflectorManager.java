package eu.enderium.betterbrushes.utils;

import com.thevoxelbox.voxelsniper.brush.IBrush;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectorManager {
    public static Method[] a(Method[] paramArrayOfMethod, String[] paramArrayOfString) {
        Method[] arrayOfMethod = new Method[3];
        for (byte b = 0; b < 3; b++) {
            for (byte b1 = 0; b1 < paramArrayOfMethod.length; b1++) {
                if (paramArrayOfMethod[b1].getName().equals(paramArrayOfString[b])) {
                    arrayOfMethod[b] = paramArrayOfMethod[b1];
                    arrayOfMethod[b].setAccessible(true);
                    break;
                }
            }
        }
        return arrayOfMethod;
    }

    public static void a(Method paramMethod, IBrush paramIBrush, Object... paramVarArgs) {
        try {
            paramMethod.invoke(paramIBrush, paramVarArgs);
        } catch (Exception exception) {
            paramMethod = null;
            exception.printStackTrace();
        }
    }

    public static void a(Field paramField, Object paramObject1, Object paramObject2) {
        try {
            paramField.set(paramObject1, paramObject2);
        } catch (Exception exception) {
            paramField = null;
            exception.printStackTrace();
        }
    }

    public static Field[] a(Field[] paramArrayOfField, String[] paramArrayOfString) {
        Field[] arrayOfField = new Field[1];
    /*for (byte b = 0; !b; b++) {
    } */

        for (byte b1 = 0; b1 < paramArrayOfField.length; b1++) {
            if (paramArrayOfField[b1].getName().equals(paramArrayOfString[0])) {
                arrayOfField[0] = paramArrayOfField[b1];
                arrayOfField[0].setAccessible(true);
                break;
            }
        }

        return arrayOfField;
    }
}
