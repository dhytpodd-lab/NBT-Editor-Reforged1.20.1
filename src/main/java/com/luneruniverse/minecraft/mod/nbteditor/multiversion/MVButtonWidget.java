package com.luneruniverse.minecraft.mod.nbteditor.multiversion;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class MVButtonWidget extends PressableWidget {
	
	@FunctionalInterface
	public interface PressAction {
		public void onPress(MVButtonWidget button);
	}
	
	private final PressAction onPress;
	private final MVTooltip tooltip;
	
	public MVButtonWidget(int x, int y, int width, int height, Text text, PressAction onPress, MVTooltip tooltip) {
		super(x, y, width, height, text);
		this.onPress = onPress;
		this.tooltip = tooltip;
		if (tooltip != null) {
			Version.newSwitch()
					.range("1.19.3", null, () -> setTooltip(tooltip.toNewTooltip()))
					.range(null, "1.19.2", () -> {})
					.run();
		}
	}
	public MVButtonWidget(int x, int y, int width, int height, Text text, PressAction onPress) {
		this(x, y, width, height, text, onPress, null);
	}
	
	@Override
	public void onPress() {
		onPress.onPress(this);
	}
	
	@Override
	protected void appendClickableNarrations(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		Version.newSwitch()
				.range("1.20.0", null, () -> super.render(MVDrawableHelper.getDrawContext(matrices), mouseX, mouseY, delta))
				.range(null, "1.19.4", () -> MVDrawableHelper.super_render(MVButtonWidget.class, this, matrices, mouseX, mouseY, delta))
				.run();
	}
	public final void method_25394(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		render(matrices, mouseX, mouseY, delta);
	}
	@Override
	public final void render(DrawContext context, int mouseX, int mouseY, float delta) {
		render(MVDrawableHelper.getMatrices(context), mouseX, mouseY, delta);
	}
	
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.renderButton(MVDrawableHelper.getDrawContext(matrices), mouseX, mouseY, delta);
	}
	@Override
	protected final void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
		renderButton(MVDrawableHelper.getMatrices(context), mouseX, mouseY, delta);
	}
	public final void method_48579(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderButton(matrices, mouseX, mouseY, delta);
	}
	public final void method_25359(MatrixStack matrices, int mouseX, int mouseY, float delta) { // renderButton <= 1.19.3
		renderButton(matrices, mouseX, mouseY, delta);
	}
	public void method_25352(MatrixStack matrices, int mouseX, int mouseY) { // renderTooltip
		if (tooltip != null)
			tooltip.render(matrices, mouseX, mouseY);
	}
	
}
