package physica.proxy;

import java.util.ArrayList;
import java.util.List;

import physica.api.base.IProxyContent;

public class ProxyLoader {
	private final List<IProxyContent> proxyList = new ArrayList();

	public void addContent(IProxyContent content) {
		proxyList.add(content);
	}

	public List<IProxyContent> getProxyList() {
		return proxyList;
	}

	public void init() {
		for (IProxyContent proxy : proxyList)
		{
			proxy.init();
		}
	}

	public void loadComplete() {
		for (IProxyContent proxy : proxyList)
		{
			proxy.loadComplete();
		}
	}

	public void postInit() {
		for (IProxyContent proxy : proxyList)
		{
			proxy.postInit();
		}
	}

	public void preInit() {
		for (IProxyContent proxy : proxyList)
		{
			proxy.preInit();
		}
	}

}
