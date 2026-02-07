package com.axiomportals.client;

import net.fabricmc.api.ClientModInitializer;

public class AxiomPortalsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPortalManager.init();
    }
}
