package physica.api.core.load;

import java.util.ArrayList;
import java.util.List;

public class ContentLoader {

	private final List<IContent> contentList = new ArrayList<>();

	public void addContent(IContent content) {
		contentList.add(content);
	}

	public List<IContent> getContentList() {
		return contentList;
	}

	public void callRegister(LoadPhase phase, Object... args) {
		if (args.length > 0) {
			for (IContent proxy : contentList) {
				proxy.registerAdvanced(phase);
			}
		} else {
			for (IContent proxy : contentList) {
				proxy.register(phase);
			}
		}
	}

}
