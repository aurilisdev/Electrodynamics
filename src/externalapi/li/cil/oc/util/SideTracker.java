package li.cil.oc.util;

import java.util.Collections;
import java.util.Set;

import cpw.mods.fml.common.FMLCommonHandler;

public final class SideTracker {
    private static final Set<Thread> serverThreads = Collections.newSetFromMap(new java.util.WeakHashMap<Thread, Boolean>());

    public static void addServerThread() {
        serverThreads.add(Thread.currentThread());
    }

    public static boolean isServer() {
        return FMLCommonHandler.instance().getEffectiveSide().isServer() || serverThreads.contains(Thread.currentThread());
    }

    public static boolean isClient() {
        return !isServer();
    }
}
