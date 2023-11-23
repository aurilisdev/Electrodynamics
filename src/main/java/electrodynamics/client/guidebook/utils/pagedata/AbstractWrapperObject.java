package electrodynamics.client.guidebook.utils.pagedata;

import javax.annotation.Nullable;

/**
 * Base class for all guidebook rendering
 * 
 * @author skip999
 *
 * @param <T> The non-abstract class to keep the builder from breaking
 */
public abstract class AbstractWrapperObject<T extends AbstractWrapperObject<?>> {

	@Nullable
	public OnTooltip onTooltip = null;
	@Nullable
	public OnClick onClick = null;
	@Nullable
	public OnKeyPress onKeyPress = null;

	public boolean newPage = false;

	public AbstractWrapperObject() {

	}

	public T onTooltip(OnTooltip onTooltip) {
		this.onTooltip = onTooltip;
		return (T) this;
	}

	public T onClick(OnClick onClick) {
		this.onClick = onClick;
		return (T) this;
	}

	public T onKeyPress(OnKeyPress onKeyPress) {
		this.onKeyPress = onKeyPress;
		return (T) this;
	}

	public T setNewPage() {
		newPage = true;
		return (T) this;
	}

}
