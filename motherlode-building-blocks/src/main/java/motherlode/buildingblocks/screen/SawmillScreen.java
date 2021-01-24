package motherlode.buildingblocks.screen;

import java.util.List;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import motherlode.buildingblocks.MotherlodeModule;
import motherlode.buildingblocks.recipe.SawmillingRecipe;
import com.mojang.blaze3d.systems.RenderSystem;

public class SawmillScreen extends HandledScreen<SawmillScreenHandler> {
    private float scrollAmount;
    private int scrollCellOffset = 0;
    private boolean canCraft;
    private boolean scrollbarClicked;
    private ItemStack tooltipItemStack = null;

    public SawmillScreen(SawmillScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        handler.setScreenOnInventoryChanged(this::onInventoryChange);
        titleX = 54;
        titleY -= 1;
        playerInventoryTitleY += 2;
    }

    final int columns = 5;
    final int rows = 3;
    final int cells = columns * rows;

    private void bindTexture() {
        client.getTextureManager().bindTexture(MotherlodeModule.id("textures/gui/container/sawmill.png"));
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        renderBackground(matrices);
        RenderSystem.clearColor(1f, 1f, 1f, 1f);
        bindTexture();

        // Background
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        // Scrollbar
        int scrollbarYPos = (int) (47f * this.scrollAmount);
        drawTexture(matrices, x + 159, y + 14 + scrollbarYPos, 176 + (shouldScroll() ? 0 : 12), 0, 12, 15);

        // Recipes
        int lastDisplayedCell = this.scrollCellOffset + cells;
        List<SawmillingRecipe> availableRecipes = handler.getAvailableRecipes();
        for (int i = scrollCellOffset; i < lastDisplayedCell && i < handler.getAvailableRecipes().size(); i++) {
            int iNoOffset = i - scrollCellOffset;
            int cellSize = 18;
            int cellSpace = 2;

            // Background
            int backgroundX = x+54 + cellSpace + iNoOffset % columns * (cellSize+cellSpace);
            int backgroundY = y+14 + cellSpace + iNoOffset / columns * (cellSize+cellSpace);
            boolean mouseOver = (mouseX > backgroundX && mouseX < backgroundX+cellSize && mouseY > backgroundY && mouseY < backgroundY+cellSize);
            drawTexture(matrices, backgroundX, backgroundY, 176, 15 + ((mouseOver) ? 0 : cellSize), cellSize, cellSize);

            // Item
            int itemX = x+55 + cellSpace + iNoOffset % columns * (cellSize+cellSpace);
            int itemY = y+15 + cellSpace + iNoOffset / columns * (cellSize+cellSpace);
            ItemRenderer itemRenderer = client.getItemRenderer();
            ItemStack output = availableRecipes.get(i).getOutput();
            itemRenderer.renderInGuiWithOverrides(output, itemX, itemY);
            // The item gui could be rendered only if the mouse cursor is over it.
            itemRenderer.renderGuiItemOverlay(client.textRenderer, output, itemX, itemY);
            if (mouseOver)
                tooltipItemStack = output;
            bindTexture(); // ItemRenderer#renderInGuiWithOverrides binds the texture to something else
        }
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        if (tooltipItemStack != null)
            renderTooltip(matrices, tooltipItemStack, x, y);
        tooltipItemStack = null;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        scrollbarClicked = false;
        if (canCraft) {
            int lastDisplayedCell = scrollCellOffset + cells;
            for (int i = scrollCellOffset; i < lastDisplayedCell; ++i) {
                int iNoOffset = i - scrollCellOffset;
                int cellSize = 18;
                int cellSpace = 2;

                int backgroundX = x+54 + cellSpace + iNoOffset % columns * (cellSize+cellSpace);
                int backgroundY = y+14 + cellSpace + iNoOffset / columns * (cellSize+cellSpace);
                if (mouseX > backgroundX && mouseX < backgroundX+cellSize && mouseY > backgroundY && mouseY < backgroundY+cellSize && handler.onButtonClick(client.player, i)) {
                    // Play craft sound effect
                    client.interactionManager.clickButton(handler.syncId, i);
                    return true;
                }
            }

            if (mouseX >= (double) (x+159) && mouseX < (double) (x+159 + 12) && mouseY >= (double) (y+14) && mouseY < (double) (y+14 + 54)) {
                scrollbarClicked = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (scrollbarClicked && shouldScroll()) {
            int i = this.y + 14;
            int j = i + 54;
            scrollAmount = ((float) mouseY - (float) i - 7.5f) / ((float) (j - i) - 15f);
            scrollAmount = MathHelper.clamp(scrollAmount, 0.0f, 1f);
            scrollCellOffset = (int) ((double) (scrollAmount * (float) getMaxScroll()) + 0.5d) * 4;
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (shouldScroll()) {
            int maxScroll = getMaxScroll();
            scrollAmount = (float) ((double) scrollAmount - amount / (double) maxScroll);
            scrollAmount = MathHelper.clamp(scrollAmount, 0f, 1f);
            scrollCellOffset = (int) ((double) (scrollAmount * (float) maxScroll) + 0.5d) * columns;
        }

        return true;
    }

    private boolean shouldScroll() {
        return canCraft && handler.getAvailableRecipes().size() > cells;
    }

    protected int getMaxScroll() {
        return (handler.getAvailableRecipes().size() + columns - 1) / columns - rows;
    }

    private void onInventoryChange() {
        canCraft = handler.canCraft();
        if (!canCraft) {
            scrollAmount = 0f;
            scrollCellOffset = 0;
        }
    }
}
