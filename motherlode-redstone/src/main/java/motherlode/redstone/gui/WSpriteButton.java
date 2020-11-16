package motherlode.redstone.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Texture;

public class WSpriteButton extends WSprite {
    private Runnable onClick;
    private Identifier[] focusedFrames;
    private boolean singleFocusedImage;
    private OrderedText tooltip;

    private final Identifier[] frames;

    public WSpriteButton(Identifier image) {
        super(image);

        this.frames = new Identifier[super.frames.length];

        for (int i = 0; i < super.frames.length; i++) {
            this.frames[i] = super.frames[i].image;
        }
    }

    @Override
    public void onClick(int x, int y, int button) {
        if (this.isWithinBounds(x, y)) {
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            if (this.onClick != null) {
                this.onClick.run();
            }
        }
    }

    public void setTooltip(OrderedText tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public boolean canFocus() {
        return true;
    }

    public WSpriteButton setFocusedImage(Identifier focusedFrames) {
        this.focusedFrames = new Identifier[] {focusedFrames};
        singleFocusedImage = true;
        return this;
    }

    @Environment(EnvType.CLIENT)
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        boolean hovered = mouseX >= 0 && mouseY >= 0 && mouseX < this.getWidth() && mouseY < this.getHeight();
        if ((this.isFocused() || hovered) && focusedFrames != null) {
            paintFrames(singleFocusedImage, focusedFrames, x, y);
            ScreenDrawing.drawStringWithShadow(matrices, tooltip, HorizontalAlignment.CENTER, x + 40, y - 10, this.width, 14737632);
            ScreenDrawing.coloredRect(x + 38, y - 12, tooltip.toString().length() * 6, 24, 0xaaee00);
        } else {
            paintFrames(singleImage, frames, x, y);
        }
    }

    private void paintFrames(boolean single, Identifier[] frames, int x, int y) {
        if (single) {
            this.paintFrame(x, y, super.frames[0]);
        } else {
            long now = System.nanoTime() / 1000000L;
            boolean inBounds = this.currentFrame >= 0 && this.currentFrame < frames.length;
            if (!inBounds) {
                this.currentFrame = 0;
            }

            Identifier currentFrameTex = frames[this.currentFrame];
            this.paintFrame(x, y, new Texture(currentFrameTex));
            long elapsed = now - this.lastFrame;
            this.currentFrameTime += elapsed;
            if (this.currentFrameTime >= (long) this.frameTime) {
                ++this.currentFrame;
                if (this.currentFrame >= frames.length - 1) {
                    this.currentFrame = 0;
                }

                this.currentFrameTime = 0L;
            }

            this.lastFrame = now;
        }
    }

    @Override
    public void addTooltip(TooltipBuilder builder) {
        if (builder != null)
            builder.add(tooltip);
    }

    public WSpriteButton setOnClick(Runnable r) {
        this.onClick = r;
        return this;
    }
}
