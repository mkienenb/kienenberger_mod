package org.gamenet.minecraft.mods.kienenberger_mod.town;

import net.minecraft.nbt.NBTTagCompound;

public class Job {

	private static final String JOB_TYPE_ORDINAL_KEY = "jobTypeOrdinal";
	private JobType jobType;
	
	public JobType getJobType() {
		return jobType;
	}

	public Job(JobType jobType) {
		this.jobType = jobType;
	}

	private Job() {
	}

    public Job(NBTTagCompound nbt) {
    	readFromNBT(nbt);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbtIn) {
    	int jobTypeOrdinal = jobType.ordinal();
    	nbtIn.setInteger(JOB_TYPE_ORDINAL_KEY, jobTypeOrdinal);
    	return nbtIn;
	}
	
    public void readFromNBT(NBTTagCompound nbt) {
    	int jobTypeOrdinal = nbt.getInteger(JOB_TYPE_ORDINAL_KEY);
    	for (JobType jobType : JobType.values()) {
			if (jobType.ordinal() == jobTypeOrdinal) {
				this.jobType = jobType;
			}
		}
	}

	public static Job createFromNBT(NBTTagCompound nbt) {
		Job job = new Job();
		job.readFromNBT(nbt);
		if (null != job.getJobType()) {
			return job;
		} else {
			return null;
		}
	}

	public String getJobName() {
		return jobType.toString();
	}
}
