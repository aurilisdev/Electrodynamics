package physica.forcefield.common.item;

import java.util.HashSet;
import java.util.Set;

public class Permission {

	public static Set<Permission> LIST = new HashSet<>();

	public static Permission BLOCK_ALTER = new Permission(0, "Block Alter");

	public static Permission BLOCK_ACCESS = new Permission(1, "Block Access");

	public static Permission SECURITY_CENTER_CONFIGURE = new Permission(2, "Configure Identifier");

	public static Permission BYPASS_INTERDICTION_MATRIX = new Permission(3, "Bypass Matrix");

	public static Permission BYPASS_CONFISCATION = new Permission(4, "Bypass Confiscation");

	public int id;

	public String name;

	public Permission(int id, String name) {
		this.id = id;
		this.name = name;
		LIST.add(this);
	}

	public static Set<Permission> getPermissions()
	{
		return LIST;
	}

	public static Permission getPermission(int id)
	{
		Permission perm = null;
		for (Permission permission : LIST)
		{
			if (permission.id == id)
			{
				perm = permission;
			}
		}
		return perm;
	}
}
