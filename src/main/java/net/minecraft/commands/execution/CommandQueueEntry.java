package net.minecraft.commands.execution;

public record CommandQueueEntry<T>(Frame frame, EntryAction<T> action) {
    public void execute(ExecutionContext<T> p_310616_) {
        this.action.execute(p_310616_, this.frame);
    }
}