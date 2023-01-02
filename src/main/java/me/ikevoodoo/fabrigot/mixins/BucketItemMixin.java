package me.ikevoodoo.fabrigot.mixins;

import me.ikevoodoo.fabrigot.Fabrigot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/BucketItem;placeFluid(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/hit/BlockHitResult;)Z"))
    private boolean handleEmptiedMethod(BucketItem instance, PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult) {
        if (player instanceof ServerPlayerEntity serverPlayerEntity) {

            var mat = Material.fromMinecraftItem(instance);

            // TODO remove nulls
            var event = new PlayerBucketEmptyEvent(
                    Fabrigot.getFabrigotServer().convertPlayerEntity(serverPlayerEntity),
                    null, // Block
                    null, // Clicked
                    null, // Face
                    mat,
                    new ItemStack(mat, player.getActiveItem().getCount()), // Stack,
                    EquipmentSlot.fromHand(player.getActiveHand())
            );

            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return false;
            }

            // handle Event#setItemStack
        }

        return instance.placeFluid(player, world, pos, hitResult);
    }

}