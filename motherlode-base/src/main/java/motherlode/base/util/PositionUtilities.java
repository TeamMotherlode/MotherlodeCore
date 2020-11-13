package motherlode.core.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PositionUtilities {
    public static Vec3d fromLerpedPosition(Vec3d start, Vec3d end, float delta) {
        double d = MathHelper.lerp(delta, start.x, end.x);
        double e = MathHelper.lerp(delta, start.y, end.y);
        double f = MathHelper.lerp(delta, start.z, end.z);
        return new Vec3d(d, e, f);
    }
}
