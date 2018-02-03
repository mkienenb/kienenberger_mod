package org.gamenet.minecraft.mods.kienenberger_mod;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.gamenet.minecraft.mods.kienenberger_mod.blocks.client.render.BlockRenderRegister;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.client.render.EntityRenderRegister;
import org.gamenet.minecraft.mods.kienenberger_mod.items.client.render.ItemRenderRegister;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		
		EntityRenderRegister.registerEntityRenderer();
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);

		ItemRenderRegister.registerItemRenderer();
		BlockRenderRegister.registerBlockRenderer();
		
//		IBlockColor pinkBlockColor = new IBlockColor() {
//			@Override
//			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//				return  (new Color(255, (int)(255 * 0.8), 255).getRGB());
//			}
//		};
//		IBlockColor blueBlockColor = new IBlockColor() {
//			@Override
//			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//				return  (new Color(0, 0, 255).getRGB());
//			}
//		};
//		IBlockColor greenBlockColor = new IBlockColor() {
//			@Override
//			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//				return  (new Color(0, 255, 0).getRGB());
//			}
//		};
//		IBlockColor orangeBlockColor = new IBlockColor() {
//			@Override
//			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//				return  (new Color(255, 200, 0).getRGB());
//			}
//		};
//		IBlockColor yellowBlockColor = new IBlockColor() {
//			@Override
//			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//				return  (new Color(255, 255, 0).getRGB());
//			}
//		};
//		IBlockColor grayBlockColor = new IBlockColor() {
//			@Override
//			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//				return  (new Color(50, 50, 50).getRGB());
//			}
//		};
//		IBlockColor whiteBlockColor = new IBlockColor() {
//			@Override
//			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//				return  (new Color(255, 255, 255).getRGB());
//			}
//		};
//		
//		IBlockColor variationBlockColor = new IBlockColor() {
//			@Override
//			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//				int scalerX = Math.abs(pos.getX() % 255);
//				int scalerZ = Math.abs(pos.getZ() % 255);
//				int scalerY = Math.abs( (scalerX + scalerZ ) % 255 );
//				return  (new Color(scalerX, scalerY, scalerZ).getRGB());
//			}
//		};
//
//		IBlockColor variationByTimeBlockColor = new IBlockColor() {
//			@Override
//			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//				return Color.getHSBColor(((((System.currentTimeMillis()/20)+85) % 256) / 256f), 0.75f, 1f).getRGB();
//			}
//		};
//
//		IBlockColor variationByTime2BlockColor = new IBlockColor() {
//			@Override
//			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
//				return Color.getHSBColor(((((System.currentTimeMillis()/11)+56) % 256) / 256f), 0.75f, 1f).getRGB();
//			}
//		};

		
//		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(blueBlockColor, Blocks.DIRT);
//		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(pinkBlockColor, Blocks.GRASS);
//		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(variationByTimeBlockColor, Blocks.LEAVES);
//		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(variationByTimeBlockColor, Blocks.LEAVES2);
//		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(greenBlockColor, Blocks.WATER);
//		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(yellowBlockColor, Blocks.LOG);
//		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(yellowBlockColor, Blocks.LOG2);
//		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(grayBlockColor, Blocks.LAVA);
//		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(variationByTime2BlockColor, Blocks.TALLGRASS);
//		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(variationByTime2BlockColor, Blocks.DOUBLE_PLANT);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}

//	// In your client proxy:
//	@Override
//	public EntityPlayer getPlayerEntity(MessageContext ctx) {
//	 // Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
//	 // your packets will not work because you will be getting a client
//	 // player even when you are on the server! Sounds absurd, but it's true.
//
//	 // Solution is to double-check side before returning the player:
//	 return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
//	}
}