package org.gamenet.minecraft.mods.kienenberger_mod.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIWrapper extends EntityAIBase {
	private EntityAIBase delegate;
	
	public EntityAIWrapper(EntityAIBase entityAI) {
		this.delegate = entityAI;
	}
	
	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		return delegate.shouldExecute();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		return delegate.continueExecuting();
	}

	/**
	 * Determine if this AI Task is interruptible by a higher (= lower value)
	 * priority task. All vanilla AITask have this value set to true.
	 */
	public boolean isInterruptible() {
		return delegate.isInterruptible();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		delegate.startExecuting();
	}

	/**
	 * Resets the task
	 */
	public void resetTask() {
		delegate.resetTask();
	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		delegate.updateTask();
	}

	/**
	 * Sets a bitmask telling which other tasks may not run concurrently. The
	 * test is a simple bitwise AND - if it yields zero, the two tasks may run
	 * concurrently, if not - they must run exclusively from each other.
	 */
	public void setMutexBits(int mutexBitsIn) {
		delegate.setMutexBits(mutexBitsIn);
	}

	/**
	 * Get a bitmask telling which other tasks may not run concurrently. The
	 * test is a simple bitwise AND - if it yields zero, the two tasks may run
	 * concurrently, if not - they must run exclusively from each other.
	 */
	public int getMutexBits() {
		return delegate.getMutexBits();
	}

	public void setDelegate(EntityAIBase entityAI) {
		this.delegate = entityAI;
	}
}