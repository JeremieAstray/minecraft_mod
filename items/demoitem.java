package items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class demoitem extends Item {

	public demoitem(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub

		this.setUnlocalizedName("name"); // 设置名字 非游戏中显示
		this.setCreativeTab(CreativeTabs.tabTools); // 创�?模式标签
		this.setMaxDamage(50); // 物品耐久
	}

	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("demo:1"); // 注册图标
	}

	/*public int getDamageVsEntity(Entity par1Entity) {
		return 8; // 攻击伤害
	}*/

	public int getItemEnchantability() {
		return 25; // 物品附魔能力
	}

	/*public boolean hitEntity(ItemStack par1ItemStack,
			EntityLiving par2EntityLiving, EntityLiving par3EntityLiving) {

		return true; // 攻击
	}*/

	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add("物体下方提示信息"); // 提示信息
	}

}
