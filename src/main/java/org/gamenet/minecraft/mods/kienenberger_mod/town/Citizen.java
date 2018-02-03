package org.gamenet.minecraft.mods.kienenberger_mod.town;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.ai.EntityAIMoveToTree;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.ai.EntityAIWrapper;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.monster.EntityDiamondGolem;
import org.gamenet.minecraft.mods.kienenberger_mod.tileentity.TownControllerBlockTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Citizen {
    private static final Logger LOGGER = LogManager.getLogger();
	
    private static final String CITIZEN_UUID_KEY = "uuid";
    private static final String CITIZEN_JOB_KEY = "job";
	
	private static int citizenNameCounter = 0;

    private TownControllerBlockTileEntity townControllerBlockTileEntity = null;

	private UUID uuid = null;
	private EntityCreature entityCreature = null;
	private Job job;

	public Citizen(TownControllerBlockTileEntity townControllerBlockTileEntity, EntityCreature entityCreature) {
		this.townControllerBlockTileEntity = townControllerBlockTileEntity;
		this.entityCreature = entityCreature;
	}

	static public String generateCitizenName(EntityCreature entityCreature) {
		int nextCitizenNameCount;
		synchronized (Citizen.class) {
			// Note that this value isn't saved and will be reset to zero each time the java Citizen class is reloaded
			nextCitizenNameCount = ++Citizen.citizenNameCounter;
		}
		String citizenName = "Citizen #" + String.valueOf(nextCitizenNameCount);
		return citizenName;
	}

	public Citizen(TownControllerBlockTileEntity townControllerBlockTileEntity, UUID uuid) {
		this.townControllerBlockTileEntity = townControllerBlockTileEntity;
		this.uuid = uuid;
	}

	public EntityCreature getEntityCreature() {
		Entity entity;
		if (null == entityCreature) {
			World world = townControllerBlockTileEntity.getWorld();
			if (null == world) {
	    		// TODO: need an error here
				return null;
			}
			
	    	if (world.isRemote) {
	    		entity = Minecraft.getMinecraft().getIntegratedServer().getEntityFromUuid(uuid);
	    	} else {
	    		// TODO: no entity found on game load.   Possibly an ordering problem?
	    		// Change citizen to store uuid and defer resolution
	        	entity = world.getMinecraftServer().getEntityFromUuid(uuid);
	    	}
	    	LOGGER.info("getEntityCreature: entity[" + uuid + "]=" + entity);

	    	if (null != entity) {
	        	LOGGER.info("readFromNBT: entity class[" + uuid + "]=" + entity.getClass().getName());
	        	if (entity instanceof EntityCreature) {
	        		this.entityCreature = (EntityCreature)entity;
	        	}
	    	} else {
	    		// TODO: need an error here
	    	}
		}
    	
		return entityCreature;
	}

	public void setEntityCreature(EntityCreature entityCreature) {
		this.entityCreature = entityCreature;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbtIn) {
    	UUID persistentID = getEntityCreature().getPersistentID();
		nbtIn.setUniqueId(CITIZEN_UUID_KEY, persistentID);
		Job job = getJob();
		if (null != job) {
			NBTTagCompound nbtJob = new NBTTagCompound();
			job.writeToNBT(nbtJob);
			nbtIn.setTag(CITIZEN_JOB_KEY, nbtJob);
		}
		
    	return nbtIn;
	}
	
	public static Citizen createFromNBT(TownControllerBlockTileEntity townControllerBlockTileEntity, World world, NBTTagCompound nbt) {
		UUID uuid = nbt.getUniqueId(CITIZEN_UUID_KEY);
		LOGGER.info("readFromNBT: uuid=" + uuid);
		// TODO: this code is probably broken in some circumstances
		Entity entity;
		if (world.isRemote) {
			entity = Minecraft.getMinecraft().getIntegratedServer().getEntityFromUuid(uuid);
		} else {
			// TODO: no entity found on game load.   Possibly an ordering problem?
			// Change citizen to store uuid and defer resolution
			entity = world.getMinecraftServer().getEntityFromUuid(uuid);
		}
		LOGGER.info("readFromNBT: entity=" + entity);

		Citizen citizen;
		if (null != entity) {
			LOGGER.info("readFromNBT: entity class=" + entity.getClass().getName());
			if (entity instanceof EntityCreature) {
				EntityCreature entityCreature = (EntityCreature)entity;
		    	citizen = new Citizen(townControllerBlockTileEntity, entityCreature);
		    	LOGGER.info("readFromNBT: citizen from entity=" + citizen);
			} else {
				// TODO: probably should generate an error here
				citizen = null;
			}
		} else {
			citizen = new Citizen(townControllerBlockTileEntity, uuid);
			LOGGER.info("readFromNBT: citizen from uuid=" + citizen);
		}
		
		NBTTagCompound nbtJob = (NBTTagCompound) nbt.getTag(CITIZEN_JOB_KEY);
		if (null != nbtJob) {
			Job job = Job.createFromNBT(nbtJob);
			if (null != job) {
				citizen.setJob(job);
			} else {
				// TODO: probably should generate an error here
			}
		}
		
		return citizen;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
		
		// Probably need to reconfigure this to be a capability?
		EntityCreature entityWorker = getEntityCreature();
		if (null != entityWorker) {
			EntityDiamondGolem entityDiamondGolem = (EntityDiamondGolem)entityWorker;
			EntityAIWrapper aiWrapper = entityDiamondGolem.getAiTaskWrapper();
			switch(job.getJobType()) {
				case LUMBERJACK:
					double movementSpeed = 0.6D;
					int searchLength = 64;
					aiWrapper.setDelegate(new EntityAIMoveToTree(entityWorker, movementSpeed, searchLength));
					break;
				case FARMER:
				case BUILDER:
				case MINER:
				default:
					// do nothing;
					break;
			}
		} else {
			// Not sure when the entity is going to exist.
			// Probably configure it in initEntityAI()?
		}
		
	}
}
