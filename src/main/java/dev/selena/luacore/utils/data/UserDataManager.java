package dev.selena.luacore.utils.data;

import com.google.common.annotations.Beta;
import dev.selena.luacore.exceptions.data.NoUserJsonFoundException;
import dev.selena.luacore.exceptions.data.NotUserFolderException;
import dev.selena.luacore.utils.config.FileManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Used for internal handling of player data
 */
@Beta
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
    public String getUserFolderPath(@NotNull UUID uuid) {
        return FileManager.folderPath(folderName + File.separator + uuid);
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
    @SuppressWarnings("unchecked")
    public <T> T getUserDataFolder(Class<T> userFolderClass, UUID uuid) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoUserJsonFoundException {
        if (userMap.containsKey(uuid)) {
            return (T) userMap.get(uuid);
        }
        File parent = new File(getUserFolderPath(uuid));
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


}
