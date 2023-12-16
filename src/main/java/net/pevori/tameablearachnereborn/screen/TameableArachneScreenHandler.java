package net.pevori.tameablearachnereborn.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.pevori.tameablearachnereborn.entity.custom.TameableArachneEntity;

public class TameableArachneScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private int entityId;
    private TameableArachneEntity entity;

    public TameableArachneScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, new SimpleInventory(19));

        entityId = buf.readInt();
        this.entity = (TameableArachneEntity) playerInventory.player.world.getEntityById(entityId);
    }

    public TameableArachneScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(TameableArachneScreenRegistries.TAMEABLE_ARACHNE_SCREEN_HANDLER, syncId);

        checkSize(inventory, 19);
        this.inventory = inventory;

        inventory.onOpen(playerInventory.player);

        //This will place the slot in the correct locations for a 4x5 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        int m, l;
        //The Humanoid Animal´s inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < getInventoryColumns(); ++l) {
                this.addSlot(new Slot(inventory, 1 + l + m * getInventoryColumns(), 62 + l * 18, 18 + m * 18));
            }
        }

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.markDirty();

        this.inventory.onClose(player);
    }

    public TameableArachneEntity getEntity(){
        return entity;
    }

    public int getInventoryColumns() {
        return 6;
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);
        ItemStack itemStack = inventory.getStack(slotIndex);

        if(entity == null){
            entity = (TameableArachneEntity) player.world.getEntityById(entityId);
        }
    }
}
