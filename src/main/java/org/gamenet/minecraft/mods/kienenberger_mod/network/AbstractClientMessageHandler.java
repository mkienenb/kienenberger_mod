//package org.gamenet.minecraft.mods.kienenberger_mod.network;
//
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//
////From http://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/2137055-1-7-x-1-8-customizing-packet-handling-with
///**
// * 
// * This is just a convenience class that will prevent the server-side message
// * handling method from appearing in our client message handler classes.
// *
// */
//public abstract class AbstractClientMessageHandler<T extends IMessage> extends
//		AbstractMessageHandler<T>
//
//{
//	// implementing a final version of the server message handler both prevents
//	// it from
//	// appearing automatically and prevents us from ever accidentally overriding
//	// it
//	public final IMessage handleServerMessage(EntityPlayer player, T message,
//			MessageContext ctx) {
//		return null;
//	}
//}