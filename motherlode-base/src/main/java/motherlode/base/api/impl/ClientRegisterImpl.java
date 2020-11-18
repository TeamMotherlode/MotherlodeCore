package motherlode.base.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class ClientRegisterImpl {
    public static final ClientRegisterImpl INSTANCE = new ClientRegisterImpl();

    private List<Pair<Identifier, Consumer<Identifier>>> clientConsumers = new ArrayList<>();

    private ClientRegisterImpl() {
    }

    public void addClientConsumer(Identifier id, Consumer<Identifier> clientConsumer) {
        this.clientConsumers.add(new Pair<>(id, clientConsumer));
    }

    public List<Pair<Identifier, Consumer<Identifier>>> getClientConsumers() {
        return this.clientConsumers;
    }

    public void removeClientConsumerList() {
        this.clientConsumers = null;
    }
}
