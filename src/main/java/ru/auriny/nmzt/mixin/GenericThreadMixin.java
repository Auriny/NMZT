package ru.auriny.nmzt.mixin;

import net.minecraft.server.rcon.thread.GenericThread;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.auriny.nmzt.NMZT;

@Mixin(GenericThread.class)
public abstract class GenericThreadMixin {
    @Shadow @Final protected String name;

    @Inject(method = "stop", at = @At("TAIL"))
    private void onShutdown(CallbackInfo ci) {
        NMZT.LOGGER.info("Ensuring all threads are disabled...");

        if (this.name.equals("Query Listener")) {
            ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
            while (rootGroup.getParent() != null) {
                rootGroup = rootGroup.getParent();
            }

            Thread[] threads = new Thread[rootGroup.activeCount()];
            int n = rootGroup.enumerate(threads, true);
            NMZT.LOGGER.info(String.format("Terminating %s threads...", n));

            for (Thread thread : threads) {
                if (thread != null && thread.isAlive() && !thread.isDaemon()) {
                    thread.interrupt();
                }
            }

        }
    }
}
