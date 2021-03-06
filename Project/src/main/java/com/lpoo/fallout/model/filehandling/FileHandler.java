package com.lpoo.fallout.model.filehandling;

import com.lpoo.fallout.model.wander.Arena;
import com.lpoo.fallout.model.wander.Attributes;
import com.lpoo.fallout.model.wander.WanderModel;
import com.lpoo.fallout.model.wander.element.VaultBoy;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class FileHandler {

    public Arena createArena(@NotNull String fileName) throws IOException, ClassNotFoundException {

        Arena arena;
        FileInputStream is = new FileInputStream("savefiles/arenas/" + fileName + ".bin");
        ObjectInputStream ois = new ObjectInputStream(is);

        arena = (Arena) ois.readObject();
        ois.close();

        return arena;
    }

    public WanderModel createWanderModel(String fileName) throws IOException, ClassNotFoundException {

        FileInputStream is = new FileInputStream("savefiles/" + fileName + ".bin");
        ObjectInputStream ois = new ObjectInputStream(is);

        String arenaName = (String) ois.readObject();
        VaultBoy vaultBoy = (VaultBoy) ois.readObject();
        ois.close();

        Arena arena = createArena(arenaName);
        return new WanderModel(vaultBoy, arena);
    }

    public void saveArena(String fileName, Arena arena) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("savefiles/arenas/" + fileName + ".bin",false));
        os.writeObject(arena);
        os.close();
    }

    public void saveModel(String fileName, WanderModel wanderModel) throws IOException {
        saveArena(wanderModel.getArena().getName(), wanderModel.getArena());
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("savefiles/" + fileName + ".bin", false));
        os.writeObject(wanderModel.getArena().getName());
        os.writeObject(wanderModel.getVaultBoy());
        os.close();
    }

    public void resetSavedGame() throws IOException, ClassNotFoundException {
        WanderModel wanderModel = createWanderModel("gamestatinit");
        Arena arena1 = createArena("arena1init");
        Arena arena2 = createArena("arena2init");
        Arena arena3 = createArena("arena3init");
        saveModel("gamestat", wanderModel);
        saveArena("arena1", arena1);
        saveArena("arena2", arena2);
        saveArena("arena3", arena3);
    }
}
