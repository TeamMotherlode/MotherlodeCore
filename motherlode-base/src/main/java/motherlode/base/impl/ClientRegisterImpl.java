package motherlode.base.impl;

import java.util.ArrayList;
import java.util.List;

public class ClientRegisterImpl {
    public static final ClientRegisterImpl INSTANCE = new ClientRegisterImpl();

    private List<Runnable> clientTasks = new ArrayList<>();

    private ClientRegisterImpl() {
    }

    public void addClientTask(Runnable clientTask) {
        this.clientTasks.add(clientTask);
    }

    public List<Runnable> getClientTasks() {
        return this.clientTasks;
    }

    public void removeClientTaskList() {
        this.clientTasks = null;
    }
}
