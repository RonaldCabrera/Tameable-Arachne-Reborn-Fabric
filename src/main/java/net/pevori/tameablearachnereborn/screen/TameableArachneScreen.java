package net.pevori.tameablearachnereborn.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pevori.tameablearachnereborn.TameableArachneReborn;
import net.pevori.tameablearachnereborn.entity.custom.TameableArachneEntity;

import java.text.NumberFormat;

public class TameableArachneScreen extends HandledScreen<TameableArachneScreenHandler> {
    private final TameableArachneEntity entity;
    public static final Identifier GUI = new Identifier(TameableArachneReborn.MOD_ID, "textures/gui/container/tameable_arachne.png");
    private static final ItemStack BOOK = Items.BOOK.getDefaultStack();

    public TameableArachneScreen(TameableArachneScreenHandler handler, PlayerInventory inventory, Text text) {
        super(handler, inventory, text);
        this.entity = handler.getEntity();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        // Draws the background of the inventory.
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        // Draws the entity inventory slots.
        drawTexture(matrices, x + 61, y + 17, 0, this.backgroundHeight, 6 * 18, 54);

        // Draws the player inventory and hotbar slots.
        drawTexture(matrices, x + 7, y + 35, 0, this.backgroundHeight + 54, 18, 18);

        // Draws the entity render in the black box.
        InventoryScreen.drawEntity(x + 33, y + 67, 21, (float)(x + 51) - mouseX, (float)(y + 75 - 50) - mouseY, this.entity);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();

        int left = (int) ((this.width - backgroundWidth) / 2F) - 5;
        int top = (int) ((this.height - backgroundHeight) / 2F);
        int size = 20;

        this.addDrawableChild(new ButtonWidget(left - size, top + size, size, size, Text.of(""),
            (button) -> {
                NumberFormat format = NumberFormat.getInstance();
                format.setMaximumFractionDigits(1);

                PlayerEntity owner = (PlayerEntity)entity.getOwner();
                assert owner != null;
                owner.sendMessage(entity.getFormattedStats(format), false);
                owner.sendMessage(entity.getFormattedProtectionStats(), false);
            }){
            @Override
            public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
                super.renderButton(matrices, mouseX, mouseY, delta);

                // Box is 20x20, items are generally 16x16 so adding 2 in both values centers the icon
                itemRenderer.renderGuiItemIcon(BOOK, left - size + 2, top + size + 2);
            }
        });

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 14;
    }
}
