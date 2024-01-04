package dev.selena.luacore.utils.data;

import com.google.common.annotations.Beta;
import dev.selena.luacore.exceptions.data.NoUserJsonFoundException;
import dev.selena.luacore.exceptions.data.NotUserFolderException;
import dev.selena.luacore.utils.config.ConfigLoader;
import dev.selena.luacore.utils.config.FileManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Used for internal handling of player data
 */
public class UserDataManager {

    /**
     * The folder that the user data is stored in
     */
    public static String folderName;
    private final Map<UUID, Object> userMap;

    /**
     * Used for setting up the manager class.
      * @param baseFolder The base folder for user data to be stored in
     */
    @Beta
    public UserDataManager(String baseFolder) {
        UserDataManager.folderName = baseFolder;
        File folder = FileManager.folder(folderName);
        if (!folder.exists())
            folder.mkdirs();
        userMap = new TreeMap<>();
    }

    /**
     * Used for getting the users data path as a string.
     * @param uuid The users UUID
     * @return The folder path
     */
    @Beta
    public String getUserFolderPath(@NotNull UUID uuid) {
        return FileManager.folderPath(getRelativeUserFolderPath(uuid));
    }

    /**
     * Used for getting the path inside your plugin data folder excluding /plugins/PLUGIN/
     * @param uuid the players UUID
     * @return the relative folder path
     */
    public String getRelativeUserFolderPath(@NotNull UUID uuid) {
        return (folderName + File.separator + uuid);
    }

    /**
     * Used for getting the initialized data folder class
     * @param userFolderClass The class that extends UserFolder
     * @param uuid The users UUID
     * @return The initialized class
     * @param <T> The type of the class
     * @throws NoSuchMethodException Thrown on failed class initialization
     * @throws InvocationTargetException Thrown on failed class initialization
     * @throws InstantiationException Thrown on failed class initialization
     * @throws IllegalAccessException Thrown on failed class initialization
     * @throws NoUserJsonFoundException Thrown when you have not called the {@link UserFolder#loadData(Class, String)} method.
     */
    @Beta
    @SuppressWarnings("unchecked")
    public <T> T getUserDataFolder(Class<T> userFolderClass, UUID uuid) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoUserJsonFoundException {
        if (userMap.containsKey(uuid)) {
            return (T) userMap.get(uuid);
        }
        if (!(UserFolder.class.isAssignableFrom(userFolderClass))) {
            throw new NotUserFolderException("The class " + userFolderClass.getName() + " Is not instance of UserData");
        }
        T userClass = userFolderClass.getConstructor(UUID.class).newInstance(uuid);

        try {
            ((UserFolder) userClass).init();
            userMap.put(uuid, userClass);

            return userClass;
        } catch (NotUserFolderException exception) {
            return null;
        }

    }

    /**
     * Used for saving the users data
     * @param folder The UserFolder class
     * @throws IOException thrown by {@link ConfigLoader#saveConfig(Object, File)}
     */
    public void saveUserData(UserFolder folder) throws IOException {
        if (folder == null)
            throw new NotUserFolderException("The class " + folder.getClass().getName() + " Is not instance of UserData");
        String folderPath = getRelativeUserFolderPath(folder.getUuid());
        for (Class<?> clazz : folder.getLoadedFiles().keySet()) {
            UserFolder.FileClass fileClass = folder.getLoadedFiles().get(clazz);
            String fileName = fileClass.fileName;
            File file = FileManager.file(folderPath, fileName);
            ConfigLoader.saveConfig(fileClass.data, file);
        }
    }

    /**
     * Used for saving all users data
     * @throws IOException thrown by {@link ConfigLoader#saveConfig(Object, File)}
     */
    public void saveAllUserData() throws IOException {
        for (Object folderInst : userMap.values()) {
            if (!(folderInst instanceof UserFolder folder))
                throw new NotUserFolderException("The class " + folderInst.getClass().getName() + " Is not instance of UserData");
            saveUserData(folder);
        }
    }


}
