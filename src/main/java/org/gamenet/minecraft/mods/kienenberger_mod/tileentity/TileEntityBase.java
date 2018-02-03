package org.gamenet.minecraft.mods.kienenberger_mod.tileentity;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntityBase extends TileEntity {
    private static final Logger LOGGER = LogManager.getLogger();

    abstract protected String getNonCustomName();
    
	private String customName;

    public TileEntityBase() {
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }
    
    public String getName() {
        return this.hasCustomName() ? this.customName : getNonCustomName();
    }

    public boolean hasCustomName() {
        return this.customName != null && !this.customName.equals("");
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    // TODO: document diff between getUpdateTag (single TE) and getUpdatePacket(startup -- all tes)
    
    public void markForUpdate() {
    	LOGGER.info("markForUpdate");
    	int flags = 1; // Not sure if the flag matters, and I picked an arbirary value
    	IBlockState currentBlockState = this.worldObj.getBlockState(this.pos);
		worldObj.notifyBlockUpdate(pos, currentBlockState, currentBlockState, flags);;
    }
    
    /**
     * Data sent by server on chunk load
     * @see net.minecraft.tileentity.TileEntity#getUpdateTag()
     */
    @Override
    public NBTTagCompound getUpdateTag() {
    	LOGGER.info("getUpdateTag");
    	NBTTagCompound nbtTagCompound = new NBTTagCompound();
    	writeToNBT(nbtTagCompound);
    	return nbtTagCompound;
    }
    
    /**
     * Data sent by server for individual tile-entity updates
     * @see net.minecraft.tileentity.TileEntity#getUpdateTag()
     */
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
    	LOGGER.info("getUpdatePacket");
		return new SPacketUpdateTileEntity(getPos(), getBlockMetadata(), getUpdateTag());
    };

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	LOGGER.info("onDataPacket");
    	NBTTagCompound nbtTagCompound = pkt.getNbtCompound();
    	readFromNBT(nbtTagCompound);
    }
    

    @Override
    @OverridingMethodsMustInvokeSuper
    public NBTTagCompound writeToNBT(NBTTagCompound nbtIn) {
    	NBTTagCompound nbt = super.writeToNBT(nbtIn);

        if (this.hasCustomName()) {
            nbt.setString("CustomName", this.getCustomName());
        }
        
        return nbt;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        if (nbt.hasKey("CustomName", 8)) {
            this.setCustomName(nbt.getString("CustomName"));
        }

        markDirty();
    }
}