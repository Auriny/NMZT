package ru.auriny.nmzt.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "stopServer", at = @At("TAIL"))
    private void onShutdown(CallbackInfo ci) {
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        while (rootGroup.getParent() != null) {
            rootGroup = rootGroup.getParent();
        }

        Thread[] threads = new Thread[rootGroup.activeCount()];
        rootGroup.enumerate(threads, true);

        for (Thread thread : threads) {
            if (thread != null && thread.isAlive() && !thread.isDaemon()) {
                thread.interrupt();
            }
        }

        Executors.newSingleThreadScheduledExecutor().schedule(() -> Runtime.getRuntime().halt(0), 3, TimeUnit.SECONDS);
    }
}
