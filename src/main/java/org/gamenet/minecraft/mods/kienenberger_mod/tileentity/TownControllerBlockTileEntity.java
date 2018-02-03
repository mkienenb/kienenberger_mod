package org.gamenet.minecraft.mods.kienenberger_mod.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gamenet.minecraft.mods.kienenberger_mod.town.Citizen;
import org.gamenet.minecraft.mods.kienenberger_mod.town.Job;
import org.gamenet.minecraft.mods.kienenberger_mod.town.JobType;
import org.gamenet.minecraft.mods.kienenberger_mod.town.StockPile;

public class TownControllerBlockTileEntity extends TileEntityBase {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String CITIZENS_LIST_KEY = "Citizens";
	private static final String JOBS_LIST_KEY = "Jobs";

    private List<Citizen> citizenList = new ArrayList<Citizen>();
	private List<Job> unassignedJobList = new ArrayList<Job>();
	private Map<Job,Citizen> assignedJobByCitizenMap = new HashMap<Job,Citizen>();
	private List<StockPile> stockPileList = new ArrayList<StockPile>();

	private World worldReferenceOnCreate;

	/**
	 * Called on world load
	 */
	public TownControllerBlockTileEntity() {
	}
	
    /**
     * Called on tile entity creation
     * @param world
     */
    public TownControllerBlockTileEntity(World world) {
    	if (!world.isRemote) {
    		unassignedJobList.add(new Job(JobType.LUMBERJACK));
    		unassignedJobList.add(new Job(JobType.MINER));
    		unassignedJobList.add(new Job(JobType.FARMER));
    		unassignedJobList.add(new Job(JobType.BUILDER));
    		unassignedJobList.add(new Job(JobType.LUMBERJACK));
    		unassignedJobList.add(new Job(JobType.MINER));
    	}
    }

    @Override
    protected String getNonCustomName() {
		return "town_controller_tile_entity";
	}

    @Override
    @OverridingMethodsMustInvokeSuper
    public NBTTagCompound writeToNBT(NBTTagCompound nbtIn) {
    	NBTTagCompound nbt = super.writeToNBT(nbtIn);

        NBTTagList citizenTagList = new NBTTagList();
    	LOGGER.info("writeToNBT: citizenList.size()=" + citizenList.size());
        for (Citizen citizen : citizenList) {
        	NBTTagCompound nbtTagCompound = new NBTTagCompound();
        	NBTTagCompound citizenTag = citizen.writeToNBT(nbtTagCompound);
        	citizenTagList.appendTag(citizenTag);
		}
        nbt.setTag(CITIZENS_LIST_KEY, citizenTagList);
        
        NBTTagList jobTagList = new NBTTagList();
    	LOGGER.info("writeToNBT: jobList.size()=" + unassignedJobList.size());
        for (Job job : unassignedJobList) {
        	NBTTagCompound nbtTagCompound = new NBTTagCompound();
            NBTTagCompound jobTag = job.writeToNBT(nbtTagCompound);
        	jobTagList.appendTag(jobTag);
		}
        nbt.setTag(JOBS_LIST_KEY, jobTagList);
        
        return nbt;
    }

    @Override
    protected void setWorldCreate(World world) {
    	this.worldReferenceOnCreate = world;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        int NBT_TAG_COMPOUND_TYPE_ID = new NBTTagCompound().getId();

		// TODO: no world on game startup!
		World world = getWorld();
		if (null == world) {
			world = worldReferenceOnCreate;
		}

		citizenList.clear();
		assignedJobByCitizenMap.clear();
		NBTTagList citizenTagList = nbt.getTagList(CITIZENS_LIST_KEY, NBT_TAG_COMPOUND_TYPE_ID);
    	LOGGER.info("readFromNBT: citizenTagList.tagCount()=" + citizenTagList.tagCount());
    	for (int i = 0; i < citizenTagList.tagCount(); ++i) {
    		LOGGER.info("readFromNBT: loading citizen[" + i + "]");
            NBTTagCompound citizenTagCompound = citizenTagList.getCompoundTagAt(i);
			Citizen citizen = Citizen.createFromNBT(this, world, citizenTagCompound);
        	if (null != citizen) {
    			citizenList.add(citizen);
    			Job job = citizen.getJob();
    			if (null != job) {
    				assignedJobByCitizenMap.put(job, citizen);
    			}
        	}
        }

    	unassignedJobList.clear();
		NBTTagList jobTagList = nbt.getTagList(JOBS_LIST_KEY, NBT_TAG_COMPOUND_TYPE_ID);
    	LOGGER.info("readFromNBT: jobTagList.tagCount()=" + jobTagList.tagCount());
    	for (int i = 0; i < jobTagList.tagCount(); ++i) {
            NBTTagCompound jobTagCompound = jobTagList.getCompoundTagAt(i);
            Job job = Job.createFromNBT(jobTagCompound);
        	if (null != job) {
        		unassignedJobList.add(job);
        	}
        }
    }

	public void addCitizen(Citizen citizen) {
		citizenList.add(citizen);
		markForUpdate();
	}

	public List<Citizen> getCitizenList() {
		return citizenList;
	}

	public List<Job> getUnassignedJobList() {
		return unassignedJobList;
	}

	public Map<Job,Citizen> getAssignedJobByCitizenMap() {
		return assignedJobByCitizenMap;
	}

	public void assignJob(Citizen citizen) {
		if (!unassignedJobList.isEmpty()) {
			Job job = unassignedJobList.remove(0);
			citizen.setJob(job);
			assignedJobByCitizenMap.put(job, citizen);
		}
	}
}