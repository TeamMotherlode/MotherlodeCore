package motherlode.base.api.impl;

import java.util.ArrayList;
import java.util.List;

public class ServerRegisterImpl {
    public static final ServerRegisterImpl INSTANCE = new ServerRegisterImpl();

    private List<Runnable> serverTasks = new ArrayList<>();

    private ServerRegisterImpl() {
    }

    public void addServerTask(Runnable serverTask) {
        this.serverTasks.add(serverTask);
    }

    public List<Runnable> getServerTasks() {
        return this.serverTasks;
    }

    public void removeServerTaskList() {
        this.serverTasks = null;
    }
}
