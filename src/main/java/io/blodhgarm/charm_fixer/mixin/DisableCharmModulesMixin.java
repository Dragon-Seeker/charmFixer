package io.blodhgarm.charm_fixer.mixin;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(targets = {"svenhjol/charm/loader/ModuleLoader"}, remap = false)
@Pseudo
public class DisableCharmModulesMixin {
	private static final Logger LOGGER = LogUtils.getLogger();

	private static final List<String> CHARM_FIXER$BLACKLISTED_MODULES = List.of("PortableCraftingClient", "InventoryTidyingClient", "CoreClient");

	@SuppressWarnings("UnresolvedMixinReference")
	@Inject(method = "prepareModules", at = @At(value = "RETURN"))
	private void owo$disable_modules(CallbackInfoReturnable<Map<String, ?>> cir){
		CHARM_FIXER$BLACKLISTED_MODULES.forEach(cir.getReturnValue()::remove);

		LOGGER.warn("[CharmFixer]: PortableCrafting, InventoryTidying, and CoreClient(InventoryButtonManager) are disabled meaning such features will be missing!");
	}
}
