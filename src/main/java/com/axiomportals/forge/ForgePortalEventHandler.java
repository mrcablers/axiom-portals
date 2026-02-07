package com.axiomportals.forge;

/**
 * Forge event handler for Axiom-Portals compatibility.
 * This is a placeholder showing the structure for Forge compatibility.
 * 
 * Usage: Register with MinecraftForge.EVENT_BUS in main Forge entry point
 * 
 * Example implementation:
 * 
 * @Mod.EventBusSubscriber(modid = AxiomPortalsMain.MOD_ID)
 * public class PortalEventHandler {
 *     @SubscribeEvent
 *     public static void onEntityTeleport(EntityTeleportEvent event) {
 *         PortalCompatibilityManager.onPrePortalTraverse(event.getEntity());
 *         // ... handle teleport
 *         PortalCompatibilityManager.onPostPortalTraverse(event.getEntity());
 *     }
 * }
 */
public class ForgePortalEventHandler {
    
    /**
     * This class is referenced by Forge implementations.
     * Forge event handlers should delegate to PortalCompatibilityManager
     * just like the Fabric side does.
     */
    public static final String DOCUMENTATION = "" +
        "// Forge Implementation Example:\n" +
        "package com.axiomportals.forge.event;\n" +
        "\n" +
        "import net.minecraftforge.api.distmarker.Dist;\n" +
        "import net.minecraftforge.event.entity.EntityTeleportEvent;\n" +
        "import net.minecraftforge.eventbus.api.SubscribeEvent;\n" +
        "import net.minecraftforge.fml.common.Mod;\n" +
        "import com.axiomportals.PortalCompatibilityManager;\n" +
        "import com.axiomportals.AxiomPortalsMain;\n" +
        "\n" +
        "@Mod.EventBusSubscriber(modid = AxiomPortalsMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)\n" +
        "public class ForgePortalEventHandler {\n" +
        "\n" +
        "    @SubscribeEvent\n" +
        "    public static void onEntityTeleport(EntityTeleportEvent event) {\n" +
        "        var entity = event.getEntity();\n" +
        "        PortalCompatibilityManager.onPrePortalTraverse(entity);\n" +
        "        // Portal traversal will happen in vanilla event\n" +
        "        PortalCompatibilityManager.onPostPortalTraverse(entity);\n" +
        "    }\n" +
        "\n" +
        "    @SubscribeEvent\n" +
        "    public static void onEntityDimensionChange(ChangeD Dimensionevent) {\n" +
        "        var entity = event.getEntity();\n" +
        "        var oldWorld = event.getFromWorld();\n" +
        "        var newWorld = event.getToWorld();\n" +
        "        PortalCompatibilityManager.onDimensionChange(entity, oldWorld, newWorld);\n" +
        "    }\n" +
        "\n" +
        "    @SubscribeEvent\n" +
        "    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {\n" +
        "        var entity = event.getEntity();\n" +
        "        PortalCompatibilityManager.onPostPortalTraverse(entity);\n" +
        "    }\n" +
        "}\n";
}
