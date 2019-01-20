package physica.proxy;

import java.util.ArrayList;
import java.util.List;

import physica.api.core.IContent;

public class ContentLoader {

	private final List<IContent> contentList = new ArrayList<>();

	public void addContent(IContent content) {
		contentList.add(content);
	}

	public List<IContent> getContentList() {
		return contentList;
	}

	public void init() {
		for (IContent proxy : contentList) {
			proxy.init();
		}
	}

	public void loadComplete() {
		for (IContent proxy : contentList) {
			proxy.loadComplete();
		}
	}

	public void postInit() {
		for (IContent proxy : contentList) {
			proxy.postInit();
		}
	}

	public void preInit() {
		for (IContent proxy : contentList) {
			proxy.preInit();
		}
	}

}
